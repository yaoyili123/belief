package com.example.belief.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.belief.MvpApp;
import com.example.belief.R;
import com.example.belief.data.network.model.UserInfo;
import com.example.belief.data.network.model.UserSportInfo;
import com.example.belief.di.component.ActivityComponent;
import com.example.belief.ui.MainActivity;
import com.example.belief.ui.base.BaseFragment;
import com.example.belief.ui.sport.ShowClassActivity;

import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*用户模块一级页面
 * */
public class UserMainFragment extends BaseFragment implements UserMvpView{

    MainActivity mainActivity;

    private UserInfo curUser;

    @BindView(R.id.user_main_name)
    public TextView mName;

    @BindView(R.id.user_main_sex)
    public TextView mSex;

    @BindView(R.id.user_main_head)
    public ImageView imageView;

    @BindView(R.id.usi_totalTime)
    public TextView tTotalTime;

    @BindView(R.id.usi_today)
    public TextView tToday;

    @BindView(R.id.usi_sumkcal)
    public TextView tSumKcal;

    @Inject
    UserMvpPresenter<UserMvpView> userMvpPresenter;

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

    @OnClick(R.id.bt_update_info)
    public void updateInfo() {
        if (!MvpApp.get(getBaseActivity()).isLogined(getBaseActivity())) {
            getBaseActivity().startActivity(LoginActivity.getStartIntent(getBaseActivity()));
            return;
        }
        Intent intent = new Intent(getBaseActivity(), PersonalinfoActivity.class);
        startActivity(intent);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.farg_user_main, container, false);
        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnbinder(ButterKnife.bind(this, view));
            userMvpPresenter.onAttach(this);
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("MyLog", "UserMainFragment onResume()");
        setUp(null);
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        Log.d("MyLog", "UserMainFragment onStart()");
//    }

    @Override
    protected void setUp(View view) {
        Glide.with(this).load(R.drawable.unlogined).into(imageView);
        mName.setText("未登录");
        mSex.setText("？");
        if (MvpApp.get(getBaseActivity()).isLogined(getBaseActivity())) {
            userMvpPresenter.getUserInfo(MvpApp.get(getContext()).getCurUser().getUid());
            userMvpPresenter.getSportInfo(MvpApp.get(getContext()).getCurUser().getUid());
        }
    }

    @Override
    public void setData(Object obj) {
        curUser = (UserInfo)obj;
        userMvpPresenter.downPic(curUser.getPhotoUrl(), imageView);
        mName.setText(curUser.getName());
        mSex.setText(curUser.getSex());
    }

    public void setSportInfo(UserSportInfo sportInfo) {
        Integer totalName =  sportInfo.getTotalSportTime();
        Integer tmp = totalName / 60;
        tTotalTime.setText(tmp.toString());
        tToday.setText(sportInfo.getTodayKcal().toString());
        tSumKcal.setText(sportInfo.getTotalKcal().toString());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void openMainActivity(Map args) {

    }

    @OnClick(R.id.bt_myClass)
    public void toMyClass() {
        if (!MvpApp.get(getBaseActivity()).isLogined(getBaseActivity())) {
            getBaseActivity().startActivity(LoginActivity.getStartIntent(getBaseActivity()));
            return;
        }
        Intent intent=new Intent(getContext(), ShowClassActivity.class);
        intent.putExtra("business", 1);
        getActivity().startActivity(intent);
    }

    @OnClick(R.id.bt_exit)
    public void exit() {
        MvpApp.get(getContext()).setCurUser(null);
        startActivity(MainActivity.getStartIntent(getActivity()));
        getActivity().finish();
    }
}