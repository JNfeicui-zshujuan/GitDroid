package com.feicuiedu.gitdroid.httpclient;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zhengshujuan on 2016/7/6.
 * //获取到的Token,访问令牌
 */
public class AccessTokenResult {
    @SerializedName("access_token")
private String accessToken;
    @SerializedName("token_type")
    private String tokenType;

    public String getScope() {
        return scope;
    }

    public String getTokenType() {
        return tokenType;
    }

    public String getAccessToken() {
        return accessToken;
    }

    @SerializedName("scope")
    private String scope;


}
