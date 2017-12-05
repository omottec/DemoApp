package com.omottec.demoapp.fragment;

import android.graphics.Bitmap;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.cache.common.CacheKey;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.cache.MemoryCache;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.image.CloseableStaticBitmap;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.omottec.demoapp.R;
import com.omottec.demoapp.Tag;
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
//        mSdv.setImageURI(uri);

        BaseControllerListener<ImageInfo> baseControllerListener = new BaseControllerListener<ImageInfo>() {
            @Override
            public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                mSdv.setDrawingCacheEnabled(true);
                Bitmap drawingCache = mSdv.getDrawingCache();
                mSdv.setDrawingCacheEnabled(false);
                Logger.d(Tag.FRESCO, "getByteCount:" + drawingCache.getByteCount()
                        + ", getRowBytes:" + drawingCache.getRowBytes()
                        + ", getHeight:" + drawingCache.getHeight()
                        + ", getWidth:" + drawingCache.getWidth());

                Logger.d(Tag.FRESCO, "onFinalImageSet id:" + id
                        + ", imageInfo:" + imageInfo
                        + ", getSizeInBytes:" + ((CloseableStaticBitmap)imageInfo).getSizeInBytes()
                        + ", imageInfo.getHeight:" + imageInfo.getHeight()
                        + ", imageInfo.getHeight:" + imageInfo.getHeight());
                MemoryCache<CacheKey, CloseableImage> bitmapMemoryCache = Fresco.getImagePipeline().getBitmapMemoryCache();
                Logger.d(Tag.FRESCO, "MemoryCache:" + bitmapMemoryCache);
            }

            @Override
            public void onFailure(String id, Throwable throwable) {
                Logger.d(Tag.FRESCO, "onFailure id:" + id);
            }
        };
        Frescos.load(mSdv, uri, baseControllerListener);
    }
}
