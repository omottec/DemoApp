package com.omottec.demoapp.fresco;

import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.cache.common.CacheKey;
import com.facebook.common.references.CloseableReference;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.cache.CountingMemoryCache;
import com.facebook.imagepipeline.cache.DefaultCacheKeyFactory;
import com.facebook.imagepipeline.cache.InstrumentedMemoryCache;
import com.facebook.imagepipeline.cache.MemoryCache;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.image.CloseableStaticBitmap;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.omottec.demoapp.R;
import com.omottec.demoapp.Tag;

import com.omottec.logger.Logger;
import java.lang.reflect.Field;

/**
 * Created by qinbingbing on 16/08/2017.
 */

public class FrescoFragment extends Fragment {
    public static final String TAG = "FrescoFragment";

    private SimpleDraweeView mSdv;
    private SimpleDraweeView mSdv1;
    private ImageRequest mImageRequest;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f_fresco, container, false);
        mSdv = (SimpleDraweeView) view.findViewById(R.id.sdv);
        mSdv1 = view.findViewById(R.id.sdv1);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mSdv.setOnClickListener(v -> {
            loadUrl(true);
        });
        mSdv1.setOnClickListener(v -> {
            loadUrl(false);
        });
    }

    private void loadUrl(boolean rawLoad) {
//        String uri = "http://p0.meituan.net/mallimages/dd50357c9fc0afa6740d3b57989214cd79343.jpg";
        String uri = "http://p1.meituan.net/shangchao/bda30e3fb3274baa9ba165c4d1bf35e5.jpg";
//        String uri = "http://d.hiphotos.baidu.com/image/pic/item/42a98226cffc1e17e11735424090f603738de91d.jpg";

        BaseControllerListener<ImageInfo> baseControllerListener = new BaseControllerListener<ImageInfo>() {
            @Override
            public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                Logger.d(Tag.FRESCO, "onFinalImageSet id:" + id
                        + ", imageInfo:" + imageInfo
                        + ", getSizeInBytes:" + ((CloseableStaticBitmap)imageInfo).getSizeInBytes()
                        + ", imageInfo.getWidth:" + imageInfo.getWidth()
                        + ", imageInfo.getHeight:" + imageInfo.getHeight());

                MemoryCache<CacheKey, CloseableImage> bitmapMemoryCache = Fresco.getImagePipeline().getBitmapMemoryCache();
                Logger.d(Tag.FRESCO, "MemoryCache:" + bitmapMemoryCache);
                if (bitmapMemoryCache instanceof InstrumentedMemoryCache) {
                    InstrumentedMemoryCache instrumentedMemoryCache = (InstrumentedMemoryCache) bitmapMemoryCache;
                    try {
                        Field mDelegate = instrumentedMemoryCache.getClass().getDeclaredField("mDelegate");
                        mDelegate.setAccessible(true);
                        CountingMemoryCache countingMemoryCache = (CountingMemoryCache) mDelegate.get(instrumentedMemoryCache);
                        Logger.d(Tag.FRESCO, "countingMemoryCache.getCount:" + countingMemoryCache.getCount());
                        Logger.d(Tag.FRESCO, "countingMemoryCache.getSizeInBytes:" + countingMemoryCache.getSizeInBytes());
                        Logger.d(Tag.FRESCO, "countingMemoryCache.getInUseCount:" + countingMemoryCache.getInUseCount());
                        Logger.d(Tag.FRESCO, "countingMemoryCache.getInUseSizeInBytes:" + countingMemoryCache.getInUseSizeInBytes());
                        Logger.d(Tag.FRESCO, "countingMemoryCache.getEvictionQueueCount:" + countingMemoryCache.getEvictionQueueCount());
                        Logger.d(Tag.FRESCO, "countingMemoryCache.getEvictionQueueSizeInBytes:" + countingMemoryCache.getEvictionQueueSizeInBytes());
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }

                if (mImageRequest == null) return;
                CacheKey bitmapCacheKey = DefaultCacheKeyFactory.getInstance().getBitmapCacheKey(mImageRequest, null);
                CloseableReference<CloseableImage> closeableReference = bitmapMemoryCache.get(bitmapCacheKey);
                int sizeInBytes = closeableReference.get().getSizeInBytes();
                Logger.d(Tag.FRESCO, "CloseableImage sizeInBytes: " + sizeInBytes);
            }

            @Override
            public void onFailure(String id, Throwable throwable) {
                Logger.d(Tag.FRESCO, "onFailure id:" + id);
            }
        };
        if (rawLoad)
            Frescos.rawLoad(mSdv, uri, baseControllerListener);
        else
            mImageRequest = Frescos.load(mSdv1, uri, baseControllerListener);
    }
}
