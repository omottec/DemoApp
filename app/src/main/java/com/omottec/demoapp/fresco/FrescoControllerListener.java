package com.omottec.demoapp.fresco;

import android.graphics.drawable.Animatable;

import com.facebook.drawee.controller.ControllerListener;
import com.facebook.imagepipeline.image.CloseableStaticBitmap;
import com.facebook.imagepipeline.image.ImageInfo;
import com.omottec.demoapp.Tag;
import com.omottec.demoapp.memory.MemoryUtils;
import com.omottec.demoapp.utils.Logger;

/**
 * Created by qinbingbing on 09/02/2018.
 */

public class FrescoControllerListener implements ControllerListener<ImageInfo> {
    @Override
    public void onSubmit(String id, Object callerContext) {
        Logger.d(Tag.FRESCO, "onSubmit id:" + id
                + ", callerContext:" + callerContext);
    }

    @Override
    public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
        Logger.d(Tag.FRESCO, "onFinalImageSet id:" + id
                + ", imageInfo:" + imageInfo
                + ", getSizeInBytes:" + ((CloseableStaticBitmap)imageInfo).getSizeInBytes()
                + ", imageInfo.getWidth:" + imageInfo.getWidth()
                + ", imageInfo.getHeight:" + imageInfo.getHeight()
                + ", animatable:" + animatable);
        Logger.d(Tag.FRESCO, "onFinalImageSet " + MemoryUtils.getMemoryInfo());
    }

    @Override
    public void onIntermediateImageSet(String id, ImageInfo imageInfo) {
        Logger.d(Tag.FRESCO, "onIntermediateImageSet id:" + id
                + ", imageInfo:" + imageInfo
                + ", getSizeInBytes:" + ((CloseableStaticBitmap)imageInfo).getSizeInBytes()
                + ", imageInfo.getWidth:" + imageInfo.getWidth()
                + ", imageInfo.getHeight:" + imageInfo.getHeight());
    }

    @Override
    public void onIntermediateImageFailed(String id, Throwable throwable) {
        Logger.d(Tag.FRESCO, "onIntermediateImageFailed id:" + id
                + ", throwable:" + throwable);
    }

    @Override
    public void onFailure(String id, Throwable throwable) {
        Logger.d(Tag.FRESCO, "onFailure id:" + id
                + ", throwable:" + throwable);
    }

    @Override
    public void onRelease(String id) {
        Logger.d(Tag.FRESCO, "onRelease id:" + id);
        Logger.d(Tag.FRESCO, "onRelease " + MemoryUtils.getMemoryInfo());
    }
}
