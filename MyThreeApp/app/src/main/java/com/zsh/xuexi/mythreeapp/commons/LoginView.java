package com.zsh.xuexi.mythreeapp.commons;

/**
 * Created by zsh on 2016/7/30.
 */
public interface LoginView {
    //显示进度
    void showProgress();

    //显示信息
    void showMessage(String msg);

    //重置webview
    void resetWeb();

    //导航切换至main页面
    void navigateToMain();

}
