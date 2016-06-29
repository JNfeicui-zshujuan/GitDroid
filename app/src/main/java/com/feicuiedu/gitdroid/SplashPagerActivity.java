package com.feicuiedu.gitdroid;

import android.animation.ArgbEvaluator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator;

/**
 * Created by zhengshujuan on 2016/6/28.
 */
public class SplashPagerActivity extends Fragment {
    @Bind(R.id.viewPager)
    ViewPager viewPager;
    @Bind(R.id.indicator)
    CircleIndicator indicator;
    @BindColor(R.color.colorGreen)
    int colorGreen;
    @BindColor(R.color.colorRed)
    int colorRed;
    @BindColor(R.color.colorYellow)
    int colorYellow;
    ViewPagerAdapter adapter;
    @Bind(R.id.content)
    FrameLayout frameLayout;
    @Bind(R.id.layoutPhone)
    FrameLayout layoutphone;
    @Bind(R.id.ivPhone)
    ImageView ivPhone;
    @Bind(R.id.ivPhoneBlank)
    ImageView ivPhoneBlank;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_splash_pager, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        adapter = new ViewPagerAdapter(getContext());
        viewPager.setAdapter(adapter);
        indicator.setViewPager(viewPager);
        viewPager.addOnPageChangeListener(pageChangeListener);
        viewPager.addOnPageChangeListener(listener);
    }

    private ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (position==0){
                float scale=0.3f+positionOffset*0.7f;
                layoutphone.setScaleX(scale);
                layoutphone.setScaleY(scale);

                ivPhone.setAlpha(positionOffset);
                //移动过程中平移的动画
                int scroll= (int) ((positionOffset-1)*290);
            layoutphone.setTranslationX(scroll);
            return;
        }

        if (position == 1) {
            layoutphone.setTranslationX(-positionOffsetPixels);
            return;
        }
        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        private String TAG = "MainActivity";
        //颜色取值器
        ArgbEvaluator argbEvaluator = new ArgbEvaluator();

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (position == 0) {
                int evaluate = (int) argbEvaluator.evaluate(0.2f, colorGreen, colorRed);
                Log.d(TAG, "onPageScrolled: 颜色1******" + evaluate);
                frameLayout.setBackgroundColor(evaluate);
                return;
            }
            if (position == 1) {
                int evaluate = (int) argbEvaluator.evaluate(0.4f, colorRed, colorYellow);
                Log.d(TAG, "onPageScrolled: 颜色2******" + evaluate);
                frameLayout.setBackgroundColor(evaluate);
                return;
            }
        }

        @Override
        public void onPageSelected(int position) {
            //显示最后一个页面的视图动画
            if (position == 2) {
                pager2 pagersecond = (pager2) adapter.getView(position);
                pagersecond.showAnimaton();
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }


    };


}
