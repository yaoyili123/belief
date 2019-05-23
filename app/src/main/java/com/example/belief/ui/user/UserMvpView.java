package com.example.belief.ui.user;

import com.example.belief.data.network.model.UserAuth;
import com.example.belief.ui.base.MvpView;

public interface UserMvpView extends MvpView {
    void openMainActivity(UserAuth userAuth);
}
