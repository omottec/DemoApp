package com.omottec.demoapp.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.omottec.demoapp.R;
import com.omottec.demoapp.Tag;
import com.omottec.demoapp.app.MyApplication;

/**
 * Created by qinbingbing on 8/24/16.
 */
public class ScaleTypeFragment extends Fragment {
    private View mRootView;
    private ImageView mIv;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.f_iv, null);
        mIv = (ImageView) mRootView.findViewById(R.id.iv);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /*BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.userguide_product_icon_fast, options);
        Log.d(Tag.SCALE_TYPE, "xhdpi " + options.outWidth + ":" + options.outHeight);*/
        Log.d(Tag.SCALE_TYPE, "view:" + mIv.getLayoutParams().width + "*" + mIv.getLayoutParams().height);
        Bitmap bitmap = ((BitmapDrawable) mIv.getDrawable()).getBitmap();
        Log.d(Tag.SCALE_TYPE, "bitmap:" + bitmap.getWidth() + "*" + bitmap.getHeight());
        MyApplication.getUiHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mIv.setDrawingCacheEnabled(true);
                Bitmap drawingCache = mIv.getDrawingCache();
                mIv.setDrawingCacheEnabled(false);
                Log.d(Tag.SCALE_TYPE, "drawingCache:" + drawingCache.getWidth() + "*" + drawingCache.getHeight());
            }
        }, 3000);
    }
}
