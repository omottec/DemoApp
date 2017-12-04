package com.omottec.demoapp.fragment;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.omottec.demoapp.R;
import com.omottec.demoapp.app.MyApplication;
import com.omottec.demoapp.app.fresco.Frescos;
import com.omottec.demoapp.utils.Logger;

/**
 * Created by qinbingbing on 16/08/2017.
 */

public class FrescoFragment extends Fragment {
    public static final String TAG = "FrescoFragment";

    private SimpleDraweeView mSdv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f_fresco, container, false);
        mSdv = (SimpleDraweeView) view.findViewById(R.id.sdv);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        String uri = "http://p0.meituan.net/mallimages/dd50357c9fc0afa6740d3b57989214cd79343.jpg";
        String uri = "http://p1.meituan.net/shangchao/bda30e3fb3274baa9ba165c4d1bf35e5.jpg";
//        String uri = "http://d.hiphotos.baidu.com/image/pic/item/42a98226cffc1e17e11735424090f603738de91d.jpg";
        mSdv.setImageURI(uri);
//        Frescos.load(mSdv, uri);
        MyApplication.getUiHandler().postDelayed(() -> {
            mSdv.setDrawingCacheEnabled(true);
            Bitmap drawingCache = mSdv.getDrawingCache();
            mSdv.setDrawingCacheEnabled(false);
            Logger.d(TAG, "getByteCount:" + drawingCache.getByteCount()
                    + ", getRowBytes:" + drawingCache.getRowBytes()
                    + ", getHeight:" + drawingCache.getHeight()
                    + ", getWidth:" + drawingCache.getWidth());
        }, 3000);
    }
}
