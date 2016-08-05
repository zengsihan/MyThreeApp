package com.zsh.xuexi.mythreeapp.commons;

import com.zsh.xuexi.mythreeapp.entity.GankItem;

import java.util.List;

/**
 * Created by zsh on 2016/8/5.
 * 每日干货业务，视图接口
 */
public interface GankView {
    void showEmptyView();
    void hideEmptyView();
    void showMessage(String msg);
    void setData(List<GankItem> gankItems);
}
