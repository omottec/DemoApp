package com.omottec.demoapp.launch;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.telecom.TelecomManager;
import android.view.View;

import com.facebook.common.executors.UiThreadImmediateExecutorService;
import com.facebook.datasource.BaseDataSubscriber;
import com.facebook.datasource.DataSource;
import com.facebook.datasource.DataSubscriber;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.request.ImageRequest;
import com.omottec.demoapp.R;
import com.omottec.demoapp.Tag;
import com.omottec.demoapp.activity.BaseActivity;
import com.omottec.demoapp.app.MyApplication;
import com.omottec.demoapp.task.MainActivity;
import com.omottec.demoapp.utils.Logger;

import java.util.concurrent.TimeUnit;

/**
 * Created by qinbingbing on 17/03/2017.
 */

public class SplashActivity extends BaseActivity {
    private static final String IMG_URI = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1496933431724&di=81c5c1a4aad63cd1c9ac8e7777c49e11&imgtype=0&src=http%3A%2F%2Fimage.tianjimedia.com%2FuploadImages%2F2012%2F326%2FXP07382U1K18.jpg";
    private SimpleDraweeView mSplashSdv;
    private boolean mJump2Main = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mToolbar.setVisibility(View.GONE);
        if (mJump2Main) {
            finishDisplay();
        } else {
            



            // 使用占位图和直接加载图片
            /*GenericDraweeHierarchyBuilder builder =
                    new GenericDraweeHierarchyBuilder(getResources());
            GenericDraweeHierarchy hierarchy = builder
                    .setPlaceholderImage(R.drawable.splash)
                    .build();
            mSplashSdv.setHierarchy(hierarchy);
            mSplashSdv.setImageURI(IMG_URI);
            MyApplication.getUiHandler().postDelayed(() -> finishDisplay(), 2 * 1000);*/

            /**
             * 有缓存使用缓存，延迟2s跳主页
             * 无缓存使用默认图，下载，立即跳主页
             */
            SystemClock.sleep(2000);
            ImagePipeline imagePipeline = Fresco.getImagePipeline();
            Uri uri = Uri.parse(IMG_URI);
            ImageRequest imageRequest = ImageRequest.fromUri(IMG_URI);
            DataSource<Boolean> inDiskCacheSource = imagePipeline.isInDiskCache(uri);
            DataSubscriber<Boolean> subscriber = new BaseDataSubscriber<Boolean>() {
                @Override
                protected void onNewResultImpl(DataSource<Boolean> dataSource) {
                    boolean finished = dataSource.isFinished();
                    Logger.d(Tag.SPLASH, "onNewResultImpl dataSource.isFinished: " + finished);
                    if (!finished) {
                        imagePipeline.prefetchToDiskCache(imageRequest, this);
                        finishDisplay();
                        return;
                    }
                    boolean isInCache = dataSource.getResult();
                    Logger.d(Tag.SPLASH, "onNewResultImpl isInCache: " + isInCache);
                    if (isInCache) {
                        mSplashSdv.setImageURI(uri);
                        finishDisplay();
                    } else {
                        imagePipeline.prefetchToDiskCache(imageRequest, this);
                        finishDisplay();
                    }

                }

                @Override
                protected void onFailureImpl(DataSource<Boolean> dataSource) {
                    Logger.d(Tag.SPLASH, "onFailureImpl");
                    imagePipeline.prefetchToDiskCache(imageRequest, this);
                    finishDisplay();
                }
            };
            inDiskCacheSource.subscribe(subscriber, UiThreadImmediateExecutorService.getInstance());
        }
    }

    private void finishDisplay() {
//        MyApplication.getUiHandler().postDelayed(() -> {
//            startActivity(new Intent(this, MainActivity.class));
//            finish();
//            overridePendingTransition(0, R.anim.translate_from_center_to_left);
//        }, TimeUnit.SECONDS.toMillis(2));
    }

    @Override
    protected View createContentView() {
        mSplashSdv = (SimpleDraweeView) View.inflate(this, R.layout.a_splash, null);
        return mSplashSdv;
    }

    @Override
    protected void onViewCreated(View view) {
    }
}
