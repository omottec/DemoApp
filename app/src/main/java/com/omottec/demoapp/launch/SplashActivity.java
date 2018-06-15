package com.omottec.demoapp.launch;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
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
import com.omottec.demoapp.permission.Permissions;
import com.omottec.demoapp.task.MainActivity;
import com.omottec.demoapp.utils.Logger;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * Created by qinbingbing on 17/03/2017.
 */

public class SplashActivity extends AppCompatActivity {
    public static final String TAG = "SplashActivity";
    private static final String IMG_URI = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1496933431724&di=81c5c1a4aad63cd1c9ac8e7777c49e11&imgtype=0&src=http%3A%2F%2Fimage.tianjimedia.com%2FuploadImages%2F2012%2F326%2FXP07382U1K18.jpg";
    private static final String[] PERMISSIONS_ON_LAUNCH = new String[] {Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    public static final int REQUEST_CODE_PERMISSIONS_ON_LAUNCH = 1;
    public static final int REQUEST_CODE_ACCESS_COARSE_LOCATION = 2;
    private SimpleDraweeView mSplashSdv;
    private boolean mJump2Main = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_splash);
        mSplashSdv = (SimpleDraweeView) findViewById(R.id.sdv_splash);
//        mSplashSdv.setActualImageResource(R.drawable.launch_bg);
        Logger.d(TAG, "onCreate");
        if (mJump2Main) {
            finishDisplay();
        } else {
//            SystemClock.sleep(2000);
            boolean launchPermGranted = Permissions.permissionGranted(this, PERMISSIONS_ON_LAUNCH);
            if (!launchPermGranted) {
                Logger.d(TAG, "ActivityCompat.requestPermissions requestCode:" + REQUEST_CODE_PERMISSIONS_ON_LAUNCH);
                ActivityCompat.requestPermissions(this, PERMISSIONS_ON_LAUNCH, REQUEST_CODE_PERMISSIONS_ON_LAUNCH);
                return;
            }
            boolean locationPermGranted = Permissions.permissionGranted(this, Manifest.permission.ACCESS_COARSE_LOCATION);
            if (locationPermGranted) {
                handleDisplay();
                // get location
            } else {
                Logger.d(TAG, "ActivityCompat.requestPermissions requestCode:" + REQUEST_CODE_ACCESS_COARSE_LOCATION);
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE_ACCESS_COARSE_LOCATION);
            }
//            handleDisplay();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Logger.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Logger.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Logger.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Logger.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Logger.d(TAG, "onDestroy");
    }

    private void handleDisplay() {
        Logger.d(TAG, "handleDisplay");
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
//        SystemClock.sleep(2000);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Logger.d(TAG, "onRequestPermissionsResult requestCode:" + requestCode
                + ", permissions:" + Arrays.toString(permissions)
                + ", grantResults:" + Arrays.toString(grantResults));
        switch (requestCode) {
            case REQUEST_CODE_PERMISSIONS_ON_LAUNCH:
                boolean launchPermGranted = Permissions.permissionGranted(this, PERMISSIONS_ON_LAUNCH);
                if (!launchPermGranted) {
                    Permissions.showPermissionSettingDialog(this,
                            "权限申请",
                            "电话权限,存储权限为必选项，全部开通才可以正常使用App,请到设置中开启",
                            false,
                            (dialog, which) -> { finish(); },
                            (dialog, which) -> {
                                Permissions.startPermissionSetting(SplashActivity.this);
                                finish();
                            });
                } else {
                    boolean locationPermGranted = Permissions.permissionGranted(this, Manifest.permission.ACCESS_COARSE_LOCATION);
                    if (locationPermGranted) {
                        handleDisplay();
                        // get location
                    } else {
                        Logger.d(TAG, "ActivityCompat.requestPermissions requestCode:" + REQUEST_CODE_ACCESS_COARSE_LOCATION);
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE_ACCESS_COARSE_LOCATION);
                    }
                }
                break;
            case REQUEST_CODE_ACCESS_COARSE_LOCATION:
                boolean locationPermGranted = Permissions.permissionGranted(this, Manifest.permission.ACCESS_COARSE_LOCATION);
                if (locationPermGranted) {
                    // get location
                } else {
                    // show hint
                }
                handleDisplay();
                break;
        }


    }

    private void finishDisplay() {
        Logger.d(TAG, "finishDisplay");
        MyApplication.getUiHandler().postDelayed(() -> {
            startActivity(new Intent(this, MainActivity.class));
            finish();
            overridePendingTransition(0, R.anim.translate_from_center_to_left);
        }, TimeUnit.SECONDS.toMillis(2));
    }
}
