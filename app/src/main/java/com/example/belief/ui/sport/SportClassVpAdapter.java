package com.example.belief.ui.sport;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/*
FragmentPagerAdapter, 将每一个page视为一个fragment, 非常适合维护一些静态的（我的理解就是
经常重复返回的页面，应该一直在内存中的）fragment，比如列表

FragmentStatePagerAdapter，适用于较大的页面集合
* */
public class SportClassVpAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;
    private List<String> titles;

    public void setList(List<Fragment> list, List<String> titles) {
        this.fragments = list;
        this.titles = titles;
        notifyDataSetChanged();
    }

    public SportClassVpAdapter(FragmentManager fm) {

        super(fm);
    }

    public SportClassVpAdapter(List<Fragment> list, List<String> titles, FragmentManager fm) {
        super(fm);
        this.fragments = list;
        this.titles = titles;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments != null ? fragments.size() : 0;
    }
}
