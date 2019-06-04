package com.example.belief.ui.comm;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.belief.R;
import com.example.belief.data.network.model.ShareInfoResponse;
import com.example.belief.ui.base.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommDetailActivity extends BaseActivity implements CommMvpView {

    @Inject
    CommMvpPresenter<CommMvpView> commMvpPresenter;

    @BindView(R.id.share_detail_head)
    public ImageView vHead;

    @BindView(R.id.share_detail_name)
    public TextView vName;

    @BindView(R.id.share_detail_content)
    public TextView vContent;

    @BindView(R.id.share_detail_img)
    public ImageView vImage;

    public ShareInfoResponse shareInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_comm_dynamic_main);
        getActivityComponent().inject(this);
        commMvpPresenter.onAttach(this);
        setUnbinder(ButterKnife.bind(this));
        setUp();
    }

    @Override
    protected void setUp() {
        int sid = getIntent().getIntExtra("sid", 1);
        commMvpPresenter.getShareDetail(sid);
    }

    public void setData(ShareInfoResponse data) {
        shareInfo = data;
        commMvpPresenter.downPic(shareInfo.getHeadUrl(), vHead);
        commMvpPresenter.downPic(shareInfo.getPhotoUrl(), vImage);
        vName.setText(shareInfo.getTitle());
        vContent.setText(shareInfo.getContent());
    }

    @Override
    public void Back() {

    }
}
