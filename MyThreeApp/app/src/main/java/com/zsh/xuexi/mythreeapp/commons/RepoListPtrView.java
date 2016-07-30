package com.zsh.xuexi.mythreeapp.commons;

import android.view.View;

import java.util.List;

/**
 * Created by zsh on 2016/7/28.
 * 用于下拉刷新
 */
public interface RepoListPtrView {
    // 刷新的方法
    // 视图上:
    // 显示内容 or 错误 or 空白 , 三选一
     void showContentView();

     void showErrorView(String errorMsg);

     void showEmptyView();

    // 显示提示信息
    // 如：Toast， 直接在当前页面上页面
     void showMessage(String msg);

    //停止刷新，
     void stopRefresh();

    // 刷新数据
    // 将后台线程更新加载到的数据，刷新显示到视图(listview)上来显示给用户看
     void refreshData(List<String> data);
}
