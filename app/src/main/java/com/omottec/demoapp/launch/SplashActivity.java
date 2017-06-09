package com.omottec.demoapp.launch;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

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

/**
 * Created by qinbingbing on 17/03/2017.
 */

public class SplashActivity extends BaseActivity {
    private static final String IMG_URI = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1496933431724&di=81c5c1a4aad63cd1c9ac8e7777c49e11&imgtype=0&src=http%3A%2F%2Fimage.tianjimedia.com%2FuploadImages%2F2012%2F326%2FXP07382U1K18.jpg";
    private View mContentView;
    private ImageView mSplashIv;
    private SimpleDraweeView mSplashSdv;
    private boolean mJump2Main = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mToolbar.setVisibility(View.GONE);
        if (mJump2Main) {
            jump2MainActivity();
        } else {
            // 使用占位图和直接加载图片
            /*GenericDraweeHierarchyBuilder builder =
                    new GenericDraweeHierarchyBuilder(getResources());
            GenericDraweeHierarchy hierarchy = builder
                    .setPlaceholderImage(R.drawable.splash)
                    .build();
            mSplashSdv.setHierarchy(hierarchy);
            mSplashSdv.setImageURI(IMG_URI);
            MyApplication.getUiHandler().postDelayed(() -> jump2MainActivity(), 2 * 1000);*/

            /**
             * 有缓存使用缓存，延迟2s跳主页
             * 无缓存使用默认图，下载，立即跳主页
             */
            ImagePipeline imagePipeline = Fresco.getImagePipeline();
            Uri uri = Uri.parse(IMG_URI);
            ImageRequest imageRequest = ImageRequest.fromUri(IMG_URI);
            DataSource<Boolean> inDiskCacheSource = imagePipeline.isInDiskCache(uri);
            DataSubscriber<Boolean> subscriber = new BaseDataSubscriber<Boolean>() {
                @Override
                protected void onNewResultImpl(DataSource<Boolean> dataSource) {
                    Logger.d(Tag.SPLASH, "onNewResultImpl");
                    if (!dataSource.isFinished()) {
                        mSplashSdv.setImageResource(R.drawable.splash);
                        imagePipeline.prefetchToDiskCache(imageRequest, this);
                        jump2MainActivity();
                        return;
                    }
                    boolean isInCache = dataSource.getResult();
                    Logger.d(Tag.SPLASH, "isInCache:" + isInCache);
                    if (isInCache) {
                        mSplashSdv.setImageURI(uri);
                        MyApplication.getUiHandler().postDelayed(() -> jump2MainActivity(), 2 * 1000);
                    } else {
                        mSplashSdv.setImageResource(R.drawable.splash);
                        imagePipeline.prefetchToDiskCache(imageRequest, this);
                        jump2MainActivity();
                    }

                }

                @Override
                protected void onFailureImpl(DataSource<Boolean> dataSource) {
                    Logger.d(Tag.SPLASH, "onFailureImpl");
                    mSplashSdv.setImageResource(R.drawable.splash);
                    imagePipeline.prefetchToDiskCache(imageRequest, this);
                    jump2MainActivity();
                }
            };
            inDiskCacheSource.subscribe(subscriber, UiThreadImmediateExecutorService.getInstance());
        }
    }

    private void jump2MainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected View createContentView() {
        mContentView = View.inflate(this, R.layout.a_splash, null);
        return mContentView;
    }

    @Override
    protected void onViewCreated(View view) {
        mSplashIv = (ImageView) mContentView.findViewById(R.id.iv_splash);
        mSplashSdv = (SimpleDraweeView) mContentView.findViewById(R.id.sdv_splash);
        mSplashSdv.setScaleType(ImageView.ScaleType.FIT_XY);
    }
}
