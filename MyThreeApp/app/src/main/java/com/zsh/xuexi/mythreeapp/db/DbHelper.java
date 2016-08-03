package com.zsh.xuexi.mythreeapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.zsh.xuexi.mythreeapp.entity.RepoGroup;

import java.sql.SQLException;

/**
 * Created by zsh on 2016/8/3.
 */
public class DbHelper extends OrmLiteSqliteOpenHelper {
    private static final String NAME="repo_favorite.db";
    private static final int VERSION=2;

    private  Context context;
    private static DbHelper dbHelper;

    private DbHelper(Context context) {
        super(context, NAME, null, VERSION);
        this.context=context;
    }
    public static synchronized DbHelper getInstance(Context context){
        if(dbHelper==null){
            dbHelper=new DbHelper(context.getApplicationContext());
        }
        return dbHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            //创建表
            TableUtils.createTableIfNotExists(connectionSource, RepoGroup.class);
            //将本地的默认数据添加到表里去
            new RepoGroupDao(this).createOrUpdata(RepoGroup.getDefaultGroups(context));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource,RepoGroup.class,true);
            onCreate(database,connectionSource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
