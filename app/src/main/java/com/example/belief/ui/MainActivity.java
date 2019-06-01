package com.example.belief.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.belief.MvpApp;
import com.example.belief.R;
import com.example.belief.ui.base.BaseActivity;
import com.example.belief.ui.comm.CommMainFragment;
import com.example.belief.ui.recipe.RecipeMainFragment;
import com.example.belief.ui.sport.SportMainFragment;
import com.example.belief.ui.user.LoginActivity;
import com.example.belief.ui.user.UserMainFragment;
import com.example.belief.utils.BNVEffect;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportFragment;

/*
*APP的root活动，可以用来保存全局状态
* */
public class MainActivity extends BaseActivity {

    @BindView(R.id.top_toolbar)
    public Toolbar mTitle;

    @BindView(R.id.buttom_nav_view)
    public BottomNavigationView bnv;

    private MenuItem mi;

    private boolean isExit = false;

    private SupportFragment[] mFragments = new SupportFragment[4];

    private int prePos = 0;

    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frag_main);
        setUnbinder(ButterKnife.bind(this));
        requestPermission();
        setUp();
    }

    //监听所有fragments的callbacks，实现FragmentManager.FragmentLifecycleCallbacks中的callbacks
    class FragmentsListener extends FragmentManager.FragmentLifecycleCallbacks {
        @Override
        public void onFragmentCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
            Log.i("MainActivity", "onFragmentCreated--->" + f.getClass().getSimpleName());
        }
    }

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
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
                    if (!MvpApp.get(MainActivity.this).isLogined(MainActivity.this)) {
                        MainActivity.this.startActivity(LoginActivity.getStartIntent(MainActivity.this));
                        return false;
                    }
                    mTitle.setTitle(R.string.top_title_user);
                    showHideFragment(mFragments[3], mFragments[prePos]);
                    prePos = 3;
                    return true;
                }
            }
            return false;
        }
    };

    //按两次退出
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

    @Override
    protected void setUp() {
        //setSupportActionBar(mTitle);
        BNVEffect.disableShiftMode(bnv);
        bnv.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        SupportFragment firstFragment = findFragment(SportMainFragment.class);
        if (firstFragment == null) {
            mFragments[0] = SportMainFragment.newInstance("运动");
            mFragments[1] = RecipeMainFragment.newInstance();
            mFragments[2] = CommMainFragment.newInstance();
            mFragments[3] = UserMainFragment.newInstance("用户");

            //此处就会调用Fragment的各种初始化callback
            loadMultipleRootFragment(R.id.blank_frag, 0,
                    mFragments[0],
                    mFragments[1],
                    mFragments[2],
                    mFragments[3]);
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题

            // 这里我们需要拿到mFragments的引用
            mFragments[0] = firstFragment;
            mFragments[1] = findFragment(RecipeMainFragment.class);
            mFragments[2] = findFragment(CommMainFragment.class);
            mFragments[3] = findFragment(UserMainFragment.class);
        }
    }

    private void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            // 利用handler延迟发送更改状态信息
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            finish();
            System.exit(0);
        }
    }

    private void requestPermission() {
        // 版本判断。当手机系统大于 23 时，才有必要去判断权限是否获取
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(this, permissions, 321);
        }

    }
}
