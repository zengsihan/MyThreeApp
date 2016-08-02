package com.zsh.xuexi.mythreeapp.commons;

/**
 * Created by zsh on 2016/8/1.
 */
public interface RepoInfoView {
    void showProgrss();
    void hideProgress();
    void showMessage(String msg);
    void setData(String htmlContent);
}
