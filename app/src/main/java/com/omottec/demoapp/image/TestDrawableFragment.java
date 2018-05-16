package com.omottec.demoapp.image;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.omottec.demoapp.R;
import com.omottec.demoapp.utils.Logger;
import com.omottec.demoapp.utils.UiUtils;

/**
 * Created by qinbingbing on 16/05/2018.
 */

public class TestDrawableFragment extends Fragment {
    public static final String TAG = "TestDrawableFragment";
    private ImageView mIv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mIv = (ImageView) inflater.inflate(R.layout.f_test_drawable, container, false);
        return mIv;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Logger.d(TAG, "DisplayMetrics:" + UiUtils.getDisplayMetrics(getActivity()));
        BitmapDrawable bitmapDrawable = (BitmapDrawable) mIv.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        Logger.d(TAG, "Bitmap:" + bitmap.getWidth() + "*" + bitmap.getHeight());
    }
}
