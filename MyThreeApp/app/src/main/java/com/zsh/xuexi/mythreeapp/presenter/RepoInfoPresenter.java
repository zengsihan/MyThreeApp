package com.zsh.xuexi.mythreeapp.presenter;

import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.Log;

import com.zsh.xuexi.mythreeapp.commons.RepoInfoView;
import com.zsh.xuexi.mythreeapp.entity.Repo;
import com.zsh.xuexi.mythreeapp.entity.RepoContentResult;
import com.zsh.xuexi.mythreeapp.http.GitHubClient;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zsh on 2016/8/1.
 * 业务（视图业务和逻辑业务）
 */
public class RepoInfoPresenter {
    private RepoInfoView repoInfoView;
    private Call<RepoContentResult> repoContentCall;
    private Call<ResponseBody> mdhtmlCall;

    public RepoInfoPresenter(@Nullable RepoInfoView repoInfoView){
        this.repoInfoView=repoInfoView;
    }

    public void getReadme(Repo repo){
        repoInfoView.showProgrss();
        String login=repo.getOwner().getLogin();
        String name=repo.getName();
        if(repoContentCall!=null){
            repoContentCall.cancel();
        }
        repoContentCall= GitHubClient.getInstancec().getReadme(login,name);
        repoContentCall.enqueue(repoContentCallback);
    }
    private Callback<RepoContentResult> repoContentCallback=new Callback<RepoContentResult>() {
        @Override
        public void onResponse(Call<RepoContentResult> call, Response<RepoContentResult> response) {
            String content=response.body().getContent();
            Log.i("ms","content="+content);
            //BASE64解码
            byte[] data= Base64.decode(content,Base64.DEFAULT);
            //根据data获取到markdown（也就是readme文件）的html格式文件
            MediaType mediaType=MediaType.parse("text/plain");
            RequestBody body=RequestBody.create(mediaType,data);
            Log.i("ms","body="+body);
            if(mdhtmlCall!=null){
                mdhtmlCall.cancel();
            }
            mdhtmlCall=GitHubClient.getInstancec().markDown(body);
            mdhtmlCall.enqueue(mdhtmlCallback);
        }

        @Override
        public void onFailure(Call<RepoContentResult> call, Throwable t) {
            repoInfoView.hideProgress();
            repoInfoView.showMessage(t.getMessage());
        }
    };
    private Callback<ResponseBody> mdhtmlCallback=new Callback<ResponseBody>() {
        @Override
        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            repoInfoView.hideProgress();
            try {
                String htmlContent=response.body().string();
                repoInfoView.setData(htmlContent);
            } catch (IOException e) {
                onFailure(call,e);
            }
        }

        @Override
        public void onFailure(Call<ResponseBody> call, Throwable t) {
            repoInfoView.hideProgress();
            repoInfoView.showMessage(t.getMessage());
        }
    };

}
