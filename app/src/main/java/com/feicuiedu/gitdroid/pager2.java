package com.feicuiedu.gitdroid;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zhengshujuan on 2016/6/28.
 */
public class pager2 extends FrameLayout {
    @Bind(R.id.ivBubble1)
    ImageView ivBubble1;
    @Bind(R.id.ivBubble2) ImageView ivBubble2;
    @Bind(R.id.ivBubble3) ImageView ivBubble3;

    public pager2(Context context) {
        super(context);
        init();
    }

    public pager2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public pager2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    private void init() {

            LayoutInflater.from(getContext()).inflate(R.layout.content_pager_2, this, true);
            ButterKnife.bind(this);
            ivBubble1.setVisibility(View.GONE);
            ivBubble2.setVisibility(View.GONE);
            ivBubble3.setVisibility(View.GONE);
        }

//显示当前页面内三个图像控件的进入动画,只会显示一次
    public void showAnimaton() {
        if (ivBubble1.getVisibility()!=View.VISIBLE) {

        postDelayed(new Runnable() {
            @Override
            public void run() {

                YoYo.with(Techniques.FadeIn).duration(300).playOn(ivBubble1);
                ivBubble1.setVisibility(View.VISIBLE);

            }
        }, 50);
        postDelayed(new Runnable() {
            @Override
            public void run() {

                YoYo.with(Techniques.FadeIn).duration(300).playOn(ivBubble2);
                ivBubble2.setVisibility(View.VISIBLE);
            }
        },550);
        postDelayed(new Runnable() {
            @Override
            public void run() {

                YoYo.with(Techniques.FadeIn).duration(300).playOn(ivBubble3);
                ivBubble3.setVisibility(View.VISIBLE);
            }
        },1000);
    }
}
}