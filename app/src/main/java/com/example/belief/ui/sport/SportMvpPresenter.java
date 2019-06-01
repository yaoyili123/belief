package com.example.belief.ui.sport;

import android.widget.SimpleAdapter;

import com.example.belief.data.network.model.UserAuth;
import com.example.belief.ui.base.MvpPresenter;
import com.example.belief.ui.base.MvpView;
import com.flyco.dialog.widget.NormalDialog;

import java.util.List;

public interface SportMvpPresenter<V extends MvpView> extends MvpPresenter<V> {

    //加载首页数据
    void onMainViewPrepared(UserAuth userAuth, String imageName);

    //获取用户参加的课程
    void getJoinedClasses(int uid);

    void deleteJoinedClass(int uid, int scid, int pos, List showList, List classList, NormalDialog dialog, SimpleAdapter adapter);

    void getAllClasses();

    void getSportClass(int scid, int uid);

    void getSportActions(int scid);

    void downPicGIF(String url);
}
