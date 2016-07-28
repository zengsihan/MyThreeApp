package com.zsh.xuexi.mythreeapp.presenter;

import android.os.AsyncTask;
import android.util.Log;

import com.zsh.xuexi.mythreeapp.commons.RepoListPtrView;
import com.zsh.xuexi.mythreeapp.commons.RepoListView;

import java.util.ArrayList;

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
        new RefreshTask().execute();
    }

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
