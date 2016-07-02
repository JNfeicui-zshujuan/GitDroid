package com.feicuiedu.gitdroid.home;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.feicuiedu.gitdroid.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zhengshujuan on 2016/7/1.
 * 此视图三种不同的状态，1.加载中的进度条 2. 所有数据加载完毕的信息 3.加载发生错误的信息
 * 布局文件为1个progressbar,2个textview.
 */
public class FooterView extends FrameLayout {
    //代表视图三种状态的静态常量值
    private static final int STATE_LOADING = 0;
    private static final int STATE_COMPLETE = 1;
    private static final int STATE_ERROR = 2;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.tv_complete)
    TextView tvComplete;
    @Bind(R.id.tv_error)
    TextView tv_error;
    //默认为加载中的状态
    private int state = STATE_LOADING;

    public FooterView(Context context) {
        super(context, null);
    }

    public FooterView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public FooterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.content_load_footer, this, true);
        init();
    }

    private void init() {
        ButterKnife.bind(this);
    }

    //显示加载中
    public void showLoading() {
        state = STATE_LOADING;
        progressBar.setVisibility(VISIBLE);
        tvComplete.setVisibility(GONE);
        tv_error.setVisibility(GONE);
    }

    //显示数据加载完成
    public void showComplete() {
        state = STATE_COMPLETE;
        progressBar.setVisibility(GONE);
        tvComplete.setVisibility(VISIBLE);
        tv_error.setVisibility(GONE);

    }

    //显示加载发生错误
    public void showError() {
        state = STATE_ERROR;
        progressBar.setVisibility(GONE);
        tvComplete.setVisibility(GONE);
        tv_error.setVisibility(VISIBLE);
    }

    public boolean isLoading() {
        return state == STATE_COMPLETE;
    }

    public void setErrorClickListener(OnClickListener onClickListener) {
        tv_error.setOnClickListener(onClickListener);
    }
}
