package com.example.belief.ui.base;

import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;

public class BaseFragment extends SupportFragment implements MvpView {

    protected Unbinder unbinder;

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
