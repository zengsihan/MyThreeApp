package com.zsh.xuexi.mythreeapp.presenter;

import android.os.AsyncTask;
import android.util.Log;

import com.zsh.xuexi.mythreeapp.commons.RepoListView;
import com.zsh.xuexi.mythreeapp.http.GitHubApi;
import com.zsh.xuexi.mythreeapp.http.GitHubClient;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zsh on 2016/7/27.
 */
public class RepoListPresenter {
    private RepoListView repoListView;

    public RepoListPresenter(RepoListView repoListView) {
        this.repoListView = repoListView;
    }

    //下拉刷新处理
    public void refresh(){
//        new RefreshTask().execute();

        //通过retrofit拿数据
        GitHubClient gitHubClient=new GitHubClient();
        GitHubApi gitHubApi=gitHubClient.getGitHubApi();
        Call<ResponseBody> call=gitHubApi.gitHub();
        //两种方式：直接在当前线程执行，异步执行
        call.enqueue(refreshCallback);


    }
    private final Callback<ResponseBody> refreshCallback=new Callback<ResponseBody>() {
        @Override//响应
        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            repoListView.stopRefresh();//停止刷新动画
            //成功：200-299
            if(response.isSuccessful()){
                try {
                    ResponseBody body=response.body();
                    if (body == null) {
                        repoListView.showMessage("未知错误！");
                        return;
                    }
                    // content:就是从服务器拿到的响应体数据
                    String content = body.string();
                    Log.i("ms",content);
                    repoListView.showContentView();
                } catch (IOException e) {
//                    e.printStackTrace();
                    onFailure(call, e);//失败，调用失败方法
                }
            }else {//其他code
                repoListView.showMessage("code:"+response.code());
            }
        }

        @Override//失败
        public void onFailure(Call<ResponseBody> call, Throwable t) {
            repoListView.stopRefresh();
            repoListView.showMessage(t.getMessage());
            repoListView.showContentView();
        }
    };

    /*加载更多处理*/
    public void loadMore(){
        repoListView.showLoadMoreLoading();
        new LoadMoreTask().execute();
    }

    //上拉加载更多异步处理
    class LoadMoreTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            ArrayList<String> datas=new ArrayList<String>();
            for(int i=0;i<10;i++){
                datas.add("上拉数据"+i);
            }
            repoListView.addMoreData(datas);
            repoListView.hideLoadMore();
        }
    }


    //下拉刷新异步处理
    class RefreshTask extends AsyncTask<Void,Void,Void>{

        @Override//执行
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override//执行完成
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            ArrayList<String> datas=new ArrayList<String>();
            for(int i=0;i<20;i++){
                datas.add("ceshi"+i);
            }
            repoListView.stopRefresh();//调用隐藏刷新动画的方法
            repoListView.refreshData(datas);
            repoListView.showContentView();
        }

        @Override//准备工作
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override//更新
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}
