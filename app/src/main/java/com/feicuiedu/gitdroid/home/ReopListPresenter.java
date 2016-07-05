package com.feicuiedu.gitdroid.home;

import android.os.AsyncTask;

import com.feicuiedu.gitdroid.View.PtrPageView;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by zhengshujuan on 2016/7/1.
 */
public class ReopListPresenter extends MvpNullObjectBasePresenter<PtrPageView> {



    //这是下来刷新的视图业务逻辑
    public void loadData() {
        new LoadDataTask().execute();
    }

    //这是上拉加载更多的视图业务逻辑
    public void loadMore() {
        new LoadMoreTask().execute();
    }

    private static int count = 0;

    private final class LoadDataTask extends AsyncTask<Void, Void, List<String>> {

        @Override
        protected List<String> doInBackground(Void... params) {
            //模拟网络连接
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            final int size = new Random().nextInt(100);
            final ArrayList<String> loadDatas = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                loadDatas.add("我是下拉刷新" + (++count));
            }
            return loadDatas;
        }

        @Override
        protected void onPostExecute(List<String> strings) {
            super.onPostExecute(strings);
            int size = strings.size();
            if (size == 0) {
                getView().showEmptyView();
            } else if (size == 1) {
                getView().showErroView();
            } else {
                getView().showContentView();
                getView().refreshData(strings);
            }
            getView().stopRefresh();
        }
    }

    private final class LoadMoreTask extends AsyncTask<Void, Void, List<String>> {
        //开启任务的方法
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //显示加载中.....
            getView().showLoadMoreLoading();
        }

        @Override
        protected List<String> doInBackground(Void... params) {
            //模拟加载更多时的网络连接
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            final ArrayList<String> loadDatas = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                loadDatas.add("我是loadMore的第" + i + "条数据");
            }
            return loadDatas;
        }

        //处理数据的方法
        @Override
        protected void onPostExecute(List<String> strings) {
            super.onPostExecute(strings);
            getView().addMoreData(strings);
            getView().hideLoadMore();
        }
    }
}



