package com.zsh.xuexi.mythreeapp.http;

import com.zsh.xuexi.mythreeapp.entity.AccessTokenResult;
import com.zsh.xuexi.mythreeapp.entity.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by zsh on 2016/7/28.
 *
 * Retrofit能将标准的reset接口，用Java接口来描述(通过注解),
 *
 * 通过Retrofit的create方法，去创建Call模
 */
public interface GitHubApi {
    // 请求方式:Get
    // 请求路径:https://api.github.com/user
    // 请求参数：无
    // 请求头：无(其实OKHTTP内部会帮我们做一些基本数据补全)
    // 最终首次构建完成了一个Call模型
//    @GET("repos/square/retrofit/contributors")
//    Call<ResponseBody> gitHub();

    String CALL_BACK ="zsh";
    String CLIENT_ID ="fcd4c34664424bd2c0eb";
    String CLIENT_SECRET = "6cd41e6964afe3845e8a21941ff8b8ad5137928c";

    String AUTH_SCOPE="user,public_repo,repo";

    //授权登录页面（用webview加载）
    String AUTH_URL = "https://github.com/login/oauth/authorize?client_id=" + CLIENT_ID + "&scope=" + AUTH_SCOPE;

    //获取token api
    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("https://github.com/login/oauth/access_token")
    Call<AccessTokenResult> getOAuthToken(
            @Field("client_id")String client,
            @Field("client_secret")String clientSecret,
            @Field("code")String code);

    @GET("user")
    Call<User> getUserInfo();
}
