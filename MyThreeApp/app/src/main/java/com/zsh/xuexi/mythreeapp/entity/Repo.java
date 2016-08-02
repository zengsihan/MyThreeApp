package com.zsh.xuexi.mythreeapp.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by zsh on 2016/8/1.
 */
public class Repo implements Serializable {
    private int id;

    //仓库名称
    private String name;

    //仓库全名
    @SerializedName("full_name")
    private String fullName;

    //仓库描述
    private String description;

    //本仓库的star数量（在github上的被关注量）
    @SerializedName("startgazers_count")
    private int startCount;

    //本仓库的fork数量（在github上被拷贝的数量）
    @SerializedName("forks_count")
    private int forkCount;

    //该仓库的拥有者
    private User owner;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStartCount() {
        return startCount;
    }

    public void setStartCount(int startCount) {
        this.startCount = startCount;
    }

    public int getForkCount() {
        return forkCount;
    }

    public void setForkCount(int forkCount) {
        this.forkCount = forkCount;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
