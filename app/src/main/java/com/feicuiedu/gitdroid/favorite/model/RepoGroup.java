package com.feicuiedu.gitdroid.favorite.model;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by zhengshujuan on 2016/7/8.
 * 本地仓库...收藏...类别表
 */
@DatabaseTable(tableName = "repostiory_groups")
public class RepoGroup {
    @DatabaseField(id=true)
    private int id;

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    @DatabaseField(columnName = "Name")
    private String name;


     static List<RepoGroup> DEFULT_GROUP;
    public static List<RepoGroup> getDefultGroup(Context context) throws IOException {
        if (DEFULT_GROUP!=null) return DEFULT_GROUP;
        InputStream inputStream=context.getAssets().open("repogroup.json");
        String content= IOUtils.toString(inputStream);
        Gson gson=new Gson();
        DEFULT_GROUP=gson.fromJson(content,new TypeToken<List<RepoGroup>>(){}.getType());
        return DEFULT_GROUP;
    }
}
