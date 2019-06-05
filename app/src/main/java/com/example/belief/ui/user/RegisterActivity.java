package com.example.belief.ui.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

public class RegisterActivity extends BaseActivity implements UserMvpView {

    private final static String USERNAME_ERROR = "用户名不能为空";

    private final static String PASSWORD_ERROR = "密码不能为空";

    private final static String REPASSWORD_ERROR = "请重复输入密码";

    private final static String PASSWORD_UNEQUAL = "重复输入不正确";

    @Inject
    UserMvpPresenter<UserMvpView> userMvpPresenter;

    @BindView(R.id.register_un)
    public EditText rUn;

    @BindView(R.id.register_pw)
    public EditText rPw;

    @BindView(R.id.register_repw)
    public EditText rrPw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        getActivityComponent().inject(this);
        userMvpPresenter.onAttach(this);
        setUnbinder(ButterKnife.bind(this));
        setUp();
    }

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        return intent;
    }

    @Override
    protected void setUp() {

    }

    @Override
    public void openMainActivity(Map args) {
        if (args != null) {
            UserAuth userAuth = (UserAuth) args.get("userAuth");
            if (userAuth != null)
                MvpApp.get(this).setCurUser(userAuth);
        }
        Intent intent = MainActivity.getStartIntent(RegisterActivity.this);
        startActivity(intent);
        finish();
    }

    @Override
    public void setData(Object obj) {

    }

    @OnClick(R.id.bt_register)
    public void register() {
        hideKeyboard();
        String username = rUn.getText().toString();
        String password = rPw.getText().toString();
        String rpassword = rrPw.getText().toString();
        if (username.isEmpty()) {
            onError(USERNAME_ERROR);
            return;
        }
        if (password.isEmpty()) {
            onError(PASSWORD_ERROR);
            return;
        }
        if (rpassword.isEmpty()) {
            onError(REPASSWORD_ERROR);
            return;
        }
        if (!password.equals(rpassword)) {
            onError(PASSWORD_UNEQUAL);
            return;
        }

        userMvpPresenter.register(username, password);
    }


}
