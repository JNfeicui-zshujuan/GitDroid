package com.feicuiedu.gitdroid.readme;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by zhengshujuan on 2016/7/8.
 * //
 */
public interface RepoInfoVIew extends MvpView {
    void showProgress();
    void hideProgress();
    //设置数据
    void setData(String htmlContent);
    void showMessage();

}
