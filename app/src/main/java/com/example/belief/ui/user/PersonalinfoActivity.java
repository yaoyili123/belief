package com.example.belief.ui.user;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.example.belief.MvpApp;
import com.example.belief.R;
import com.example.belief.data.network.model.UserInfo;
import com.example.belief.ui.base.BaseActivity;
import com.example.belief.utils.LQRPhotoSelectUtils;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.addapp.pickers.entity.City;
import cn.addapp.pickers.entity.County;
import cn.addapp.pickers.entity.Province;
import cn.addapp.pickers.picker.DatePicker;
import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

public class PersonalinfoActivity extends BaseActivity implements View.OnClickListener,
        UserMvpView {

    @Inject
    UserMvpPresenter<UserMvpView> userMvpPresenter;

    private View sexChangePage;
    private View nameChangePage;
    private View cityChangePage;
    private View birthChangePage;
    private View imageChangePage;
    private LQRPhotoSelectUtils mLqrPhotoSelectUtils;
    private ImageView image;

    private TextView cityView;
    private TextView name;
    private TextView sex;
    private TextView birth;

    private UserInfo curUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personalinfo);
        getActivityComponent().inject(this);
        userMvpPresenter.onAttach(this);
        setUnbinder(ButterKnife.bind(this));
        setUp();

//        if (Build.VERSION.SDK_INT >= 23) {
//            int REQUEST_CODE_CONTACT = 101;
//            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                    Manifest.permission.READ_EXTERNAL_STORAGE, };
//            //验证是否许可权限
//            for (String str : permissions) {
//                if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
//                    //申请权限
//                    this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
//                    return;
//                }
//            }
//        }
    }

    protected void setUp() {
        sexChangePage = findViewById(R.id.sexChangePage);
        nameChangePage = findViewById(R.id.nameChangePage);
        cityChangePage = findViewById(R.id.cityChangePage);
        imageChangePage = findViewById(R.id.imageChangePage);
        image = (ImageView)findViewById(R.id.image);
//        initView();
        sexChangePage.setOnClickListener(this);
        nameChangePage.setOnClickListener(this);

        sex = (TextView) findViewById(R.id.sex);
        cityView = (TextView) findViewById(R.id.city);
        birth = (TextView) findViewById(R.id.birth);
        name = (TextView) findViewById(R.id.name);

        initImgSelected();
        imageChangePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 3、调用从图库选取图片方法
                PermissionGen.needPermission(PersonalinfoActivity.this,
                        LQRPhotoSelectUtils.REQ_SELECT_PHOTO,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE}
                );
            }
        });

        userMvpPresenter.getUserInfo(MvpApp.get(this).getCurUser().getUid());
    }

    @Override
    public void setData(Object info) {
        curUser = (UserInfo)info;
        userMvpPresenter.downPic(((UserInfo) info).getPhotoUrl(), image);
        name.setText(curUser.getName());
        cityView.setText(curUser.getCity());
        birth.setText(curUser.getBothday().toString());
        sex.setText(curUser.getSex());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sexChangePage: {
                Intent intent =  new Intent(PersonalinfoActivity.this, UpdateInfoActivity.class);
                intent.putExtra("action", "sex");
                startActivityForResult(intent, 1);
                break;
            }
            case R.id.nameChangePage:{
                Intent intent =  new Intent(PersonalinfoActivity.this, UpdateInfoActivity.class);
                intent.putExtra("action", "name");
                startActivityForResult(intent, 5);
                break;
            }
        }
    }

    @OnClick(R.id.bt_update)
    public void updateInfo() {
        userMvpPresenter.uploadPic(image, curUser.getPhotoUrl());
        userMvpPresenter.updateUserInfo(curUser);
    }

    private void initImgSelected() {
        // 1、创建LQRPhotoSelectUtils（一个Activity对应一个LQRPhotoSelectUtils）
        mLqrPhotoSelectUtils = new LQRPhotoSelectUtils(this, new LQRPhotoSelectUtils.PhotoSelectListener() {
            @Override
            public void onFinish(File outputFile, Uri outputUri) {
                // 4、当拍照或从图库选取图片成功后回调
                Glide.with(PersonalinfoActivity.this)
                        .load(outputUri)
//                        .error(R.drawable.sport_drill)
                        .into(new ImageViewTarget<Drawable>(image) {
                            @Override
                            protected void setResource(Drawable resource) {
                                image.setImageDrawable(resource);
                            }

//                            @Override
//                            public void onLoadFailed(Exception e, Drawable errorDrawable) {
//                                super.onLoadFailed(e, errorDrawable);
//                                e.printStackTrace();
//                            }
                        });
                curUser.setPhotoUrl(outputFile.getName());

            }
        }, false);//true裁剪，false不裁剪

    }

    @PermissionSuccess(requestCode = LQRPhotoSelectUtils.REQ_SELECT_PHOTO)
    private void selectPhoto() {
        mLqrPhotoSelectUtils.selectPhoto();
    }

    @PermissionFail(requestCode = LQRPhotoSelectUtils.REQ_SELECT_PHOTO)
    private void showTip2() {
        //        Toast.makeText(getApplicationContext(), "不给我权限是吧，那就别玩了", Toast.LENGTH_SHORT).show();
        showDialog();
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    public void showDialog() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK){
            String result = data.getExtras().getString("sex");
            sex.setText("");
            sex.setText(result);
            curUser.setSex(result);
        }

        else if (requestCode == mLqrPhotoSelectUtils.REQ_SELECT_PHOTO && resultCode == RESULT_OK)   {
            mLqrPhotoSelectUtils.attachToActivityForResult(requestCode, resultCode, data);
        }

        else if (requestCode == 4 && resultCode == RESULT_OK)   {
            String newBirth= data.getExtras().getString("birth");
            birth.setText("");
            birth.setText(newBirth);

            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                curUser.setBothday(sdf.parse(newBirth).toString());
            }
            catch (ParseException e) {
                e.printStackTrace();
            }

        }
        else if (requestCode == 5 && resultCode == RESULT_OK)   {
            String newName= data.getExtras().getString("name");
            name.setText("");
            name.setText(newName);
            curUser.setName(newName);
        }

//
    }

    public void onAddress3Picker(View view) {
        AddressPickTask task = new AddressPickTask(this);
        task.setHideCounty(true);
        task.setCallback(new AddressPickTask.Callback() {
            @Override
            public void onAddressInitFailed() {
                showToast("数据初始化失败");
            }

            @Override
            public void onAddressPicked(Province province, City city, County county) {
                cityView.setText(province.getAreaName() + " " + city.getAreaName());
                curUser.setCity(province.getAreaName() + " " + city.getAreaName());
            }
        });
        String[] tmps = curUser.getCity().split(" ");
        task.execute(tmps[0], tmps[1]);
    }

    public void onYearMonthDayPicker(View view) {
        final DatePicker picker = new DatePicker(this);
        picker.setCanLoop(true);
        picker.setWheelModeEnable(true);
        picker.setTopPadding(15);
        picker.setRangeStart(1900, 8, 29);
        picker.setRangeEnd(2111, 1, 11);
        String cury = curUser.getBothday().substring(0, 4);
        String curm = curUser.getBothday().substring(5, 7);
        String curd = curUser.getBothday().substring(8, 10);
        picker.setSelectedItem(Integer.parseInt(cury),
                Integer.parseInt(curm), Integer.parseInt(curd));
        picker.setWeightEnable(true);
        picker.setLineColor(Color.BLACK);
        picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                birth.setText(year + "-" + month + "-" + day);
                curUser.setBothday(year + "-" + month + "-" + day);
            }
        });
        picker.setOnWheelListener(new DatePicker.OnWheelListener() {
            @Override
            public void onYearWheeled(int index, String year) {
                picker.setTitleText(year + "-" + picker.getSelectedMonth() + "-" + picker.getSelectedDay());
            }

            @Override
            public void onMonthWheeled(int index, String month) {
                picker.setTitleText(picker.getSelectedYear() + "-" + month + "-" + picker.getSelectedDay());
            }

            @Override
            public void onDayWheeled(int index, String day) {
                picker.setTitleText(picker.getSelectedYear() + "-" + picker.getSelectedMonth() + "-" + day);
            }
        });
        picker.show();
    }

    @Override
    public void openMainActivity(Map args) {

    }
}
