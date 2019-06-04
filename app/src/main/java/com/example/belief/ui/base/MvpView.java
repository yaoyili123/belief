package com.example.belief.ui.base;
/*
* View层通用接口，由Presenter依赖，一般被Activity和Fragment继承
* */
public interface  MvpView {

    void showLoading();

    void hideLoading();

    void onError(String message);

    void showMessage(String message);

    void toLogin();

    void hideKeyboard();

    void handleApiError(Throwable error);
}
