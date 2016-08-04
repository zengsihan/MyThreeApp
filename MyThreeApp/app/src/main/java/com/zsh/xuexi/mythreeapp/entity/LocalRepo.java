package com.zsh.xuexi.mythreeapp.entity;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by zsh on 2016/8/4.
 * 本地cangk
 */
@DatabaseTable(tableName = "local_repo")
public class LocalRepo {
    public static final String COLUMN_GROUP_ID="group_id";

    @DatabaseField(id=true)
    private long id;

    @DatabaseField
    private String name;

    @SerializedName("full_name")
    private String fullName;

    @DatabaseField
    private String description;

    @SerializedName("stargazers_count")
    private int startCount;

    @SerializedName("forks_count")
    private int forkCount;

    @SerializedName("avatar_url")
    private String avatar;

    //是一个外键，可以为null
    @DatabaseField(columnName = COLUMN_GROUP_ID,foreign = true,canBeNull = true)
    @SerializedName("group")
    private RepoGroup repoGroup;

    /*获取本地默认仓库数据*/
    public static List<LocalRepo> getDefaultLocalRepos(Context context){
        try {
            InputStream inputStream=context.getAssets().open("defaultrepos.json");
            String content= IOUtils.toString(inputStream);
            Gson gson=new Gson();
            return gson.fromJson(content,new TypeToken<List<LocalRepo>>(){}.getType());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public RepoGroup getRepoGroup() {
        return repoGroup;
    }

    public void setRepoGroup(RepoGroup repoGroup) {
        this.repoGroup = repoGroup;
    }
}
