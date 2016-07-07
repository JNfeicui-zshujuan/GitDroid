package com.feicuiedu.gitdroid.login;

import android.util.Log;

import com.feicuiedu.gitdroid.httpclient.AccessTokenResult;
import com.feicuiedu.gitdroid.httpclient.CurrentUser;
import com.feicuiedu.gitdroid.httpclient.GitHubClient;
import com.feicuiedu.gitdroid.httpclient.GithubApi;
import com.feicuiedu.gitdroid.httpclient.User;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zhengshujuan on 2016/7/5.
 */
public class LoginPresenter extends MvpNullObjectBasePresenter<LoginView> {
    private Call<AccessTokenResult> tokenCall;
    private Call<User> userCall;
    private static final String TAG = "LoginPresenter";

    public void login(String code) {
        getView().showProgress();
        //为了确保每个用户令牌唯一
        if (tokenCall != null) tokenCall.cancel();
            tokenCall = GitHubClient.getInstance().getOAuthToken(GithubApi.GLIENT_ID, GithubApi.GLIENT_SECRET, code);
            tokenCall.enqueue(tokenCallBack);
        Log.d(TAG, "login: " +tokenCall);
    }

    private Callback<AccessTokenResult> tokenCallBack = new Callback<AccessTokenResult>() {
        @Override
        public void onResponse(Call<AccessTokenResult> call, Response<AccessTokenResult> response) {
            //保存token到内存里
            //再使用token去获取当前已认证的用户信息.名称,头像....
            String token = response.body().getAccessToken();
            CurrentUser.setAccessToken(token);
            if (userCall!=null) userCall.cancel();
            userCall=GitHubClient.getInstance().getUserInfo();
            userCall.enqueue(userCallback);
        }

        @Override
        public void onFailure(Call<AccessTokenResult> call, Throwable t) {
            //重置webview
            getView().showProgress();
            getView().resetWeb();
        }
    };
    private Callback<User> userCallback = new Callback<User>() {
        @Override
        public void onResponse(Call<User> call, Response<User> response) {
            //保存user到内存里面
            User user = response.body();
            CurrentUser.setUser(user);
            //导航至主界面
            getView().showMessage();
            getView().navigateToMain();
        }


        @Override
        public void onFailure(Call<User> call, Throwable t) {
            //清除缓存的用户信息
            CurrentUser.clear();
            //重置w-e-b---View
            getView().showProgress();
            getView().resetWeb();
        }
    };
    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if (!retainInstance) {
            if (tokenCall != null) tokenCall.cancel();
            if (userCall != null) userCall.cancel();
        }
    }
}
