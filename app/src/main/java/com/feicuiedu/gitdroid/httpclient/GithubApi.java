package com.feicuiedu.gitdroid.httpclient;

/**
 * Created by zhengshujuan on 2016/7/5.
 */
public interface GithubApi {
    //GitHu开发者,申请
    String GLIENT_ID="c360b015ef058902248b";
    //授权是可申请的可访问域
    String INIITAL_SCOPE="user,public_repo,repo";
    //WebView来加载此url,用来显示gethub的登录界面
    //https://github.com/login/oauth/authorize?client_id=c360b015ef058902248b&scope=user,public_repo,repo
    String ANTH_URL="https://github.com/login/oauth/authorize?client_id="+
            GLIENT_ID+"&"+"scope="+INIITAL_SCOPE;
}
