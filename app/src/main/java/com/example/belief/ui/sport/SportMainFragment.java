package com.example.belief.ui.sport;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.belief.MvpApp;
import com.example.belief.R;
import com.example.belief.data.network.model.UserAuth;
import com.example.belief.di.component.ActivityComponent;
import com.example.belief.ui.base.BaseFragment;
import com.example.belief.ui.user.LoginActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/*运动模块一级页面
* */
public class SportMainFragment extends BaseFragment implements SportMvpView {

    @BindView(R.id.user_head)
    public CircleImageView userHead;

    @BindView(R.id.sport_time_sum)
    public TextView sportTimeSum;

    @Inject
    SportMvpPresenter<SportMvpView> sportMvpPresenter;

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
        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnbinder(ButterKnife.bind(this, view));
            sportMvpPresenter.onAttach(this);
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setUp(null);
    }

    @Override
    protected void setUp(View view) {
        //检查是否登陆，没登陆啥也不干
        if (!MvpApp.get(getContext()).isLogined(getBaseActivity()))
            return;
        UserAuth userAuth = MvpApp.get(getContext()).getCurUser();
        String imageName = MvpApp.get(getContext()).getUserHead();
        sportMvpPresenter.onMainViewPrepared(userAuth, imageName);
    }

    @OnClick(R.id.bt_classList)
    public void toClassList(View v) {
        if (!MvpApp.get(getBaseActivity()).isLogined(getBaseActivity())) {
            getBaseActivity().startActivity(LoginActivity.getStartIntent(getBaseActivity()));
            return;
        }
        Intent intent=new Intent(getContext(), ShowClassActivity.class);
        intent.putExtra("business", 1);
        getActivity().startActivity(intent);
    }

    @OnClick(R.id.bt_addClass)
    public void toAddClass(View v) {
        if (!MvpApp.get(getBaseActivity()).isLogined(getBaseActivity())) {
            getBaseActivity().startActivity(LoginActivity.getStartIntent(getBaseActivity()));
            return;
        }
        Intent intent=new Intent(getContext(), ShowClassActivity.class);
        intent.putExtra("business", 2);
        getActivity().startActivity(intent);
    }

    @OnClick(R.id.bt_sport_time)
    public void toBodyInfo(View v) {
        if (!MvpApp.get(getBaseActivity()).isLogined(getBaseActivity())) {
            getBaseActivity().startActivity(LoginActivity.getStartIntent(getBaseActivity()));
            return;
        }
//        Intent intent=new Intent(getContext(), ShowClassActivity.class);
//        getActivity().startActivity(intent);
    }
}
