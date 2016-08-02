package com.zsh.xuexi.mythreeapp.presenter;

import com.zsh.xuexi.mythreeapp.commons.LogUtils;
import com.zsh.xuexi.mythreeapp.commons.LoginView;
import com.zsh.xuexi.mythreeapp.entity.AccessTokenResult;
import com.zsh.xuexi.mythreeapp.entity.User;
import com.zsh.xuexi.mythreeapp.entity.UserRepo;
import com.zsh.xuexi.mythreeapp.http.GitHubApi;
import com.zsh.xuexi.mythreeapp.http.GitHubClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zsh on 2016/7/29.
 */
public class LoginPresenter {

    private Call<AccessTokenResult> tokenCall;
    private Call<User> userCall;
    private LoginView loginView;

    public LoginPresenter(LoginView loginView) {
        this.loginView = loginView;
    }

    //登陆（先用code换token，在用token换用户信息）
    public void login(String code){
        loginView.showProgress();//显示进度条
        if(tokenCall!=null){
            tokenCall.cancel();
        }
        //获取token
        tokenCall= GitHubClient.getInstance().getOAuthToken(GitHubApi.CLIENT_ID,GitHubApi.CLIENT_SECRET,code);
        tokenCall.enqueue(tokenCallback);
    }
    private Callback<AccessTokenResult> tokenCallback=new Callback<AccessTokenResult>() {
        @Override//token接口响应
        public void onResponse(Call<AccessTokenResult> call, Response<AccessTokenResult> response) {
            //响应体内数据（结果）
            AccessTokenResult result=response.body();
            String token=result.getAccessToken();
            LogUtils.d("token="+token);

            //缓存token
            UserRepo.setAccessToken(token);
            //再用次token执行获取用户信息接口，拿到用户信息。
            if(userCall!=null){
                userCall.cancel();
            }
            userCall=GitHubClient.getInstance().getUserInfo();
            userCall.enqueue(userCallback);
        }

        @Override
        public void onFailure(Call<AccessTokenResult> call, Throwable t) {
            LogUtils.d("tokenCallback onFailure"+ t.getMessage());
            loginView.showMessage(t.getMessage());
            loginView.showProgress();
            loginView.resetWeb();
        }
    };

    private Callback<User> userCallback=new Callback<User>() {
        @Override
        public void onResponse(Call<User> call, Response<User> response) {
            User user=response.body();
            LogUtils.d("user="+ user);
            //缓存user
            UserRepo.setUser(user);
            loginView.showMessage("登陆成功");
            loginView.navigateToMain();
        }

        @Override
        public void onFailure(Call<User> call, Throwable t) {
            LogUtils.d("userCallback onFailure "+ t.getMessage());
            loginView.showMessage(t.getMessage());
            loginView.showProgress();
            loginView.resetWeb();
        }
    };
}
