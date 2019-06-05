package com.example.belief.ui.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.belief.data.DataManager;
import com.example.belief.utils.FileUtils;
import com.example.belief.utils.rx.SchedulerProvider;

import java.io.ByteArrayOutputStream;
import java.io.File;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/*
* P层基类，Presenter需要持有的state: M层接口、V层组件引用
* work：修改全局状态、给View的UI层传数据，下达View修改UI的指令（更改UI、显示后台数据错误等）
* */
public class BasePresenter<V extends MvpView> implements MvpPresenter<V> {

    private V mMvpView;

    private final DataManager mDataManager;

    private final SchedulerProvider mSchedulerProvider;

    //统一处理订阅，一个Observable管理器
    private final CompositeDisposable mCompositeDisposable;

    @Inject
    public BasePresenter(DataManager dataManager,
                         SchedulerProvider schedulerProvider,
                         CompositeDisposable compositeDisposable) {
        this.mDataManager = dataManager;
        this.mSchedulerProvider = schedulerProvider;
        this.mCompositeDisposable = compositeDisposable;
    }


    @Override
    public void onAttach(V mvpView) {
        mMvpView = mvpView;
    }

    @Override
    public void onDetach() {
        mCompositeDisposable.dispose();
        mMvpView = null;
    }

    public boolean isViewAttached() {
        return mMvpView != null;
    }

    @Override
    public void downPic(String url, ImageView imageView) {
        if (url == null || url.isEmpty())
            return;
        //获取用户头像
        getCompositeDisposable().add(getDataManager().downPic(url)
                .map((ResponseBody body) -> {
                    //FIXME: 访问body中流只能有一次，第二次会closed
//                    byte[] bytes =
                    FileUtils.createFileWithByte(body.bytes(), url);
//                    InputStream stream = new ByteArrayInputStream(bytes);
//                    return BitmapFactory.decodeStream(stream);
                    return url;
                })
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(fileName -> {
//                    Log.d("MyLog", " " + bitmap);
//                    imageView.setImageBitmap(bitmap);
                    if (getMvpView() instanceof Fragment) {
                        Glide.with((Fragment) getMvpView())
                                .load(new File(Environment.getExternalStorageDirectory().getAbsoluteFile(), fileName))
                                .into(imageView);
                    }
                    else {
                        Glide.with((Context) getMvpView())
                                .load(new File(Environment.getExternalStorageDirectory().getAbsoluteFile(), fileName))
                                .into(imageView);
                    }

                }, (e) -> {
                    e.printStackTrace();
                    getMvpView().onError("图片加载失败");
                    getMvpView().handleApiError(e);
                }));
    }

    @Override
    public void downPicToCircle(String url, ImageView imageView) {
        if (url == null || url.isEmpty())
            return;
        //获取用户头像
        getCompositeDisposable().add(getDataManager().downPic(url)
                .map((ResponseBody body) -> {
                    FileUtils.createFileWithByte(body.bytes(), url);
                    return url;
                })
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(fileName -> {
                    if (getMvpView() instanceof Fragment) {
                        Glide.with((Fragment) getMvpView())
                                .load(new File(Environment.getExternalStorageDirectory().getAbsoluteFile(), fileName))
                                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                                .into(imageView);
                    }
                    else {
                        Glide.with((Context) getMvpView())
                                .load(new File(Environment.getExternalStorageDirectory().getAbsoluteFile(), fileName))
                                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                                .into(imageView);
                    }
                }, (e) -> {
                    e.printStackTrace();
                    getMvpView().onError("图片加载失败");
                    getMvpView().handleApiError(e);
                }));
    }

    @Override
    public void uploadPic(ImageView imageView, String filename) {
        //FIXME:这才是正确获取图片的方法
        Bitmap bitmap =  ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        String[] tmps = filename.split("\\.");
        String suffix = tmps[tmps.length-1];
        Log.d("myLog", "图片格式" + suffix);
        RequestBody requestFile = RequestBody.create(MediaType.parse("application/otcet-stream"),
                Bitmap2Bytes(bitmap, suffix));

        getCompositeDisposable().add(getDataManager().UploadFile(requestFile, filename)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe((map) -> {

                }, (e) -> {
                    e.printStackTrace();
                }));
    }

    private byte[] Bitmap2Bytes(Bitmap bm, String format){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        switch (format) {
            case "png":
                bm.compress(Bitmap.CompressFormat.PNG, 100, baos); break;
            case "jpg":
                bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); break;
        }
        return baos.toByteArray();
    }

    private Bitmap drawable2bitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else if (drawable instanceof NinePatchDrawable) {
            Bitmap bitmap = Bitmap
                    .createBitmap(
                            drawable.getIntrinsicWidth(),
                            drawable.getIntrinsicHeight(),
                            drawable.getOpacity() != PixelFormat.OPAQUE ?
                                    Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight());
            drawable.draw(canvas);
            return bitmap;
        } else {
            return null;
        }
    }


    public V getMvpView() {
        return mMvpView;
    }

    public DataManager getDataManager() {
        return mDataManager;
    }

    public SchedulerProvider getSchedulerProvider() {
        return mSchedulerProvider;
    }

    public CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }

}
