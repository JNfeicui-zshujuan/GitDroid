package com.feicuiedu.gitdroid;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by zhengshujuan on 2016/6/28.
 */
public class ViewPagerAdapter extends PagerAdapter {
    private final View[] views;

//你噶诶韩国
    public ViewPagerAdapter(Context context) {
        View view= LayoutInflater.from(context).inflate(R.layout.content_pager_0,null,false);
        View view1= LayoutInflater.from(context).inflate(R.layout.content_pager_1,null,false);
        View view2= LayoutInflater.from(context).inflate(R.layout.content_pager_2,null,false);
        views=new View[]{
                LayoutInflater.from(context).inflate(R.layout.content_pager_0,null,true),
//                new pager0(context),
//                new pager1(context),
//                new pager2(context)
                LayoutInflater.from(context).inflate(R.layout.content_pager_1,null,true),
                LayoutInflater.from(context).inflate(R.layout.content_pager_2,null,true)
        };
    }

    @Override
    public int getCount() {
        return views.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view =views[position];
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view=views[position];
        container.removeView(view);
    }
    public View getView(int position){
        return views[position];
    }

}
