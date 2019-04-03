package com.example.belief.ui.sport;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.belief.R;
import com.example.belief.ui.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*运动模块一级页面
* */
public class SportMainFragment extends BaseFragment {

    @BindView(R.id.test)
    TextView test;

    public static SportMainFragment newInstance(String name) {
        Bundle args = new Bundle();
        args.putString("name", name);
        SportMainFragment fragment = new SportMainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_sport_main, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.test)
    public void lookClass(View v) {
        Intent intent=new Intent(getContext(), ManageSportActivity.class);
        getActivity().startActivity(intent);
    }
}
