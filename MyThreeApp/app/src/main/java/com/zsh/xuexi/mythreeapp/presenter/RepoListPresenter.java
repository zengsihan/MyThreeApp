package com.zsh.xuexi.mythreeapp.presenter;

import android.os.AsyncTask;
import android.util.Log;

import com.zsh.xuexi.mythreeapp.commons.RepoListView;
import com.zsh.xuexi.mythreeapp.entity.Repo;
import com.zsh.xuexi.mythreeapp.entity.RepoResult;
import com.zsh.xuexi.mythreeapp.http.GitHubClient;
import com.zsh.xuexi.mythreeapp.http.Language;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zsh on 2016/7/27.
 */
public class RepoListPresenter {
    private RepoListView repoListView;//视图的接口
    private int nextPage=0;
    private Language language;

    private Call<RepoResult> repoCall;

    public RepoListPresenter(RepoListView repoListView,Language language) {
        this.repoListView = repoListView;
        this.language=language;
    }

    //下拉刷新处理
    public void refresh(){
        //隐藏loadmore
        repoListView.hideLoadMoreLoading();
        repoListView.showContentView();
        nextPage=1;//刷新只刷第一页，最新的数据
        repoCall= GitHubClient.getInstance().searchRepos(
                "language:"+language.getPath(), nextPage);
        repoCall.enqueue(repoCallback);
    }
    private final Callback<RepoResult> repoCallback=new Callback<RepoResult>() {
        @Override
        public void onResponse(Call<RepoResult> call, Response<RepoResult> response) {
            //判断一下
            //视图停止刷新
            repoListView.stopRefresh();
            //得到响应结果
            RepoResult repoResult=response.body();
            if(repoResult==null){
                repoListView.showErrorView("结果为空!");
                return;
            }
            //当前所搜的语言，没有仓库
            if(repoResult.getTotalCount()<=0){
                repoListView.refreshData(null);
                repoListView.showEmptyView();
                return;
            }
            //取出当前语言下的所有仓库
            List<Repo> repoList =repoResult.getRepoList();
            repoListView.refreshData(repoList);
            //下拉刷新成功，下一页则更新为2；
            nextPage=2;
        }

        @Override
        public void onFailure(Call<RepoResult> call, Throwable t) {
            //视图停止刷新
            repoListView.stopRefresh();
            repoListView.showMessage("repoCallback onFailure"+t.getMessage());
        }
    };

    /*加载更多处理*/
    public void loadMore(){
        repoListView.showLoadMoreLoading();
        repoCall=GitHubClient.getInstance().searchRepos("language:"+language.getPath(),nextPage);
        repoCall.enqueue(loadMoreCallback);
    }
    private final Callback<RepoResult> loadMoreCallback =new Callback<RepoResult>() {
        @Override
        public void onResponse(Call<RepoResult> call, Response<RepoResult> response) {
            repoListView.hideLoadMoreLoading();
            //得到响应的结果
            RepoResult repoResult=response.body();
            if(repoResult==null){
                repoListView.showLoadMoreError("结果为空");
                return;
            }
            //取出当前语言下的所有仓库
            List<Repo> repoList=repoResult.getRepoList();
            repoListView.addMoreData(repoList);
            nextPage++;
        }

        @Override
        public void onFailure(Call<RepoResult> call, Throwable t) {
            //视图停止刷新
            repoListView.hideLoadMoreLoading();
            repoListView.showMessage("repoCallback onFailure"+t.getMessage());
        }
    };


}
