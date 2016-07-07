package com.feicuiedu.gitdroid.httpclient;

/**
 * Created by zhengshujuan on 2016/7/6.
 * //保存Token
 */
public class CurrentUser {
    private CurrentUser(){}
    private static User user;
    private static String accessToken;
    public static void setAccessToken(String accessToken){
        CurrentUser.accessToken=accessToken;
    }
    public static void setUser(User user){
        CurrentUser.user=user;
    }
    //清除缓存的信息
    public static void clear(){
        accessToken=null;
        user=null;
    }
    public static  String getAccessToken(){
        return accessToken;
    }
    //当前是否有访问令牌
    public static boolean hasAccessToken(){
        return accessToken!=null;
    }
    public static boolean isEmpty(){
        return accessToken==null||user==null;
    }
    public static User getUser(){
        return user;
    }


}






