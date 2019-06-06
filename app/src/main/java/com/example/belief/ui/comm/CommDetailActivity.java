package com.example.belief.ui.comm;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.belief.R;
import com.example.belief.data.network.model.ShareInfoResponse;
import com.example.belief.ui.base.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
        commMvpPresenter.downPicToCircle(shareInfo.getHeadUrl(), vHead);
        commMvpPresenter.downPic(shareInfo.getPhotoUrl(), vImage);
        vName.setText(shareInfo.getTitle());
        vContent.setText(shareInfo.getContent());
    }

    @OnClick(R.id.bt_share_outside)
    public void shareOutSide() {
        //构造文字与图片结合的画布
//        Bitmap bitmap =((BitmapDrawable)vImage.getDrawable()).getBitmap();
//        Bitmap.Config config = bitmap.getConfig();
//        int sourceBitmapHeight = bitmap.getHeight();
//        int sourceBitmapWidth = bitmap.getWidth();
//        Paint paint = new Paint();
//        paint.setColor(Color.BLACK); // 画笔颜色
//        TextPaint textpaint = new TextPaint(paint);
//        textpaint.setTextSize(20); // 文字大小
//        textpaint.setAntiAlias(true); // 抗锯齿
//        StaticLayout title_layout = new StaticLayout(vContent.getText().toString(), textpaint,
//                sourceBitmapWidth, Layout.Alignment.ALIGN_CENTER, 1f, 1f, true);
//
//        //画分享图
//        Bitmap share_bitmap = Bitmap.createBitmap(sourceBitmapWidth, sourceBitmapHeight +
//                        title_layout.getHeight(), config);
//        Canvas canvas = new Canvas(share_bitmap);
//        canvas.drawColor(Color.WHITE);
//        canvas.drawBitmap(bitmap, 0, 0, paint); // 绘制图片
//        canvas.translate(0, sourceBitmapHeight);
//        title_layout.draw(canvas);
//
//        //压缩
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        share_bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
//        byte[] bytes = baos.toByteArray();
//        share_bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);


        //设置图片
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra(Intent.EXTRA_TEXT, vContent.getText().toString());

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(Intent.createChooser(intent, getTitle()));
    }



    @Override
    public void Back() {

    }
}
