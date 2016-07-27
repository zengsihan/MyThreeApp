package com.zsh.xuexi.mythreeapp.pager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.zsh.xuexi.mythreeapp.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zsh on 2016/7/26.
 */
public class Pager2 extends FrameLayout {

    @Bind(R.id.ivBubble1)ImageView ivBubble1;
    @Bind(R.id.ivBubble2)ImageView ivBubble2;
    @Bind(R.id.ivBubble3)ImageView ivBubble3;

    public ImageView getIvBubble1() {
        return ivBubble1;
    }

    public void setIvBubble1(ImageView ivBubble1) {
        this.ivBubble1 = ivBubble1;
    }

    public ImageView getIvBubble2() {
        return ivBubble2;
    }

    public void setIvBubble2(ImageView ivBubble2) {
        this.ivBubble2 = ivBubble2;
    }

    public ImageView getIvBubble3() {
        return ivBubble3;
    }

    public void setIvBubble3(ImageView ivBubble3) {
        this.ivBubble3 = ivBubble3;
    }

    public Pager2(Context context) {
        this(context, null);//调用当前类，两参构造
    }

    public Pager2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Pager2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init(){
        LayoutInflater.from(getContext()).inflate(R.layout.content_pager_2,this,true);
        ButterKnife.bind(this);
        ivBubble1.setVisibility(View.GONE);
        ivBubble2.setVisibility(View.GONE);
        ivBubble3.setVisibility(View.GONE);
    }

    //用来显示当前页面内三张图像的进入动画，只显示一次
    public void showAnimation(){
        if(ivBubble1.getVisibility() != View.VISIBLE){
            postDelayed(new Runnable() {
                @Override public void run() {
                    ivBubble1.setVisibility(View.VISIBLE);
                    YoYo.with(Techniques.FadeInLeft).duration(300).playOn(ivBubble1);
                }
            }, 50);
            postDelayed(new Runnable() {
                @Override public void run() {
                    ivBubble2.setVisibility(View.VISIBLE);
                    YoYo.with(Techniques.FadeInLeft).duration(300).playOn(ivBubble2);
                }
            }, 550);
            postDelayed(new Runnable() {
                @Override public void run() {
                    ivBubble3.setVisibility(View.VISIBLE);
                    YoYo.with(Techniques.FadeInLeft).duration(300).playOn(ivBubble3);
                }
            }, 1050);
        }
    }
}
