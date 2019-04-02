package com.example.belief.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.example.belief.R;
import com.example.belief.ui.base.BaseActivity;
import com.example.belief.utils.BNVEffect;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportFragment;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/*
*作为整个APP的单活动多碎片模式的那个活动，任务包括加载MainFragment以及监听事件
* */
public class MainActivity extends BaseActivity {

    @BindView(R.id.top_toolbar)
    public Toolbar mTitle;

    @BindView(R.id.buttom_nav_view)
    public BottomNavigationView bnv;

    private MainActivity mActivity;

    private MenuItem mi;

    private SupportFragment[] mFragments = new SupportFragment[4];

    private int prePos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frag_main);
        ButterKnife.bind(this);
        setSupportActionBar(mTitle);
        BNVEffect.disableShiftMode(bnv);
        bnv.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        SupportFragment firstFragment = findFragment(SportMainFragment.class);
        if (firstFragment == null) {
            mFragments[0] = SportMainFragment.newInstance("运动");
            mFragments[1] = TestFragment.newInstance("1");
            mFragments[2] = TestFragment.newInstance("2");
            mFragments[3] = UserMainFragment.newInstance("用户");

            loadMultipleRootFragment(R.id.blank_frag, 0,
                    mFragments[0],
                    mFragments[1],
                    mFragments[2],
                    mFragments[3]);
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题

            // 这里我们需要拿到mFragments的引用
            mFragments[0] = firstFragment;
            mFragments[1] = findFragment(TestFragment.class);
            mFragments[2] = findFragment(TestFragment.class);
            mFragments[3] = findFragment(UserMainFragment.class);
        }
    }

    //监听所有fragments的callbacks，实现FragmentManager.FragmentLifecycleCallbacks中的callbacks
    class FragmentsListener extends FragmentManager.FragmentLifecycleCallbacks {
        @Override
        public void onFragmentCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
            Log.i("MainActivity", "onFragmentCreated--->" + f.getClass().getSimpleName());
        }
    }

    @Override
    public void onBackPressedSupport() {
        // 对于 4个类别的主Fragment内的回退back逻辑,已经在其onBackPressedSupport里各自处理了
        super.onBackPressedSupport();
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        // 设置横向(和安卓4.x动画相同)
        return new DefaultHorizontalAnimator();
    }

    //底部导航选中callback
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            mi = item;
            switch (item.getItemId()) {
                case R.id.bn_sport: {
                    mTitle.setTitle(R.string.top_title_sport);
                    showHideFragment(mFragments[0], mFragments[prePos]);
                    prePos = 0;
                    return true;
                }
                case R.id.bn_recipe: {
                    mTitle.setTitle(R.string.top_title_recipe);
                    showHideFragment(mFragments[1], mFragments[prePos]);
                    prePos = 1;
                    return true;
                }
                case R.id.bn_comm: {
                    mTitle.setTitle(R.string.top_title_comm);
                    showHideFragment(mFragments[2], mFragments[prePos]);
                    prePos = 2;
                    return true;
                }
                case R.id.bn_user:{
                    mTitle.setTitle(R.string.top_title_user);
                    showHideFragment(mFragments[3], mFragments[prePos]);
                    prePos = 3;
                    return true;
                }
            }
            return false;
        }
    };

}
