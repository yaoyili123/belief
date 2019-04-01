package com.example.belief.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/*
FragmentPagerAdapter, 将每一个page视为一个fragment, 非常适合维护一些静态的（我的理解就是
经常重复返回的页面，应该一直在内存中的）fragment，比如列表

FragmentStatePagerAdapter，适用于较大的页面集合
* */
public class ViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> list;

    public void setList(List<Fragment> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list != null ? list.size() : 0;
    }
}
