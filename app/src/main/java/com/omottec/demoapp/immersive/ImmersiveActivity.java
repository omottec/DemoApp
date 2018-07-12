package com.omottec.demoapp.immersive;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.omottec.demoapp.R;
import com.omottec.demoapp.Tag;
import com.omottec.demoapp.utils.Logger;
import com.omottec.demoapp.utils.UiUtils;

/**
 * Created by qinbingbing on 12/07/2018.
 */

public class ImmersiveActivity extends AppCompatActivity {
    public static final String TAG = "ImmersiveActivity";
    private TextView mHeaderTv;
    private TextView mBodyTv;
    private boolean mLightStatusBar = false;

    public static void show(Context context) {
        Intent intent = new Intent(context, ImmersiveActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Logger.d(Tag.IMMERSIVE, "onCreate " + this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_immersive);
        int sdkInt = Build.VERSION.SDK_INT;
        String release = Build.VERSION.RELEASE;
        String brand = Build.BRAND;
        int statusBarHeight = UiUtils.getStatusBarHeight(this);
        Logger.d(Tag.IMMERSIVE, "sdkInt:" + sdkInt
                + ", release:" + release
                + ", brand:" + brand
                + ", statusBarHeight:" + statusBarHeight);
        if (sdkInt >= Build.VERSION_CODES.M) {
            Window window = getWindow();
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);

//            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

//            View decorView = activity.getWindow().getDecorView();
//            int sysUiFlag = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
//            decorView.setSystemUiVisibility(sysUiFlag);
//            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);

            setSystemUiVisibility();

            ViewGroup contentView = window.findViewById(Window.ID_ANDROID_CONTENT);
            View childView = contentView.getChildAt(0);
            Logger.d(Tag.IMMERSIVE, "contentView:" + contentView
                    + ", childView:" + childView);
            if (childView != null) {
                childView.setFitsSystemWindows(true);
            } else {
                contentView.setFitsSystemWindows(true);
            }
        }

        mHeaderTv = (TextView) findViewById(R.id.tv_header);
        mHeaderTv.setBackgroundColor(Color.RED);
        mBodyTv = (TextView) findViewById(R.id.tv_body);
        mBodyTv.setText("mLightStatusBar:" + mLightStatusBar);
        mBodyTv.setOnClickListener(v -> {
            mLightStatusBar = !mLightStatusBar;
            mHeaderTv.setBackgroundColor(mLightStatusBar ? Color.WHITE : Color.RED);
            mBodyTv.setText("mLightStatusBar:" + mLightStatusBar);
            setSystemUiVisibility();
        });
    }

    private void setSystemUiVisibility() {
        Logger.d(Tag.IMMERSIVE, "setSystemUiVisibility mLightStatusBar:" + mLightStatusBar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(mLightStatusBar ? Color.WHITE : Color.RED);

            View decorView = getWindow().getDecorView();
            int systemUiVisibility = decorView.getSystemUiVisibility();
            if (mLightStatusBar) {
                systemUiVisibility |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            } else {
                systemUiVisibility &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            }
            decorView.setSystemUiVisibility(systemUiVisibility);
        }
    }
}
