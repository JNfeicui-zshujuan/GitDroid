package com.feicuiedu.gitdroid.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhengshujuan on 2016/6/30.
 */
public class HotRepoAdapter extends FragmentPagerAdapter {
    private List<String> languages;


    public HotRepoAdapter(FragmentManager fm) {
        super(fm);
        languages = new ArrayList<>();
        languages.add("java1");
        languages.add("java2");
        languages.add("java3");
        languages.add("java4");
        languages.add("java5");
        languages.add("java6");
    }

    //TabLayout与viewPager相对应
    @Override
    public Fragment getItem(int position) {

        return RepoFragment.getInstance(languages.get(position));
    }

    @Override
    public int getCount() {
        return languages.size();
    }

    //设置TabLayout的数据
    @Override
    public CharSequence getPageTitle(int position) {
        return languages.get(position);
    }
}
