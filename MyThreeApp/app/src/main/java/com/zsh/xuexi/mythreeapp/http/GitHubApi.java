package com.zsh.xuexi.mythreeapp.http;

import com.zsh.xuexi.mythreeapp.entity.AccessTokenResult;
import com.zsh.xuexi.mythreeapp.entity.RepoContentResult;
import com.zsh.xuexi.mythreeapp.entity.RepoResult;
import com.zsh.xuexi.mythreeapp.entity.User;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by zsh on 2016/7/28.
 *
 * Retrofit能将标准的reset接口，用Java接口来描述(通过注解),
 *
 * 通过Retrofit的create方法，去创建Call模
 */
public interface GitHubApi {
    // GitHub开发者，申请时填写的(重定向返回时的一个标记)
    String CALL_BACK ="zsh";

    // GitHub开发者，申请就行
    String CLIENT_ID ="fcd4c34664424bd2c0eb";
    String CLIENT_SECRET = "6cd41e6964afe3845e8a21941ff8b8ad5137928c";

    // 授权时申请的可访问域
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

//    获取用户信息
    @GET("user")
    Call<User> getUserInfo();

    /**
     * 获取仓库
     * @Param query 查询参数(language:java)
     * @Param pageId 查询页数据(从1开始)
     */
    @GET("search/repositories")
    Call<RepoResult> searchRepos(@Query("q")String query, @Query("page")int pageId);

    /***
     * 获取readme
     * @param owner 仓库拥有者
     * @param repo 仓库名称
     * @return 仓库的readme页面内容,将是markdown格式且做了base64处理
     */
    @GET("/repos/{owner}/{repo}/readme")
    Call<RepoContentResult> getReadme(@Path("owner") String owner,@Path("repo") String repo);

    @Headers({"Content-Type:text/plain"})
    @POST("/markdown/raw")
    Call<ResponseBody> markDown(@Body RequestBody body);
}
