package com.omottec.demoapp.launch;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.image.CloseableImage;
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
//            mSplashIv.setImageResource(R.drawable.splash);
//            mSplashSdv.setImageURI(IMG_URI);
            ImagePipeline imagePipeline = Fresco.getImagePipeline();
            ImageRequest imageRequest = ImageRequest.fromUri(IMG_URI);
            DataSource<CloseableReference<CloseableImage>> dataSource = imagePipeline.fetchDecodedImage(imageRequest, this);
            try {
                CloseableReference<CloseableImage> imageReference = dataSource.getResult();
                if (imageReference != null) {
                    try {
                        CloseableImage closeableImage = imageReference.get();
                        Logger.d(Tag.SPLASH, closeableImage.toString());
                    } finally {
                        CloseableReference.closeSafely(imageReference);
                    }
                } else {
                    Logger.d(Tag.SPLASH, "prefetchToDiskCache");
                    imagePipeline.prefetchToDiskCache(imageRequest, this);
                }

            } finally {
                dataSource.close();
            }
            MyApplication.getUiHandler().postDelayed(() -> jump2MainActivity(), 2 * 1000);
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
        mSplashIv = (ImageView) mContentView.findViewById(R.id.iv_splash);
        mSplashSdv = (SimpleDraweeView) mContentView.findViewById(R.id.sdv_splash);
        return mContentView;
    }
}
