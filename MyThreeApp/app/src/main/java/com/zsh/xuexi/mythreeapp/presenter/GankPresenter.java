package com.zsh.xuexi.mythreeapp.presenter;

import android.support.annotation.NonNull;
import android.widget.GridView;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.zsh.xuexi.mythreeapp.commons.GankView;
import com.zsh.xuexi.mythreeapp.entity.GankItem;
import com.zsh.xuexi.mythreeapp.entity.GankResult;
import com.zsh.xuexi.mythreeapp.http.GankClient;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zsh on 2016/8/5.
 */
public class GankPresenter {
    private Call<GankResult> call;
    private GankView gankView;
    public GankPresenter(@NonNull GankView gankView){
        this.gankView=gankView;
    }
//    获取每日干货数据,通过日期
    public void getGanks(Date date){
        //得到year，month，day
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH)+1;
        int day=calendar.get(Calendar.DAY_OF_MONTH);
        call= GankClient.getInstance().getDailyData(year,month,day);
        call.enqueue(callback);
    }
    private Callback<GankResult> callback=new Callback<GankResult>() {
        @Override
        public void onResponse(Call<GankResult> call, Response<GankResult> response) {
            GankResult gankResult=response.body();
            if(gankResult==null){
                gankView.showMessage("未知错误！");
                return;
            }
            //没有数据的情况
            if(gankResult.isError()||
                    gankResult.getResults()==null||
                    gankResult.getResults().getAndroidItems()==null||
                    gankResult.getResults().getAndroidItems().isEmpty()){
                gankView.showEmptyView();
                return;
            }
            List<GankItem> gankItems=gankResult.getResults().getAndroidItems();
            //讲获取到的今天干货数据交付给是视图
            gankView.hideEmptyView();
            gankView.setData(gankItems);
        }

        @Override
        public void onFailure(Call<GankResult> call, Throwable t) {
            gankView.showMessage("error:"+t.getMessage());
        }
    };


}
