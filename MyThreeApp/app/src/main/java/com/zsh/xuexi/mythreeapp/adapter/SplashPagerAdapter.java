package com.zsh.xuexi.mythreeapp.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.zsh.xuexi.mythreeapp.pager.Pager0;
import com.zsh.xuexi.mythreeapp.pager.Pager1;
import com.zsh.xuexi.mythreeapp.pager.Pager2;

/**
 * Created by zsh on 2016/7/26.
 */
public class SplashPagerAdapter extends PagerAdapter {
    private final View[] views;

    public SplashPagerAdapter(Context cont) {
        views=new View[]{
                new Pager0(cont),
                new Pager1(cont),
                new Pager2(cont)
        };
    }

    public View getView(int position) {
        return views[position];
    }

    @Override
    public int getCount() {
        return views.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(views[position],0);
        return views[position];
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
