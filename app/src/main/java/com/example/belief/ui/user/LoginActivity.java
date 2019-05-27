package com.example.belief.ui.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.EditText;

import com.example.belief.MvpApp;
import com.example.belief.R;
import com.example.belief.data.network.model.UserAuth;
import com.example.belief.ui.MainActivity;
import com.example.belief.ui.base.BaseActivity;

import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity
        implements UserMvpView {

    private final static String USERNAME_ERROR = "用户名不能为空";

    private final static String PASSWORD_ERROR = "密码不能为空";

    @Inject
    UserMvpPresenter<UserMvpView> userMvpPresenter;

    @BindView(R.id.login_un)
    public EditText loginUn;

    @BindView(R.id.login_pw)
    public EditText loginPw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        getActivityComponent().inject(this);
        userMvpPresenter.onAttach(this);
        setUnbinder(ButterKnife.bind(this));
        setUp();
    }

    //
    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        return intent;
    }

    @Override
    protected void setUp() {

    }

    @OnClick(R.id.bt_login)
    public void login() {
        hideKeyboard();
        String username = loginUn.getText().toString();
        String password = loginPw.getText().toString();
        if (username.isEmpty()) {
            onError(USERNAME_ERROR);
            return;
        }
        if (password.isEmpty()) {
            onError(PASSWORD_ERROR);
            return;
        }
        userMvpPresenter.login(username, password);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            openMainActivity(null);
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        userMvpPresenter.onDetach();
        super.onDestroy();
    }

    //设置全局变量并跳转到主活动
    @Override
    public void openMainActivity(Map args) {
        if (args != null) {
            UserAuth userAuth = (UserAuth) args.get("userAuth");
            String image = (String) args.get("photo_url");
            if (userAuth != null)
                MvpApp.get(this).setCurUser(userAuth);
            if (image != null)
                MvpApp.get(this).setUserHead(image);
        }
        Intent intent = MainActivity.getStartIntent(LoginActivity.this);
        startActivity(intent);
        finish();
    }
}
