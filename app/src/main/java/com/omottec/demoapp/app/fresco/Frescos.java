package com.omottec.demoapp.app.fresco;

import android.net.Uri;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.DraweeView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.omottec.demoapp.Tag;
import com.omottec.demoapp.utils.Logger;
import com.omottec.demoapp.utils.UiUtils;

/**
 * Created by omottec on 29/11/2017.
 */

public final class Frescos {
    private Frescos() {}

    public static void load(DraweeView view, String url, int width, int height) {
        Logger.d(Tag.FRESCO, "origin url:" + url);
        if (width > 0 && height > 0)
            url = new StringBuilder(url).append('@')
                    .append(width).append('w').append('_')
                    .append(height).append('h').append('_')
                    .append("1l")
                    .toString();
        Logger.d(Tag.FRESCO, "venus url:" + url);
        ImageRequest imageRequest =
                ImageRequestBuilder.newBuilderWithSource(Uri.parse(url))
//                        .setResizeOptions(new ResizeOptions(width, height))
                        .build();
        DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                .setImageRequest(imageRequest)
                .setOldController(view.getController())
                .build();
        view.setController(draweeController);
    }

    public static void load(DraweeView view, String url) {
        load(view, url,
                view.getLayoutParams().width,
                view.getLayoutParams().height);
    }

}
