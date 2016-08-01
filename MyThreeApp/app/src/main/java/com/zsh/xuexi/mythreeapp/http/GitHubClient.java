package com.zsh.xuexi.mythreeapp.http;

import com.zsh.xuexi.mythreeapp.entity.AccessTokenResult;
import com.zsh.xuexi.mythreeapp.entity.RepoResult;
import com.zsh.xuexi.mythreeapp.entity.User;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.Query;

/**
 * 用Retrofit的话，能基本表现出很多的HTTP基本知识
 * Created by zsh on 2016/7/28.
 */
public class GitHubClient implements GitHubApi {

    private GitHubApi gitHubApi;
    private static GitHubClient gitHubClient;

    public static GitHubClient getInstancec(){
        if(gitHubClient==null){
            gitHubClient=new GitHubClient();
        }
        return gitHubClient;
    }

    private GitHubClient(){
        OkHttpClient okHttpClient=new OkHttpClient.Builder()
                                        //添加token拦截器
                                        .addInterceptor(new TokenIntercepter())
                                        .build();

        Retrofit retrofit=new Retrofit.Builder()
                                .baseUrl("https://api.github.com/")
                                .client(okHttpClient)
                                //Gson转换器
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
        //构建API
        gitHubApi= retrofit.create(GitHubApi.class);
    }

    @Override
    public Call<AccessTokenResult> getOAuthToken(@Field("client_id") String client, @Field("client_secret") String clientSecret, @Field("code") String code) {
        return gitHubApi.getOAuthToken(client,clientSecret,code);
    }

    @Override
    public Call<User> getUserInfo() {
        return gitHubApi.getUserInfo();
    }

    @Override
    public Call<RepoResult> searchRepos(@Query("q") String query, @Query("page") int pageId) {
        return gitHubApi.searchRepos(query,pageId);
    }
}
