package com.zsh.xuexi.mythreeapp.commons;

import com.zsh.xuexi.mythreeapp.entity.Repo;

import java.util.List;

/**
 * Created by zsh on 2016/7/28.
 * 用于上拉加载更多
 */
public interface RepoListLoadMoreView {
    /**显示加载更多的进度条 */
    public void showLoadMoreLoading();
    /* 隐藏foot*/
    public void hideLoadMore();
    /*显示加载错误*/
    public void showLoadMoreError(String erroeMsg);
    /* 添加数据*/
    public void addMoreData(List<Repo> datas);
}
