package com.example.belief.ui.comm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.belief.R;
import com.example.belief.ui.base.BaseFragment;

import butterknife.ButterKnife;

public class CommMainFragment extends BaseFragment {

//    @BindView(R.id.com2_rl_1)
//    public ViewGroup vg;

    public static CommMainFragment newInstance() {
        Bundle args = new Bundle();
        CommMainFragment fragment = new CommMainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.communitylayout, container, false);
        setUnbinder(ButterKnife.bind(this, view));
        return view;
    }


    @Override
    protected void setUp(View view) {

    }

    //    @OnClick(R.id.com2_rl_1)
//    public void goDetail(View v) {
//        Intent intent=new Intent(getContext(), CommDetailActivity.class);
//        getActivity().startActivity(intent);
//    }
}
