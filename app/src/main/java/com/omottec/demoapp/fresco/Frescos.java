package com.omottec.demoapp.fresco;

import android.net.Uri;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.DraweeView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.common.RotationOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.omottec.demoapp.Tag;
import com.omottec.demoapp.utils.Logger;

/**
 * Created by omottec on 29/11/2017.
 */

public final class Frescos {
    private Frescos() {}

    public static ImageRequest load(DraweeView view, String url, int width, int height, ControllerListener listener) {
        Logger.d(Tag.FRESCO, "origin url:" + url + ", width:" + width + ", height:" + height);
        if (width > 0 && height > 0)
            url = new StringBuilder(url).append('@')
                    .append(width).append('w').append('_')
                    .append(height).append('h').append('_')
                    .append("1l")
                    .append(".webp")
                    .toString();
        Logger.d(Tag.FRESCO, "venus url:" + url);
        ImageRequest imageRequest =
                ImageRequestBuilder.newBuilderWithSource(Uri.parse(url))
//                        .setResizeOptions(new ResizeOptions(width, height))
                        .setLocalThumbnailPreviewsEnabled(true)
                        .setRotationOptions(RotationOptions.autoRotateAtRenderTime())
                        .build();
        DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                .setImageRequest(imageRequest)
                .setOldController(view.getController())
                .setControllerListener(listener)
                .build();
        view.setController(draweeController);
        return imageRequest;
    }



    public static void load(DraweeView view, String url) {
        load(view, url,
                view.getLayoutParams().width,
                view.getLayoutParams().height,
                null);
    }

    public static ImageRequest load(DraweeView view, String url, ControllerListener listener) {
        return load(view,
                url,
                view.getLayoutParams().width,
                view.getLayoutParams().height,
                listener);
    }

    public static void rawLoad(SimpleDraweeView sdv, String uriString, ControllerListener listener) {
        Logger.d(Tag.FRESCO, "origin url:" + uriString);
        Uri uri = (uriString != null) ? Uri.parse(uriString) : null;

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setCallerContext(null)
                .setUri(uri)
                .setOldController(sdv.getController())
                .setControllerListener(listener)
                .build();
        sdv.setController(controller);
    }

    public static void load(DraweeView view, DraweeController controller) {
        view.setController(controller);
    }

    public static void load(DraweeView view, ImageRequest request) {
        DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(view.getController())
                .build();
        view.setController(draweeController);
    }

    public static void load(DraweeView view, Uri uri) {
        load(view, uri, view.getLayoutParams().width, view.getLayoutParams().height);
    }

    public static void load(DraweeView view, Uri uri, int width, int height) {
        ImageRequest imageRequest =
                ImageRequestBuilder.newBuilderWithSource(uri)
                        .setResizeOptions(new ResizeOptions(width, height))
                        .build();
        DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                .setImageRequest(imageRequest)
                .setOldController(view.getController())
                .build();
        view.setController(draweeController);
    }
}
