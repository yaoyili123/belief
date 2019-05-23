package com.example.belief.ui.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.belief.R;
import com.example.belief.di.component.ActivityComponent;
import com.example.belief.utils.UIUtils;

import java.net.SocketTimeoutException;

import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;
import retrofit2.HttpException;

public abstract class BaseFragment extends SupportFragment implements MvpView {

    protected Unbinder unbinder;
    private BaseActivity mActivity;
    private ProgressDialog mProgressDialog;

    public void setUnbinder(Unbinder unbinder) {
        this.unbinder = unbinder;
    }

    public ActivityComponent getActivityComponent() {
        if (mActivity != null) {
            return mActivity.getActivityComponent();
        }
        return null;
    }

    public BaseActivity getBaseActivity() {
        return mActivity;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            BaseActivity activity = (BaseActivity) context;
            this.mActivity = activity;
        }
    }

    @Override
    public void hideKeyboard() {
        if (mActivity != null) {
            mActivity.hideKeyboard();
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUp(view);
    }

    @Override
    public void onDestroy() {
        if (unbinder != null) {
            unbinder.unbind();
        }
        super.onDestroy();
    }

    //设置view
    protected abstract void setUp(View view);

    @Override
    public void toLogin() {
        if (mActivity != null) {
            mActivity.toLogin();
        }
    }

    @Override
    public void showLoading() {
        hideLoading();
        mProgressDialog = UIUtils.showLoadingDialog(this.getContext());
    }

    @Override
    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
        }
    }

    @Override
    public void onError(String message) {
        if (mActivity != null) {
            mActivity.onError(message);
        }
    }

    @Override
    public void showMessage(String message) {
        if (mActivity != null) {
            mActivity.showMessage(message);
        }
    }

    @Override
    public void handleApiError(Throwable error) {
        if (error instanceof SocketTimeoutException) {
            onError(getResources().getString(R.string.connection_error));
            return;
        }
        if (error instanceof HttpException) {
            HttpException exception = (HttpException)error;
            onError(exception.message());
            return;
        }
    }
}
