package com.example.belief.ui.user;

import com.example.belief.ui.base.MvpView;

import java.util.Map;

public interface UserMvpView extends MvpView {

    void openMainActivity(Map args);

    void setData(Object obj);
}
