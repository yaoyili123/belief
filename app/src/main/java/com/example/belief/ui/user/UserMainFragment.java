package com.example.belief.ui.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.belief.R;
import com.example.belief.ui.MainActivity;
import com.example.belief.utils.ToastUtils;

import me.yokeyword.fragmentation.SupportFragment;

/*用户模块一级页面
 * */
public class UserMainFragment extends SupportFragment {

    MainActivity mainActivity;

    public static UserMainFragment newInstance(String name) {
        Bundle args = new Bundle();
        args.putString("name", name);
        UserMainFragment fragment = new UserMainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity)getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        ToastUtils.showToast("call userMainFragment onCreateView");
        View view = inflater.inflate(R.layout.farg_user_main, container, false);
        mainActivity.disableToolBar();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        ToastUtils.showToast("call userMainFragment onDestroyView");
        super.onDestroyView();
        mainActivity.showToolBar();
    }
}