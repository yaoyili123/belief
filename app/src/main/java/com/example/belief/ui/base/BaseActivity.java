package com.example.belief.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.belief.MvpApp;
import com.example.belief.di.component.ActivityComponent;
import com.example.belief.di.component.DaggerActivityComponent;
import com.example.belief.di.module.ActivityModule;

import butterknife.Unbinder;

public class BaseActivity extends AppCompatActivity implements MvpView{

    private ActivityComponent mActivityComponent;

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
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
