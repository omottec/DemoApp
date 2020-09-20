package com.omottec.demoapp.memory;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.omottec.demoapp.R;

public class BitmapFragment extends Fragment {
    public static final String TAG = "BitmapFragment";
    private TextView mTv;
    private ImageView mIv;
    private ImageView mIv1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f_bitmap, container, false);
        mTv = view.findViewById(R.id.tv);
        mIv = view.findViewById(R.id.iv);
        mIv1 = view.findViewById(R.id.iv1);
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        String viewInfo = "view width:" + mIv.getLayoutParams().width
            + ", height:" + mIv.getLayoutParams().height;

        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        String screenInfo = "screen density:" + displayMetrics.density
            + ", densityDpi:" + displayMetrics.densityDpi
            + ", width:" + displayMetrics.widthPixels
            + ", height" + displayMetrics.heightPixels;

        BitmapDrawable bitmapDrawable = (BitmapDrawable) mIv.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        String ivBitmapInfo = bitmapInfo(bitmap);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        Bitmap decodedBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.goods, options);
        String decodeBitmapInfo = bitmapInfo(decodedBitmap);
        mIv1.setImageBitmap(decodedBitmap);

        String info = new StringBuilder(viewInfo).append('\n')
            .append(screenInfo).append('\n')
            .append("ivBitmapInfo ").append(ivBitmapInfo).append('\n')
            .append("decodeBitmapInfo ").append(decodeBitmapInfo).append('\n')
            .toString();
        Log.i(TAG, info);
        mTv.setText(info);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private String bitmapInfo(Bitmap bitmap) {
        return new StringBuilder(bitmap.toString())
            .append("byteCount:").append(bitmap.getByteCount())
            .append(", allocationByteCount:").append(bitmap.getAllocationByteCount())
            .append(", getWidth:").append(bitmap.getWidth())
            .append(", getConfig.name:").append(bitmap.getConfig().name())
            .append(", getRowBytes:").append(bitmap.getRowBytes())
            .append(", getHeight:").append(bitmap.getHeight())
            .append(", getDensity:").append(bitmap.getDensity())
            .toString();
    }
}
