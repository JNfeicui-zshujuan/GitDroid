package com.feicuiedu.gitdroid.View;

/**
 * Created by zhengshujuan on 2016/7/1.
 */
public interface LoadMoreView <T>{
    //加载失败.....
    void loadViewErro();
    //没有加载数据
    void loadMoreEnd();
    //加载更多数据
    void addMoreData(T datas);
    //隐藏加载更多的视图
    void hideLoadMore();
    void showLoadMoreLoading();

}
