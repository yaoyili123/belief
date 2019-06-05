package com.example.belief.ui.user;

import com.example.belief.data.network.model.UserInfo;
import com.example.belief.ui.base.MvpPresenter;
import com.example.belief.ui.base.MvpView;

//为什么每一个P都要写一个接口？ 为了依赖注入以及多态
public interface UserMvpPresenter<V extends MvpView> extends MvpPresenter<V> {

    void login(String username, String password);

    void getUserInfo(int uid);

    void updateUserInfo(UserInfo userInfo);

    void register(String username, String password);

    void getSportInfo(int uid);
}
