package com.feicuiedu.gitdroid.httpclient;

import com.feicuiedu.gitdroid.hotrepositor.RepoResultAPI;
import com.feicuiedu.gitdroid.readme.RepoContentResult;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by zhengshujuan on 2016/7/5.
 */
public interface GithubApi {
    //申请是填写的
    String CALL_BACK = "feicui";
    //GitHu开发者,申请
    String GLIENT_ID = "fd89376db5acf26b1c86";
    String GLIENT_SECRET = "0cf5f191a60b9c68d9228890658d5f8ac58ecd79";
    //授权是可申请的可访问域
    String INIITAL_SCOPE = "user,public_repo,repo";
    //WebView来加载此url,用来显示gethub的登录界面
    //https://github.com/login/oauth/authorize?client_id=c360b015ef058902248b&scope=user,public_repo,repo
    String ANTH_URL = "https://github.com/login/oauth/authorize?client_id=" +
            GLIENT_ID + "&" + "scope=" + INIITAL_SCOPE;

    //获取访问令牌API,(保存获取到的token ,要增加一个token 的实体类-AccessTokenResult)
    //授权结果
    //https://github.com/login/oauth/access_token
    @Headers("Accept:application/json")
    @FormUrlEncoded
    @POST("https://github.com/login/oauth/access_token")
    Call<AccessTokenResult> getOAuthToken(@Field("client_id") String client,
                                          @Field("client_secret") String clientSecret,
                                          @Field("code") String code);

    //用户信息的接口,需要增加一个用户的实体类-User,获取用户信息
    @GET("user")
    Call<User> getUserInfo();

    //查询仓库结果
    @GET("/search/repositories")
    Call<RepoResultAPI> searchRepo(@Query("q") String query, @Query("page") int pageId);
    //MarkDown格式
    @POST("markdown/raw")
    Call<ResponseBody> MarkDown (@Body RequestBody body);
    //获取ReadMe文件
    @GET("repos/{owner}/{repo}/readme")
    Call<RepoContentResult> getReadme(@Path("owner") String owner, @Path("repo") String repo);
}
