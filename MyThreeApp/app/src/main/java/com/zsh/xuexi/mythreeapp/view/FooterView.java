package com.zsh.xuexi.mythreeapp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zsh.xuexi.mythreeapp.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zsh on 2016/7/28.
 * 本视图用来实现上拉功能视图，
 *
 * 当ListView滚动到尾部时，我们将在会过listView的addFooterView方法将此视图添加上去，
 *
 * 并且将在后台线程去加更多数据
 *
 * 此视图将考虑三种不同状态：
 * 展示loading (进度条)
 * 显示错误（当点击时，重新load）
 * 显示没有更多数据
 */
public class FooterView extends FrameLayout {
    private static final int STATE_LOADING=0;
    private static final int STATE_COMPLETE=1;
    private static final int STATE_ERROR=2;

    private int state=STATE_LOADING;//用于状态判断

    @Bind(R.id.progressBar) ProgressBar progressBar;//进度条
    @Bind(R.id.tv_complete) TextView tvComplete;//没有数据
    @Bind(R.id.tv_error) TextView tvError;//错误

    public FooterView(Context cont) {
        this(cont, null);
    }

    public FooterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FooterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.content_load_footer, this, true);
        ButterKnife.bind(this);
    }
    /*是否正在加载中*/
    public boolean isLoading(){
        return state==STATE_LOADING;
    }

    /*是否加载完成（没有更多数据）*/
    public boolean isComplete(){
        return state==STATE_COMPLETE;
    }

    //显示加载中
    public void showLoading(){
        state=STATE_LOADING;
        progressBar.setVisibility(View.VISIBLE);
        tvComplete.setVisibility(View.GONE);
        tvError.setVisibility(View.GONE);
    }

    /*显示加载完成（没有更多数据时）*/
    public void showComlpete(){
        state=STATE_COMPLETE;
        tvComplete.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        tvError.setVisibility(View.GONE);
    }

    /*显示没有更多数据*/
    public void showError(String errorMsg){
        state=STATE_ERROR;
        tvError.setVisibility(View.VISIBLE);
        tvComplete.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
    }
    /*点击错误，重新加载数据的监听*/
    public void sertErrorClickListener(OnClickListener onClickListener){
        tvError.setOnClickListener(onClickListener);
    }

}
