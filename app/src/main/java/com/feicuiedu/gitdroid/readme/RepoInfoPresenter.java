package com.feicuiedu.gitdroid.readme;

import android.util.Base64;

import com.feicuiedu.gitdroid.hotrepositor.Repo;
import com.feicuiedu.gitdroid.httpclient.GitHubClient;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zhengshujuan on 2016/7/8.
 * //仓库详细信息列表的业务视图
 */
public class RepoInfoPresenter extends MvpNullObjectBasePresenter<RepoInfoVIew> {
    private Call<RepoContentResult> repoContentCall;
    private Call<ResponseBody> responseBodyCall;

    //获取指定仓库的ReadMe
    public void getReadMe(Repo repo) {
        getView().showProgress();
        String login = repo.getOwner().getLogin();
        String name = repo.getName();
        if (repoContentCall != null) repoContentCall.cancel();
        repoContentCall = GitHubClient.getInstance().getReadme(login, name);
        repoContentCall.enqueue(repoContentCallback);
    }

    private Callback<RepoContentResult> repoContentCallback = new Callback<RepoContentResult>() {
        @Override
        public void onResponse(Call<RepoContentResult> call, Response<RepoContentResult> response) {
            String content = response.body().getContent();
            //base64解码
            byte[] data = Base64.decode(content, Base64.DEFAULT);
            String mdContent = new String(data);
            //根据markdown格式的内容获取HTML格式的内容
            RequestBody body = RequestBody.create(MediaType.parse("text/plain"), mdContent);
            if (responseBodyCall != null) responseBodyCall.cancel();
            responseBodyCall = GitHubClient.getInstance().MarkDown(body);
            responseBodyCall.enqueue(responseBodyCallback);
        }

        @Override
        public void onFailure(Call<RepoContentResult> call, Throwable t) {
            getView().hideProgress();
        }
    };
    private Callback<ResponseBody> responseBodyCallback = new Callback<ResponseBody>() {
        @Override
        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            try {
                String htmlContent = response.body().string();
                getView().setData(htmlContent);
                getView().hideProgress();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        @Override
        public void onFailure(Call<ResponseBody> call, Throwable t) {
            getView().hideProgress();
            getView().showMessage();
        }
    };
}
