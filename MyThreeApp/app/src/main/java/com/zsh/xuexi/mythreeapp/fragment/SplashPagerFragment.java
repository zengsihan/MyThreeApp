package com.zsh.xuexi.mythreeapp.fragment;

import android.animation.ArgbEvaluator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.zsh.xuexi.mythreeapp.R;
import com.zsh.xuexi.mythreeapp.adapter.SplashPagerAdapter;
import com.zsh.xuexi.mythreeapp.pager.Pager2;

import me.relex.circleindicator.CircleIndicator;

/**
 * Created by zsh on 2016/7/26.
 */
public class SplashPagerFragment extends Fragment {
    private ViewPager vp;
    private SplashPagerAdapter adapter;
    private CircleIndicator indicator;//指示器

    private FrameLayout frameLayout;//当前页面layout（主要为了更新其背景颜色)
    private FrameLayout layoutPhone;//屏幕中央的“手机”
    private ImageView ivPhoneFont;

    private int colorGreen,colorRed,colorYellow;

    DisplayMetrics dm=null;//用户获取手机屏幕宽高

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_splash_pager,container,false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        colorGreen=getResources().getColor(R.color.colorGreen);
        colorRed=getResources().getColor(R.color.colorRed);
        colorYellow=getResources().getColor(R.color.colorYellow);

        layoutPhone= (FrameLayout) view.findViewById(R.id.layoutPhone);
        ivPhoneFont= (ImageView) view.findViewById(R.id.ivPhoneFont);

        vp= (ViewPager) view.findViewById(R.id.viewPager);
        indicator= (CircleIndicator) view.findViewById(R.id.indicator);
        frameLayout= (FrameLayout) view.findViewById(R.id.content);
        adapter=new SplashPagerAdapter(getContext());
        vp.setAdapter(adapter);
        indicator.setViewPager(vp);
        //添加viewpager监听，为动画准备,add,可以添加多个
        vp.addOnPageChangeListener(pageChangeListener);
        vp.addOnPageChangeListener(phoneViewListener);

        //获取屏幕信息
        dm=new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
    }

    //为做背景颜色渐变处理
    private ViewPager.OnPageChangeListener pageChangeListener=new ViewPager.OnPageChangeListener() {
        final ArgbEvaluator argbEvaluator=new ArgbEvaluator();
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            //第一个页面到第二个页面之间
            if(position==0){
                int color= (int) argbEvaluator.evaluate(positionOffset,colorGreen,colorRed);//颜色渐变
                frameLayout.setBackgroundColor(color);
                return;
            }
            //第二个页面到第三个页面之间
            if(position==1){
                int color= (int) argbEvaluator.evaluate(positionOffset,colorRed,colorYellow);
                frameLayout.setBackgroundColor(color);
            }

        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    //为做手机的动画效果处理
    private ViewPager.OnPageChangeListener phoneViewListener=new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            //第一个页面到第二个页面之间
            if(position==0){
                //手机的缩放处理
                float scale=0.3f+positionOffset*0.7f;
                layoutPhone.setScaleX(scale);
                layoutPhone.setScaleY(scale);
                //手机的平移
//                int scroll= (int) ((positionOffset-1)*300);//可能会出现适配问题
                //最好通过获取手机屏幕宽高来处理
                int scroll= (int) ((positionOffset-1)*(dm.widthPixels/3));
                layoutPhone.setTranslationX(scroll);
                //手机字体的渐变
                ivPhoneFont.setAlpha(positionOffset);
                return;
            }
            //第二个页面和第三个页面之间
            if(position==1){
                layoutPhone.setTranslationX(-positionOffsetPixels);//让他消失
            }

        }

        @Override
        public void onPageSelected(int position) {
            //当显示最后一个pager时，播放他自己的动画
            //改成每次到第三页都有动画
            Pager2 pager2= (Pager2) adapter.getView(2);
            if(position==2){//进入第三页执行动画
//                Pager2 pager2= (Pager2) adapter.getView(position);
                pager2.showAnimation();
            }else{//返回其他两页时隐藏组件，为再次执行动画做准备
                if(pager2.getIvBubble1().getVisibility()==View.VISIBLE)
                {
                    pager2.getIvBubble1().setVisibility(View.GONE);
                    pager2.getIvBubble2().setVisibility(View.GONE);
                    pager2.getIvBubble3().setVisibility(View.GONE);
                }
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
