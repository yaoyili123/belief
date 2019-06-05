package com.example.belief.ui.comm;

import android.Manifest;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.example.belief.MvpApp;
import com.example.belief.R;
import com.example.belief.data.network.model.RequestShare;
import com.example.belief.ui.base.BaseActivity;
import com.example.belief.utils.LQRPhotoSelectUtils;

import java.io.File;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

public class AddShareActivity extends BaseActivity implements CommMvpView {

    @Inject
    CommMvpPresenter<CommMvpView> commMvpPresenter;

    @BindView(R.id.addShare_title)
    public TextView mTitle;

    @BindView(R.id.addShare_content)
    public TextView mContent;

    @BindView(R.id.addShare_pic)
    public ImageView mPic;

    private LQRPhotoSelectUtils mLqrPhotoSelectUtils;

    public RequestShare shareInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share_infor);
        getActivityComponent().inject(this);
        commMvpPresenter.onAttach(this);
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

    @Override
    protected void setUp() {
        shareInfo = new RequestShare();
        //FIXME：由于下载代码是glideBitmap所以用glide加载图片
        Glide.with(this).load(R.drawable.unlogined).into(mPic);
        mLqrPhotoSelectUtils = new LQRPhotoSelectUtils(this, new LQRPhotoSelectUtils.PhotoSelectListener() {
            @Override
            public void onFinish(File outputFile, Uri outputUri) {
                // 4、当拍照或从图库选取图片成功后回调
                Glide.with(AddShareActivity.this)
                        .load(outputUri)
//                        .error(R.drawable.sport_drill)
                        .into(new ImageViewTarget<Drawable>(mPic) {
                            @Override
                            protected void setResource(Drawable resource) {
                                mPic.setImageDrawable(resource);
                            }

//                            @Override
//                            public void onLoadFailed(Exception e, Drawable errorDrawable) {
//                                super.onLoadFailed(e, errorDrawable);
//                                e.printStackTrace();
//                            }
                        });
                shareInfo.setPhotoUrl(outputFile.getName());

            }
        }, false);//true裁剪，false不裁剪
        mPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 3、调用从图库选取图片方法
                PermissionGen.needPermission(AddShareActivity.this,
                        LQRPhotoSelectUtils.REQ_SELECT_PHOTO,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE}
                );
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == mLqrPhotoSelectUtils.REQ_SELECT_PHOTO && resultCode == RESULT_OK) {
            mLqrPhotoSelectUtils.attachToActivityForResult(requestCode, resultCode, data);
        }
    }

    @OnClick(R.id.bt_share)
    public void addShare() {
        if(mTitle.getText().toString().isEmpty()) {
            onError("标题不能为空");
            return;
        }
        shareInfo.setTitle(mTitle.getText().toString());
        shareInfo.setDetail(mContent.getText().toString());
        if (shareInfo.getPhotoUrl() == null) {
            shareInfo.setPhotoUrl("unlogined.jpg");
        }
        shareInfo.setUid(MvpApp.get(this).getCurUser().getUid());
        commMvpPresenter.publishShare(shareInfo);
        commMvpPresenter.uploadPic(mPic, shareInfo.getPhotoUrl());
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

    public void showDialog() {

    }

    @Override
    public void Back() {
        finish();
    }
}
