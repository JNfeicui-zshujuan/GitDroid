package com.feicuiedu.gitdroid.readme;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.feicuiedu.gitdroid.R;
import com.feicuiedu.gitdroid.common.ActivityUtils;
import com.feicuiedu.gitdroid.hotrepositor.Repo;
import com.hannesdorfmann.mosby.mvp.MvpActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zhengshujuan on 2016/7/8.
 */
public class RepoInfoActivity extends MvpActivity<RepoInfoVIew, RepoInfoPresenter> implements RepoInfoVIew {
    @Bind(R.id.ivIcon)
    ImageView ivIcon;
    @Bind(R.id.tvRepoInfo)
    TextView tvRepoInfo;
    @Bind(R.id.tvRepoStars)
    TextView tvRepoStars;
    @Bind(R.id.tvRepoName)
    TextView tvRepoName;
    @Bind(R.id.webView)
    WebView webView;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private ActivityUtils activityUtils;
    private Repo repo;
    private static String KEY_REPO = "key_repo";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUtils=new ActivityUtils(this);
        setContentView(R.layout.activity_repo_info);
        //获取仓库的readMe
        getPresenter().getReadMe(repo);
    }

    //开启意图,跳转到webView,并将数据传递过去
    public static void open(Context context, Repo repo) {
        Intent intent = new Intent(context, RepoInfoActivity.class);
        intent.putExtra(KEY_REPO,  repo);
        context.startActivity(intent);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        //取出仓库
        repo= (Repo) getIntent().getSerializableExtra(KEY_REPO);
      setSupportActionBar(toolbar);
        //显示toolbar的返回键
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //将toolbar的标题设置为仓库名称
        getSupportActionBar().setTitle(repo.getName());
        //加载仓库的具体信息
        ImageLoader.getInstance().displayImage(repo.getOwner().getAvatar(),ivIcon);
        tvRepoInfo.setText(repo.getDescription());
        tvRepoName.setText(repo.getName());
        tvRepoStars.setText(String.format("star: %d  fork: %d",repo.getStargazerCount(),repo.getForksCount()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @NonNull
    @Override
    public RepoInfoPresenter createPresenter() {
        return new RepoInfoPresenter();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setData(String htmlContent) {
        webView.loadData(htmlContent, "text/html", "UTF-8");
    }

    @Override
    public void showMessage() {

    }
}
