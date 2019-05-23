package com.example.belief.ui.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.belief.MvpApp;
import com.example.belief.R;
import com.example.belief.di.component.ActivityComponent;
import com.example.belief.di.component.DaggerActivityComponent;
import com.example.belief.di.module.ActivityModule;
import com.example.belief.ui.user.LoginActivity;
import com.example.belief.utils.UIUtils;

import java.net.SocketTimeoutException;

import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportActivity;
import retrofit2.HttpException;

/*基础Activity, 提供DI component、实现一些通用任务
 * 实现了Fragmentation的SupportAcitivity, 作为Fragments管理器
 * */
public abstract class BaseActivity extends SupportActivity implements MvpView {

    private ActivityComponent mActivityComponent;

    private ProgressDialog mProgressDialog;

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityComponent = DaggerActivityComponent.builder().activityModule(new ActivityModule(this))
                .applicationComponent(((MvpApp) getApplication()).getComponent()).build();
    }

    @Override
    protected void onDestroy() {
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        super.onDestroy();
    }

    public ActivityComponent getActivityComponent() {
        return mActivityComponent;
    }

    public void setUnbinder(Unbinder mUnbinder) {
        this.mUnbinder = mUnbinder;
    }

    @Override
    public void showLoading() {
        hideLoading();
        mProgressDialog = UIUtils.showLoadingDialog(this);
    }

    @Override
    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
        }
    }

    private void showSnackBar(String message) {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                message, Snackbar.LENGTH_SHORT);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(getResources().getColor(R.color.theme_color_deep));
        TextView textView = (TextView) sbView
                .findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(this, R.color.white));
        snackbar.show();
    }

    @Override
    public void onError(String message) {
        if (message != null) {
            showSnackBar(message);
        } else {
            showSnackBar(getString(R.string.some_error));
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

    @Override
    public void showMessage(String message) {
        if (message != null) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, getString(R.string.some_error), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void toLogin() {
        startActivity(LoginActivity.getStartIntent(this));
        finish();
    }

    //在onCreate中设置view
    protected abstract void setUp();
}
