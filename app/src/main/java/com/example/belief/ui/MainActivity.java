package com.example.belief.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.example.belief.R;
import com.example.belief.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/*
*作为整个APP的单活动多碎片模式的那个活动，任务包括加载MainFragment以及监听事件
* */
public class MainActivity extends BaseActivity {

    @BindView(R.id.top_toolbar)
    public Toolbar mTitle;

    public void setToolbarTitle(int res) {
        mTitle.setTitle(res);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //设置Activity默认的ACTIONbAR，不需要在布局中再加上toolbar控件
        setSupportActionBar(mTitle);
        assert mTitle != null;

        //activity初始加载HomeFragment
        if (findFragment(MainFragment.class) == null)
            //左侧参数是加载位置
            loadRootFragment(R.id.main_act, MainFragment.newInstance());
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
}
