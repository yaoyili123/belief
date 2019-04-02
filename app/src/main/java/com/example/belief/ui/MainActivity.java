package com.example.belief.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.belief.R;
import com.example.belief.utils.BNVEffect;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {

    @BindView(R.id.buttom_nav_view)
    public BottomNavigationView bnv;

    @BindView(R.id.view_page)
    public ViewPager vp;

    private ViewPagerAdapter vpa;
    private MenuItem mi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        BNVEffect.disableShiftMode(bnv);

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
                case R.id.bn_sport:
                    vp.setCurrentItem(0);
                    return true;
                case R.id.bn_recipe:
                    vp.setCurrentItem(1);
                    return true;
                case R.id.bn_comm:
                    vp.setCurrentItem(2);
                    return true;
                case R.id.bn_user:
                    vp.setCurrentItem(3);
                    return true;
            }
            return false;
        }
    };

}
