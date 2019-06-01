package com.example.belief.ui.sport;

import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import com.example.belief.MvpApp;
import com.example.belief.R;
import com.example.belief.data.network.model.SportAction;
import com.example.belief.ui.base.BaseActivity;
import com.example.belief.utils.ToastUtils;
import com.example.belief.utils.UIUtils;
import com.flyco.dialog.widget.NormalDialog;

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

    @BindView(R.id.task_next)
    public Button TaskNext;

    @BindView(R.id.task_start)
    public Button TaskStart;

    @BindView(R.id.task_privous)
    public Button TaskPrivous;

    @BindView(R.id.action_image)
    public GifImageView curImg;

    @BindView(R.id.action_name)
    public TextView mName;

    @BindView(R.id.action_detail)
    public TextView mDetail;

    @BindView(R.id.chronometer)
    public Chronometer mChronometer;

    private NormalDialog mDialog;

    //数据管理
    public Map<String, GifDrawable> actionImgs = new HashMap();

    public List<SportAction> actionList;

    private int kcal;

    //任务控制
    private SportAction curAction;

    private int curTaskOrder = 1;

    private long sumTime = 0;

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
    protected void setUp() {
        int scid  = getIntent().getIntExtra("scid", 0);
        kcal  = getIntent().getIntExtra("kcal", 0);
        mChronometer.setText("00:00");
        mChronometer.setOnChronometerTickListener(chronometer -> {
        });
        sportMvpPresenter.getSportActions(scid);
    }

    @OnClick(R.id.task_privous)
    public void privousTask() {
        if (curTaskOrder == 1) {
            onError("已经是最开始任务");
            return;
        }
        sumUpTime();
        mChronometer.stop();
        mChronometer.setText("00:00");
        curAction = actionList.get((curTaskOrder-- - 2));
        Log.d("privousTask", "curTaskOrder: " + curTaskOrder);
        switchView(curAction);
        taskState = TaskState.NOTSTARTED;
        TaskStart.setBackground(getResources().getDrawable(R.drawable.ic_g_start));
    }

    @OnClick(R.id.task_next)
    public void nextTask() {
        //动作完成
        if (curTaskOrder == actionList.size()) {
            sumUpTime();
            String result = new String("运动总时间："+ sumTime + "秒\n"
                    + "消耗卡路里："+ kcal + " 千卡\n"
                    + "是否结束运动?");
            NormalDialog mDialog = UIUtils.getNormalDialog(result, this);
            mDialog.setOnBtnClickL(() -> { mDialog.dismiss(); }, () -> {
                sportMvpPresenter.settleKcal(MvpApp.get(this).getCurUser().getUid(),
                        kcal, (int)sumTime);
                finish();
            });
            mDialog.show();
            return;
        }
        sumUpTime();
        mChronometer.stop();
        mChronometer.setText("00:00");
        curAction = actionList.get(curTaskOrder++);
        switchView(curAction);
        taskState = TaskState.NOTSTARTED;
        TaskStart.setBackground(getResources().getDrawable(R.drawable.ic_g_start));
    }

    private void switchView(SportAction action) {
        curImg.setImageDrawable(actionImgs.get(action.getPicUrl()));
        mName.setText(action.getName());
        mDetail.setText(action.getDetail());
    }

    private void sumUpTime() {
//        curTimeText = mChronometer.getText().toString();
        if (taskState != TaskState.NOTSTARTED)
            sumTime +=  (SystemClock.elapsedRealtime() - mChronometer.getBase()) / 1000;
        ToastUtils.showToast("总时间" + sumTime);
    }

    @OnClick(R.id.task_start)
    public void startTask() {
        switch (taskState) {
            case NOTSTARTED: case STOP:{
                mChronometer.setBase(SystemClock.elapsedRealtime());
                mChronometer.start();
                TaskStart.setBackground(getResources().getDrawable(R.drawable.ic_stop));
                taskState = TaskState.STARTED;
                break;
            }
            case STARTED:{
                mChronometer.stop();
                sumUpTime();
                taskState = TaskState.STOP;
                TaskStart.setBackground(getResources().getDrawable(R.drawable.ic_g_start));
                break;
            }
            default:break;
        }
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

    @Override
    protected void onDestroy() {
        sportMvpPresenter.onDetach();
        super.onDestroy();
    }
}
