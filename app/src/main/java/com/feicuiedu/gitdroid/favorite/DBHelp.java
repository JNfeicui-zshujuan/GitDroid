package com.feicuiedu.gitdroid.favorite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.feicuiedu.gitdroid.favorite.model.LocalRepoDao;
import com.feicuiedu.gitdroid.favorite.model.RepoGroup;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by zhengshujuan on 2016/7/8.
 */
public class DBHelp extends OrmLiteSqliteOpenHelper {
    private Context context;
    private static final int VERSION = 3;
    private static final String TABLE_NAME="repo_favourite.db";
    //创建DBHelp的单利模式
    private static DBHelp sInstance;
 public static synchronized DBHelp getInstance(Context context){
     if (sInstance==null){
         sInstance=new DBHelp(context);
     }
     return sInstance;
 }

    public DBHelp(Context context) {
        super(context, TABLE_NAME, null, VERSION);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        //创建表
        try {
            TableUtils.createTableIfNotExists(connectionSource, RepoGroup.class);
            TableUtils.createTableIfNotExists(connectionSource,LocalRepo.class);
            //将本地数据(本地默认类别)添加到表里
            new RepoGroupDao(this).createOrUpdate(RepoGroup.getDefultGroup(context));
            new LocalRepoDao(this).createOrUpdata(LocalRepo.getDefaultLocalRepo(context));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, RepoGroup.class,true);
            TableUtils.dropTable(connectionSource,LocalRepo.class,true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        onCreate(database, connectionSource);
    }
}
