package com.omottec.demoapp.image;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.omottec.demoapp.R;
import com.omottec.demoapp.utils.UiUtils;
import com.omottec.logger.Logger;

/**
 * Created by qinbingbing on 16/05/2018.
 */

public class TestIconDrawableFragment extends Fragment {
    public static final String TAG = "TestIconDrawableFragment";
    private RelativeLayout mRl;
    private ImageView mIv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRl = (RelativeLayout) inflater.inflate(R.layout.f_test_icon_drawable, container, false);
        return mRl;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mIv = view.findViewById(R.id.iv);
        Logger.d(TAG, "DisplayMetrics:" + UiUtils.getDisplayMetrics(getActivity()));
        mIv.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Logger.d(TAG, "onGlobalLayout");
                Logger.d(TAG, "View:" + mIv.getWidth() + "*" + mIv.getHeight());
            }
        });
        BitmapDrawable bitmapDrawable = (BitmapDrawable) mIv.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        Logger.d(TAG, "Bitmap:" + bitmap.getWidth() + "*" + bitmap.getHeight()
                + ", getByteCount:" + bitmap.getByteCount());
    }
}
