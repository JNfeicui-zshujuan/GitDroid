package com.feicuiedu.gitdroid.login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.feicuiedu.gitdroid.R;
import com.feicuiedu.gitdroid.httpclient.GithubApi;
import com.hannesdorfmann.mosby.mvp.MvpActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import pl.droidsonroids.gif.GifImageView;

public class LoginActivity extends MvpActivity<LoginView,LoginPresenter>{
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.webView)
    WebView webView;
    @Bind(R.id.gifImageView)
    GifImageView gifImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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
    private void initWebView(){
        //删除所有cookie,主要是为了清除以前的登录历史记录
        CookieManager cookieManager=CookieManager.getInstance();
        cookieManager.removeAllCookie();
        //授权登录url
        webView.loadUrl(GithubApi.ANTH_URL);
        webView.setFocusable(true);
        webView.setFocusableInTouchMode(true);
        webView.setWebChromeClient(webChromeClient);
        webView.setWebViewClient(webViewClient);
    }
    private WebViewClient webViewClient=new WebViewClient(){
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            //登录成功后github将重定向一个url路径
            //我们从中对比取出code(临时授权值)
            //进行授权接口操作,且获取到基本信息
            return super.shouldOverrideUrlLoading(view, url);
        }
    };
    private WebChromeClient webChromeClient=new WebChromeClient(){
        @Override
        public void onProgressChanged(WebView view, int newProgress) {

            if (newProgress==100){
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
}
