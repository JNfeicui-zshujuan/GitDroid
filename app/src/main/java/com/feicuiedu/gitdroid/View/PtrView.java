package com.feicuiedu.gitdroid.View;

/**
 * Created by zhengshujuan on 2016/7/1.
 * 下拉刷新视图接口
 */
public interface PtrView <T>{
    void showContentView();
    void showErroView();
    void showEmptyView();
    //刷新数据
    void refreshData(T t);
    //停止刷新
    void stopRefresh();
    void showMessage(String string);
}
