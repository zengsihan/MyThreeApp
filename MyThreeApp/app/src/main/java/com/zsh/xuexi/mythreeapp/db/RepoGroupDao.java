package com.zsh.xuexi.mythreeapp.db;

import com.j256.ormlite.dao.Dao;
import com.zsh.xuexi.mythreeapp.entity.RepoGroup;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by zsh on 2016/8/3.
 * 仓库类别表的Dao
 */
public class RepoGroupDao {
    private  Dao<RepoGroup,Long> dao;

    public RepoGroupDao(DbHelper dbHelper){
        try {
            //创建出仓库类别表的Dao
            dao=dbHelper.getDao(RepoGroup.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** 添加和更新 仓库类别表*/
    public void createOrUpdata(RepoGroup table){
        try {
            dao.createOrUpdate(table);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** 添加和更新 仓库类别表*/
    public void createOrUpdata(List<RepoGroup> tables){
        for(RepoGroup table:tables){
            createOrUpdata(table);
        }
    }

    /*查询指定id仓库类别*/
    public RepoGroup queryForID(long id){
        try {
            return dao.queryForId(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /*查询所有，仓库类别*/
    public List<RepoGroup> queryForAll(){
        try {
            return dao.queryForAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
