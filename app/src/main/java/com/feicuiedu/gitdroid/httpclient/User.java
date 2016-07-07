package com.feicuiedu.gitdroid.httpclient;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zhengshujuan on 2016/7/6.
 */
public class User {
    private String login;
    private String name;
    private int id;

    public String getAvatar() {
        return avatar;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLogin() {
        return login;
    }

    //用户头像的路径
    @SerializedName("avatar_url")
    private String avatar;

}
