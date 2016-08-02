package com.zsh.xuexi.mythreeapp.commons;

import com.zsh.xuexi.mythreeapp.entity.User;

import java.util.List;

/**
 * Created by zsh on 2016/8/2.
 */
public interface UserListPreView {
    /** 显示下拉刷新时的内容视图*/
    void showContentView();
    /** 显示下拉刷新时的错误视图*/
    void showErrorView(String errorMsg);
    /** 显示下拉刷新时的空视图*/
    void showEmptyView();
    void showMessage(String msg);
    void stopRefresh();
    void refreshData(List<User> data);
}
