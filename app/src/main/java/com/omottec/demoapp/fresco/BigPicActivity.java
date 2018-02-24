package com.omottec.demoapp.fresco;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.omottec.demoapp.R;
import com.omottec.demoapp.activity.BaseActivity;

import java.util.concurrent.TimeUnit;

/**
 * Created by qinbingbing on 24/02/2018.
 */

public class BigPicActivity extends BaseActivity {
    public static final String EXTRA_IMAGE_DATA = "extra_image_data";
    private SimpleDraweeView mSdv;
    private TextView mTv;
    private FrescoControllerListener mListener = new FrescoControllerListener();

    public static void show(Context context, BatchLoadAdapter.ImageData imageData) {
        Intent intent = new Intent(context, BigPicActivity.class);
        intent.putExtra(EXTRA_IMAGE_DATA, imageData);
        context.startActivity(intent);
    }

    @Override
    protected View createContentView() {
        return View.inflate(this, R.layout.a_big_pic, null);
    }

    @Override
    protected void onViewCreated(View view) {
        mSdv = mContentView.findViewById(R.id.sdv);
        mTv = mContentView.findViewById(R.id.tv);
        BatchLoadAdapter.ImageData imageData = getIntent().getParcelableExtra(EXTRA_IMAGE_DATA);
        Frescos.rawLoad(mSdv, imageData.url, mListener);
        mTv.setText(imageData.title);
        new LeakThread().start();
    }

    class LeakThread extends Thread {
        @Override
        public void run() {
            try {
                TimeUnit.HOURS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
