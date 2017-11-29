package com.omottec.demoapp.app.fresco;

import android.net.Uri;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.DraweeView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

/**
 * Created by omottec on 29/11/2017.
 */

public final class Frescos {
    private Frescos() {}

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

    public static void load(DraweeView view, String uri, int width, int height) {
        load(view, Uri.parse(uri), width, height);
    }

    public static void load(DraweeView view, String uri) {
        load(view, Uri.parse(uri), view.getLayoutParams().width, view.getLayoutParams().height);
    }

    public static void load(DraweeView view, Uri uri) {
        load(view, uri, view.getLayoutParams().width, view.getLayoutParams().height);
    }

}
