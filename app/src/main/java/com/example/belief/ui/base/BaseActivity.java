package com.example.belief.ui.base;

import android.os.Bundle;

import com.example.belief.MvpApp;
import com.example.belief.di.component.ActivityComponent;
import com.example.belief.di.component.DaggerActivityComponent;
import com.example.belief.di.module.ActivityModule;

import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportActivity;

/*基础Activity, 提供DI component、实现一些通用任务
* 实现了Fragmentation的SupportAcitivity, 作为Fragments管理器
* */
public class BaseActivity extends SupportActivity implements MvpView {

    private ActivityComponent mActivityComponent;

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityComponent = DaggerActivityComponent.builder().activityModule(new ActivityModule(this))
                .applicationComponent(((MvpApp) getApplication()).getComponent()).build();

    }

    public ActivityComponent getActivityComponent() {
        return mActivityComponent;
    }

    public void setUnbinder(Unbinder mUnbinder) {
        this.mUnbinder = mUnbinder;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
