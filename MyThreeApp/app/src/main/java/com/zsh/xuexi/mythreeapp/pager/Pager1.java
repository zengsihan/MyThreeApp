package com.zsh.xuexi.mythreeapp.pager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.zsh.xuexi.mythreeapp.R;

/**
 * Created by zsh on 2016/7/26.
 */
public class Pager1 extends FrameLayout {
    public Pager1(Context context) {
        this(context, null);//调用当前类，两参构造
    }

    public Pager1(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Pager1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init(){
        LayoutInflater.from(getContext()).inflate(R.layout.content_pager_1,this,true);
    }
}
