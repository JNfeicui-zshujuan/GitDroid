package com.feicuiedu.gitdroid.httpclient;

import com.feicuiedu.gitdroid.hotrepositor.RepoResultAPI;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.Query;

/**
 * Created by zhengshujuan on 2016/7/5.
 */
public class GitHubClient implements GithubApi {
    private static GitHubClient sClient;
    private GithubApi githubApi;

    //获取GitHubClient的实例
    public static GitHubClient getInstance() {
        if (sClient == null) {
            sClient = new GitHubClient();
        }
        return sClient;
    }

    private GitHubClient() {
        //创建一个okhttp的实例
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
        //添加拦截器,处理Token
                .addInterceptor(new TokenInterceptor())
                .build();
        //创建一个retrofit的实例.将请求到的okhttp进行内部解析
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        githubApi = retrofit.create(GithubApi.class);
    }

    @Override
    public Call<AccessTokenResult> getOAuthToken(@Field("client_id") String client, @Field("client_secret") String clientSecret, @Field("code") String code) {
        return githubApi.getOAuthToken(client, clientSecret, code);
    }

    @Override
    public Call<User> getUserInfo() {
        return githubApi.getUserInfo();
    }

    @Override
    public Call<RepoResultAPI> searchRepo(@Query("q") String query, @Query("page") int pageId) {
        return githubApi.searchRepo(query, pageId);
    }
}
