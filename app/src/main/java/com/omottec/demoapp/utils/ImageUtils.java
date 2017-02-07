package com.omottec.demoapp.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;

import com.omottec.demoapp.app.MyApplication;
import com.omottec.demoapp.stamp.Stamp;

import java.io.InputStream;

/**
 * Created by omottec on 07/02/2017.
 */

public class ImageUtils {
    public static Bitmap getThumbFromAsset(String fileName, int minSideLength, int maxNumOfPixels) {
        if (TextUtils.isEmpty(fileName)) {
            return null;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bitmap = null;
        InputStream temp = null;
        InputStream in = null;
        try {
            temp = MyApplication.getContext().getAssets().open(fileName);
            BitmapFactory.decodeStream(temp, null, options);
            options.inSampleSize = computeSampleSize(options, minSideLength,
                    maxNumOfPixels);
            options.inJustDecodeBounds = false;
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            options.inDither = false;
            options.inPurgeable = true;
            options.inInputShareable = true;
            in = MyApplication.getContext().getAssets().open(fileName);
            bitmap = BitmapFactory.decodeStream(in, null, options);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                temp.close();
                in.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return bitmap;
    }

    public static int computeSampleSize(BitmapFactory.Options options,
                                        int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength,
                maxNumOfPixels);
        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }
        return roundedSize;
    }

    private static int computeInitialSampleSize(BitmapFactory.Options options,
                                                int minSideLength, int maxNumOfPixels) {
        if ((maxNumOfPixels == -1) && (minSideLength == -1)) return 1;
        double w = options.outWidth;
        double h = options.outHeight;
        int lowerBound = (maxNumOfPixels == -1) ? 1 :
                (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 :
                (int) Math.min(Math.floor(w / minSideLength),
                        Math.floor(h / minSideLength));
        if (upperBound < lowerBound) {
            return lowerBound;
        }
        if (minSideLength == -1) {
            return lowerBound;
        } else if (maxNumOfPixels == -1) {
            return upperBound;
        } else {
            return upperBound;
        }
    }
}
