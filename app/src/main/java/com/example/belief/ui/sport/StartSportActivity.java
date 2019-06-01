package com.example.belief.ui.sport;

import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import com.example.belief.R;
import com.example.belief.data.network.model.SportAction;
import com.example.belief.ui.base.BaseActivity;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class StartSportActivity extends BaseActivity implements SportMvpView {

    @Inject
    public SportMvpPresenter<SportMvpView> sportMvpPresenter;

    public Map<String, GifDrawable> actionImgs = new HashMap();

    public List<SportAction> actionList;

    @BindView(R.id.action_image)
    public GifImageView curImg;

    @BindView(R.id.action_name)
    public TextView mName;

    @BindView(R.id.action_detail)
    public TextView mDetail;

    @BindView(R.id.chronometer)
    public Chronometer mChronometer;

    public long sumTime = 0;

    public SportAction curAction;

    @BindView(R.id.task_next)
    public Button TaskNext;

    @BindView(R.id.task_start)
    public Button TaskStart;

    @BindView(R.id.task_privous)
    public Button TaskPrivous;

//    public Timestamp startTime;

    public TaskState taskState = TaskState.NOTSTARTED;

    public enum TaskState{
        NOTSTARTED, STARTED, STOP, END
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sports_gif);
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
        mChronometer.setText("00:00");
        mChronometer.setOnChronometerTickListener(chronometer -> {
        });
        sportMvpPresenter.getSportActions(scid);
    }

    @OnClick(R.id.task_next)
    public void nextTask() {
        sumTime =  (SystemClock.elapsedRealtime() - mChronometer.getBase()) / 1000;

    }

    @OnClick(R.id.task_start)
    public void startTask() {
        switch (taskState) {
            case NOTSTARTED:{
                mChronometer.setBase(SystemClock.elapsedRealtime());
                mChronometer.start();
                TaskStart.setBackground(getResources().getDrawable(R.drawable.ic_stop));
                taskState = TaskState.STARTED;
            }

            case STOP: {
                mChronometer.start();
                TaskStart.setBackground(getResources().getDrawable(R.drawable.ic_stop));
                taskState = TaskState.STARTED;
                break;
            }
            case STARTED:{
                mChronometer.stop();
                taskState = TaskState.STOP;
                TaskStart.setBackground(getResources().getDrawable(R.drawable.ic_g_start));
            }
            default:break;
        }
    }

    @OnClick(R.id.task_privous)
    public void privousTask() {

    }

    public void setData(List<SportAction> list) {
        actionList = list;
        curAction = actionList.get(0);
        actionList.forEach(item -> {
            sportMvpPresenter.downPicGIF(item.getPicUrl());
        });
        mName.setText(curAction.getName());
        mDetail.setText(curAction.getDetail());

    }

    public void setActionImgs(String url) {
        try {
            if (actionImgs.get(url) == null) {
                GifDrawable drawable = new
                        GifDrawable(new File(Environment.getExternalStorageDirectory().getAbsoluteFile(), url));
                actionImgs.put(url, drawable);
                if(curAction.getPicUrl().equals(url)) {
                    curImg.setImageDrawable(actionImgs.get(curAction.getPicUrl()));
//                    onError("帧数" +  drawable.getNumberOfFrames());
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
