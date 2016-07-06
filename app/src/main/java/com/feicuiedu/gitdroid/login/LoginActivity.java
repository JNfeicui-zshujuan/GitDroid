package com.feicuiedu.gitdroid.login;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.feicuiedu.gitdroid.MainActivity;
import com.feicuiedu.gitdroid.R;
import com.feicuiedu.gitdroid.common.ActivityUtils;
import com.feicuiedu.gitdroid.httpclient.GithubApi;
import com.hannesdorfmann.mosby.mvp.MvpActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import pl.droidsonroids.gif.GifImageView;

public class LoginActivity extends MvpActivity<LoginView, LoginPresenter> implements LoginView {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.webView)
    WebView webView;
    @Bind(R.id.gifImageView)
    GifImageView gifImageView;
    private ActivityUtils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        utils =new ActivityUtils(this);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        //显示标题栏左上角的返回按钮
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initWebView();
    }

    //初始化webView
    private void initWebView() {
        //删除所有cookie,主要是为了清除以前的登录历史记录
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        //授权登录url
        webView.loadUrl(GithubApi.ANTH_URL);
        webView.setFocusable(true);
        webView.setFocusableInTouchMode(true);
        //处理解析,渲染网页等浏览器做的事情,辅助WebView处理Javascript的对话框，网站图标，网站title，加载进度等
        webView.setWebChromeClient(webChromeClient);
        // 帮助WebView处理各种通知、请求事件的
        webView.setWebViewClient(webViewClient);
    }

    private WebViewClient webViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Uri uri=Uri.parse(url);
            //检测加载到的新URL是否是用我们规定好的CALL_Back开头的
            if (GithubApi.CALL_BACK.equals(uri.getScheme())){
                //获取授权码
                String code=uri.getQueryParameter("code");
                getPresenter().login(code);
                return true;
            }
                //登录成功后github将重定向一个url路径
                //我们从中对比取出code(临时授权值)
                //进行授权接口操作,且获取到基本信息
                return super.shouldOverrideUrlLoading(view, url);
        }
    };
    private WebChromeClient webChromeClient = new WebChromeClient() {
        //加载进度条
        @Override
        public void onProgressChanged(WebView view, int newProgress) {

            if (newProgress == 100) {
                gifImageView.setVisibility(View.GONE);
                webView.setVisibility(View.VISIBLE);
            }
        }
    };

    @NonNull
    @Override
    public LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @Override
    public void showProgress() {
        gifImageView.setVisibility(View.VISIBLE);

    }

    @Override
    public void resetWeb() {
        initWebView();
    }

    @Override
    public void showMessage() {
       utils.showToast("ok");
    }

    @Override
    public void navigateToMain() {
        utils.startActivity(MainActivity.class);
        finish();
    }
}
