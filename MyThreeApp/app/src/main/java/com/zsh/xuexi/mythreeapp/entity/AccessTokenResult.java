package com.zsh.xuexi.mythreeapp.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zsh on 2016/7/29.
 * 授权登陆响应结果
 */
public class AccessTokenResult {
    @SerializedName("access_token")
    private String accessToken;
    @SerializedName("scope")
    private String scope;
    @SerializedName("taken_type")
    private String tokenType;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}
