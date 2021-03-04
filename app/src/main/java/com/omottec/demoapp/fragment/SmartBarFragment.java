package com.omottec.demoapp.fragment;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.omottec.demoapp.R;
import com.omottec.demoapp.utils.UiUtils;

/**
 * Created by qinbingbing on 4/7/16.
 */
public class SmartBarFragment extends Fragment {
    public static final String TAG = "SmartBarFragment";
    private View mRootView;
    private TextView mTV;
    private Activity mActivity;
    private Handler mHandler;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
        mHandler = new Handler();
        /*float applyDimension = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
        Log.d(TAG, "applyDimension:" + applyDimension);
        int dip2px = UiUtils.dip2px(mActivity, 10);
        Log.d(TAG, "dip2px:" + dip2px);*/
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.full_screen_text, null);
        mTV = (TextView) mRootView.findViewById(R.id.tv);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "screenHeight:" + UiUtils.getScreenSize(mActivity, false));
        Log.d(TAG, "navigationHeight:" + UiUtils.getNavigationBarHeight(mActivity));
        Log.d(TAG, "statusBarHeight:" + UiUtils.getStatusBarHeight1(mActivity));
        Rect rect = new Rect();
        mActivity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        mActivity.getWindow().getDecorView().getMeasuredHeight();
        Log.d(TAG, "rect.left:" + rect.left
                + ", rect.top:" + rect.top
                + ", rect.right:" + rect.right
                + ", rect.bottom:" + rect.bottom);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "mRootFl.getMeasuredHeight():" + mRootView.getMeasuredHeight());
                Log.d(TAG, "getDecorView().getMeasuredHeight:" + mActivity.getWindow().getDecorView().getRootView().getMeasuredHeight());
            }
        }, 500);
        Log.d(TAG, "MANUFACTURER:" + Build.MANUFACTURER
                + ", BRAND:" + Build.BRAND
                + ", BOARD:" + Build.BOARD
                + ", MODEL:" + Build.MODEL
                + ", PRODUCT:" + Build.PRODUCT);
    }
}
