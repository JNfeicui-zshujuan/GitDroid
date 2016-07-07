package com.feicuiedu.gitdroid.login;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by zhengshujuan on 2016/7/5.
 */
public interface LoginView extends MvpView {
    //显示进度条
    void showProgress();
    //重置webView
    void resetWeb();

    void showMessage();
    //导航至主界面
    void navigateToMain();
}
