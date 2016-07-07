package com.feicuiedu.gitdroid.home;

import android.util.Log;

import com.feicuiedu.gitdroid.View.PtrPageView;
import com.feicuiedu.gitdroid.hotrepositor.Language;
import com.feicuiedu.gitdroid.hotrepositor.Repo;
import com.feicuiedu.gitdroid.hotrepositor.RepoResultAPI;
import com.feicuiedu.gitdroid.httpclient.GitHubClient;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zhengshujuan on 2016/7/1.
 */
public class ReopListPresenter extends MvpNullObjectBasePresenter<PtrPageView> {

    private Call<RepoResultAPI> repoResult;
    private int nextPage = 0;
    private Language language;
    private static final String TAG = "ReopListPresenter";
    public ReopListPresenter(Language language) {
        this.language = language;
    }



    //这是下拉刷新的视图业务逻辑
    public void refresh() {
        getView().hideLoadMore();
        getView().showContentView();
        nextPage = 1;//刷新永远是第一页
        repoResult = GitHubClient.getInstance().searchRepo("language:" + language.getPath(), nextPage);
        repoResult.enqueue(repoResultAPICallback);
    }

    private Callback<RepoResultAPI> repoResultAPICallback = new Callback<RepoResultAPI>() {
        @Override
        public void onResponse(Call<RepoResultAPI> call, Response<RepoResultAPI> response) {
            getView().stopRefresh();//视图停止刷新
            RepoResultAPI repoResultAPI = response.body();
            Log.d(TAG, "onResponse: "+repoResultAPI);
            if (repoResultAPI == null) {
                getView().showErroView("result is null");
                return;
            }
//当前搜索的语言下,没有任何仓库
            if (repoResultAPI.getTotalCount() <= 0) {
                getView().refreshData(null);
                getView().showEmptyView();
                return;
            }
            //取出当前搜索的语言下的所有仓库
            List<Repo> repoList = repoResultAPI.getRepoList();
            getView().refreshData(repoList);//刷新当前视图
            nextPage = 2;
        }

        @Override
        public void onFailure(Call<RepoResultAPI> call, Throwable t) {
            getView().stopRefresh();//停止刷新视图
        }
    };
    //上拉加载更多视图的业务逻辑
    public void LoadMore() {
        getView().showLoadMoreLoading();
        repoResult = GitHubClient.getInstance().searchRepo("language:" + language.getPath(), nextPage);
        repoResult.enqueue(loadMoreCallBack);
    }
    //上拉加载的回调
    private Callback<RepoResultAPI> loadMoreCallBack = new Callback<RepoResultAPI>() {
        @Override
        public void onResponse(Call<RepoResultAPI> call, Response<RepoResultAPI> response) {
            getView().hideLoadMore();//隐藏加载视图
            RepoResultAPI repoResultAPI = response.body();
            if (repoResultAPI == null) {
                getView().showMessage("result is null");
                return;
            }
            if (repoResultAPI.getTotalCount() <= 0) {
                getView().loadMoreEnd();
                return;
            }
            //取出当前搜索的语言下,所有仓库
            List<Repo> repoList = repoResultAPI.getRepoList();
            getView().addMoreData(repoList);
            nextPage++;
        }

        @Override
        public void onFailure(Call<RepoResultAPI> call, Throwable t) {
            getView().hideLoadMore();
            getView().loadViewErro();
        }
    };
}