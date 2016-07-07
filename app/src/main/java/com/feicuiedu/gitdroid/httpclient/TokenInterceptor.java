package com.feicuiedu.gitdroid.httpclient;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by zhengshujuan on 2016/7/6.
 * 这是一个拦截token的类
 */
public class TokenInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        //每次请求都加一个头进去
        //token值
        Request request = chain.request();
        //拿到request的构建
        Request.Builder builder = request.newBuilder();
        //是否有token,有在当前这次请求中加入token这个请求头
        if (CurrentUser.hasAccessToken()) {
            //添加的请求头格式必须正确,(和服务器的header保持一致)
            builder.header("Authorization", "token " + CurrentUser.getAccessToken());
        }
        Response response = chain.proceed(builder.build());
        if (response.isSuccessful()) {
            return response;
        }
        if (response.code() == 401 || response.code() == 403) {
            throw new IOException("未经授权,限制是每分钟10次");
        } else {
            throw new IOException("响应码:" + response.code());
        }

    }
}
