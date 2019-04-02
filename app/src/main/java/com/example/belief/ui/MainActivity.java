package com.example.belief.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.example.belief.R;
import com.example.belief.ui.base.BaseActivity;
import com.example.belief.utils.BNVEffect;
import com.example.belief.utils.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/*
*作为整个APP的单活动多碎片模式的那个活动，任务包括加载MainFragment以及监听事件
* */
public class MainActivity extends BaseActivity {

    @BindView(R.id.buttom_nav_view)
    public BottomNavigationView bnv;

    @BindView(R.id.view_page)
    public ViewPager vp;

    private ViewPagerAdapter vpa;
    private MenuItem mi;

    @BindView(R.id.top_toolbar)
    public Toolbar mTitle;

    //index映射的Title String
    private Map<Integer, Integer> mTitleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        BNVEffect.disableShiftMode(bnv);
        ToastUtils.init(getApplication());
        //设置Activity默认的ACTIONbAR，不需要在布局中再加上toolbar控件
        setSupportActionBar(mTitle);

        mTitleMap = new HashMap<>();
        mTitleMap.put(0, R.string.top_title_sport);
        mTitleMap.put(1, R.string.top_title_recipe);
        mTitleMap.put(2, R.string.top_title_comm);
        mTitleMap.put(3, R.string.top_title_user);

        bnv.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            //onPageScrolled, 当前页面滚动时触发
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            //当某个page成为selected state时触发，并设置Mnum的checked状态
            @Override
            public void onPageSelected(int position) {
                if (mi != null) {
                    mi.setChecked(false);
                } else {
                    bnv.getMenu().getItem(0).setChecked(false);
                }
                mi = bnv.getMenu().getItem(position);
                mi.setChecked(true);
            }
            //scroll state改变了
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        vpa = new ViewPagerAdapter(getSupportFragmentManager());
        vp.setAdapter(vpa);
        List<Fragment> list = new ArrayList<>();
        list.add(SportMainFragment.newInstance("运动"));
        list.add(TestFragment.newInstance("发现"));
        list.add(TestFragment.newInstance("社区"));
        list.add(UserMainFragment.newInstance("用户"));
        vpa.setList(list);
    }

    //底部导航选中callback
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            mi = item;
            switch (item.getItemId()) {
                case R.id.bn_sport: {
                    vp.setCurrentItem(0, true);
                    mTitle.setTitle(R.string.top_title_sport);
                    return true;
                }
                case R.id.bn_recipe: {
                    vp.setCurrentItem(1, true);
                    mTitle.setTitle(R.string.top_title_recipe);
                    return true;
                }
                case R.id.bn_comm: {
                    vp.setCurrentItem(2, true);
                    mTitle.setTitle(R.string.top_title_comm);
                    return true;
                }
                case R.id.bn_user:{
                    vp.setCurrentItem(3, true);
                    mTitle.setTitle(R.string.top_title_user);
                    return true;
                }
            }
            return false;
        }
    };

    //监听所有fragments的callbacks，实现FragmentManager.FragmentLifecycleCallbacks中的callbacks
    class FragmentsListener extends FragmentManager.FragmentLifecycleCallbacks {
        @Override
        public void onFragmentCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
            Log.i("MainActivity", "onFragmentCreated--->" + f.getClass().getSimpleName());
        }
    }
}
