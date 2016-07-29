package com.zsh.xuexi.mythreeapp.http;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

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
    @GET("repos/square/retrofit/contributors")
    Call<ResponseBody> gitHub();
}
