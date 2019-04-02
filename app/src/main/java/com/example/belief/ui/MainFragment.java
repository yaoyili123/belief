package com.example.belief.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.belief.R;
import com.example.belief.ui.base.BaseFragment;
import com.example.belief.utils.BNVEffect;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportFragment;

public class MainFragment extends BaseFragment {

    @BindView(R.id.buttom_nav_view)
    public BottomNavigationView bnv;

    private MainActivity mActivity;

    private MenuItem mi;

    private SupportFragment[] mFragments = new SupportFragment[4];

    private int prePos = 0;

    //index映射的Title String
    private Map<Integer, Integer> mTitleMap;

    public static MainFragment newInstance() {
        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mTitleMap = new HashMap<>();
//        mTitleMap.put(0, R.string.top_title_sport);
//        mTitleMap.put(1, R.string.top_title_recipe);
//        mTitleMap.put(2, R.string.top_title_comm);
//        mTitleMap.put(3, R.string.top_title_user);
//        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            //onPageScrolled, 当前页面滚动时触发
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//            //当某个page成为selected state时触发，并设置Mnum的checked状态
//            @Override
//            public void onPageSelected(int position) {
//                if (mi != null) {
//                    mi.setChecked(false);
//                } else {
//                    bnv.getMenu().getItem(0).setChecked(false);
//                }
//                mi = bnv.getMenu().getItem(position);
//                mi.setChecked(true);
//            }
//            //scroll state改变了
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
//
//        vpa = new ViewPagerAdapter(getSupportFragmentManager());
//        vp.setAdapter(vpa);
//        List<Fragment> list = new ArrayList<>();
//        list.add(SportMainFragment.newInstance("运动"));
//        list.add(TestFragment.newInstance("发现"));
//        list.add(TestFragment.newInstance("社区"));
//        list.add(UserMainFragment.newInstance("用户"));
//        vpa.setList(list);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.frag_main, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView(view);
        return view;
    }

    //
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SupportFragment firstFragment = findChildFragment(SportMainFragment.class);
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
            mFragments[1] = findChildFragment(TestFragment.class);
            mFragments[2] = findChildFragment(TestFragment.class);
            mFragments[3] = findChildFragment(UserMainFragment.class);
        }

    }

    private void initView(View view) {
        BNVEffect.disableShiftMode(bnv);
        mActivity = (MainActivity)getActivity();
        bnv.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    public void startBrotherFragment(SupportFragment targetFragment) {
        start(targetFragment);
    }

    //底部导航选中callback
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            mi = item;
            switch (item.getItemId()) {
                case R.id.bn_sport: {
//                    vp.setCurrentItem(0, true);
                    mActivity.setToolbarTitle(R.string.top_title_sport);
                    showHideFragment(mFragments[0], mFragments[prePos]);
                    prePos = 0;
                    return true;
                }
                case R.id.bn_recipe: {
//                    vp.setCurrentItem(1, true);
                    mActivity.setToolbarTitle(R.string.top_title_recipe);
                    showHideFragment(mFragments[1], mFragments[prePos]);
                    prePos = 1;
                    return true;
                }
                case R.id.bn_comm: {
//                    vp.setCurrentItem(2, true);
                    mActivity.setToolbarTitle(R.string.top_title_comm);
                    showHideFragment(mFragments[2], mFragments[prePos]);
                    prePos = 2;
                    return true;
                }
                case R.id.bn_user:{
//                    vp.setCurrentItem(3, true);
                    mActivity.setToolbarTitle(R.string.top_title_user);
                    showHideFragment(mFragments[3], mFragments[prePos]);
                    prePos = 3;
                    return true;
                }
            }
            return false;
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
