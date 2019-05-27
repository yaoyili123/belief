package com.example.belief.ui.sport;

import com.example.belief.data.network.model.UserAuth;
import com.example.belief.ui.base.MvpPresenter;
import com.example.belief.ui.base.MvpView;

public interface SportMvpPresenter<V extends MvpView> extends MvpPresenter<V> {

    //加载首页数据
    void onMainViewPrepared(UserAuth userAuth, String imageName);

    //获取用户参加的课程
    void getJoinedClasses(int uid);
}
