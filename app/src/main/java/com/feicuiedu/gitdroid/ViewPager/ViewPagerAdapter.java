package com.feicuiedu.gitdroid.ViewPager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.feicuiedu.gitdroid.ViewPager.pager0;
import com.feicuiedu.gitdroid.ViewPager.pager1;
import com.feicuiedu.gitdroid.ViewPager.pager2;

/**
 * Created by zhengshujuan on 2016/6/28.
 */
public class ViewPagerAdapter extends PagerAdapter {
    private final View[] views;

    //构造方法传入视图
    public ViewPagerAdapter(Context context) {
      views = new View[]{

                new pager0(context),
                new pager1(context),
                new pager2(context)

        };
    }

    @Override
    public int getCount() {
        return views.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    //添加当前视图
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = views[position];
        container.addView(view);
        return view;
    }

    //销毁当前视图
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = views[position];
        container.removeView(view);
    }

    public View getView(int position) {
        return views[position];
    }

}
