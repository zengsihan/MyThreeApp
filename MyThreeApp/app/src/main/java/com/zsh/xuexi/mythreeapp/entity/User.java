package com.zsh.xuexi.mythreeapp.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by zsh on 2016/7/29.
 *  个人用户信息响应结果
 */
public class User implements Serializable {
    private String login;//登陆所用的账号
    private String name;//用户名
    private int id;
    //用户头像路径
    @SerializedName("avatar_url")
    private String avatar;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", name='" + name + '\'' +
                ", id=" + id +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
