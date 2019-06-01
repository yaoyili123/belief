package com.example.belief.ui.comm;

import com.example.belief.ui.base.MvpPresenter;
import com.example.belief.ui.base.MvpView;

public interface CommMvpPresenter<V extends MvpView> extends MvpPresenter<V> {

    void getCommListData();
}
