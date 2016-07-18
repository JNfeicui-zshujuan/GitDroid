package com.feicuiedu.gitdroid.favorite;

import android.content.Context;

import com.feicuiedu.gitdroid.favorite.model.RepoGroup;
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
 * Created by zhengshujuan on 2016/7/8.
 * 本地仓库表
 */
@DatabaseTable(tableName = "repositor")
public class LocalRepo {
    //主键
    @DatabaseField(id = true)
    private long id;


    public String getFullName() {
        return fullName;
    }

    public long getId() {
        return id;
    }


    //仓库全名
    @DatabaseField(columnName = "full_name")
    @SerializedName("full_name")
    private String fullName;
    public static final String COLUMN_GROUP_ID = "group_id";
    //仓库描述
    //去另外一个表取数据,是一个外键
    @DatabaseField(canBeNull = true, foreign = true, columnName = COLUMN_GROUP_ID)
    @SerializedName("group")
    private RepoGroup repoGroup;

    //仓库名称
    @DatabaseField
    private String name;
    //仓库描述
    @DatabaseField
    private String description;

    public int getForkCount() {
        return forkCount;
    }

    public void setForkCount(int forkCount) {
        this.forkCount = forkCount;
    }

    public int getStarCount() {
        return starCount;
    }

    public void setStarCount(int starCount) {
        this.starCount = starCount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RepoGroup getRepoGroup() {
        return repoGroup;
    }

    public void setRepoGroup(RepoGroup repoGroup) {
        this.repoGroup = repoGroup;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setId(long id) {
        this.id = id;
    }

    //本仓库的star数量
    @SerializedName("stargazers_count")
    @DatabaseField(columnName = "stargazers_count")
    private int starCount;
    //本仓库的fork数量
    @SerializedName("forks_count")
    @DatabaseField(columnName = "forks_count")
    private int forkCount;

    public String getAvatatr() {
        return avatatr;
    }

    public void setAvatatr(String avatatr) {
        this.avatatr = avatatr;
    }

    //用户头像设置
    @SerializedName("avatar_url")
    @DatabaseField(columnName = "avatar_url")
    private String  avatatr;
    public boolean equals(Object o){
        return o!=null&&o instanceof LocalRepo&&this.id==((LocalRepo) o).getId();
    }
    public static List<LocalRepo> getDefaultLocalRepo(Context context){
        try {
            InputStream inputStream=context.getAssets().open("defaultrepos.json");
            String content = IOUtils.toString(inputStream);
            Gson gson=new Gson();
             return gson.fromJson(content, new TypeToken<List<LocalRepo>>() {
            }.getType());
        } catch (IOException e) {
           throw new RuntimeException(e);
        }
    }
}
