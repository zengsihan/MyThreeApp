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
 * Created by zsh on 2016/8/3.
 * 仓库类别表
 */
@DatabaseTable(tableName = "repostiory_group")
public class RepoGroup {
    //主键
    @DatabaseField(id=true)
    @SerializedName("id")//名字一样可以不用写
    private int id;

    @DatabaseField(columnName = "name")
    private String name;

    private static List<RepoGroup> defaultGroups;

    public static List<RepoGroup> getDefaultGroups(Context context){
        if(defaultGroups!=null){
            return defaultGroups;
        }
        try {
            InputStream inputStream=context.getAssets().open("repogroup.json");
            String content= IOUtils.toString(inputStream);
            Gson gson=new Gson();
            defaultGroups=gson.fromJson(content,new TypeToken<List<RepoGroup>>(){}.getType());
            return defaultGroups;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

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
}
