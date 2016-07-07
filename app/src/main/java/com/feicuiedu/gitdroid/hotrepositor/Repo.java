package com.feicuiedu.gitdroid.hotrepositor;

import com.feicuiedu.gitdroid.httpclient.User;
import com.google.gson.annotations.SerializedName;

/**
 * Created by zhengshujuan on 2016/7/6.
 */
public class Repo {

    public int getId() {
        return id;
    }

    public int getStargazerCount() {
        return stargazerCount;
    }

    public int getForksCount() {
        return forksCount;
    }
    private int id;
    //仓库名称
    @SerializedName("name")
    private String name;
    //仓库全名
    @SerializedName("full_name")
    private String fullName;
    public String getDescription() {
        return description;
    }

    public String getFullName() {
        return fullName;
    }

    public String getName() {
        return name;
    }
//仓库描述
    @SerializedName("description")
    private String description;
    //在github上被关注的数量
    @SerializedName("stargazers_count")
    private int stargazerCount;
    //被拷贝的数量
    @SerializedName("forks_count")
    private int forksCount;
    //本仓库的拥有者
    @SerializedName("owner")
    private User owner;
    public User getOwner() {
        return owner;
    }
}
