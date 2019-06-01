package com.example.belief.ui.sport;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.belief.MvpApp;
import com.example.belief.R;
import com.example.belief.ui.base.BaseActivity;

import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ClassDetailActivity extends BaseActivity implements SportMvpView {

    @BindView(R.id.class_detail_title)
    public TextView mTitle;

    @BindView(R.id.class_detail_kcal)
    public TextView mKcal;

    @BindView(R.id.class_detail_time)
    public TextView mTime;

    @BindView(R.id.class_detail_level)
    public TextView mLevel;

    @BindView(R.id.class_detail_detail)
    public TextView mDetail;

    @BindView(R.id.class_detail_start)
    public Button mStart;

    @Inject
    public SportMvpPresenter<SportMvpView> sportMvpPresenter;

    private Map sportClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_class_main);
        getActivityComponent().inject(this);
        sportMvpPresenter.onAttach(this);
        setUnbinder(ButterKnife.bind(this));
        setUp();
    }

    @Override
    protected void onDestroy() {
        sportMvpPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    protected void setUp() {
        int scid  = getIntent().getIntExtra("scid", 0);
        sportMvpPresenter.getSportClass(scid, MvpApp.get(this).getCurUser().getUid());
    }

    public void setData(Map data) {
        sportClass = (Map) data.get("data");
        mTitle.setText((String)sportClass.get("name"));
        mKcal.setText(((Double)sportClass.get("kcal")).toString() + "千卡");
        mTime.setText(((Double)sportClass.get("time")).toString() + "分钟");
        mLevel.setText(MvpApp.get(this).classLevel.get(((Double)sportClass.get("level")).intValue()));
        mDetail.setText((String)sportClass.get("detail"));
        if (((boolean)data.get("joined")))
            mStart.setText("已参加，开始训练");
        else
            mStart.setText("参加课程并开始训练");
    }

    @OnClick(R.id.class_detail_start)
    public void startSport(View v) {
        Intent intent = new Intent(this, StartSportActivity.class);
        intent.putExtra("scid", ((Double) sportClass.get("scid")).intValue());
        intent.putExtra("kcal", ((Double) sportClass.get("kcal")).intValue());
        this.startActivity(intent);
        sportMvpPresenter.addClassToUser(((Double) sportClass.get("scid")).intValue(),
                MvpApp.get(this).getCurUser().getUid());
    }
}

