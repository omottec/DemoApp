package com.omottec.demoapp.view.log;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.omottec.demoapp.R;
import com.omottec.demoapp.utils.UiUtils;
import com.omottec.demoapp.utils.Views;

/**
 * Created by qinbingbing on 05/11/2018.
 */

public class LogFragment extends Fragment implements View.OnClickListener {
    public static final String TAG = "LogFragment";
    private LogTextView mLogTv;
    private TextView mVisibilityTv;
    private TextView mInvalidateTv;
    private TextView mRequestLayoutTv;
    private boolean mLastInvalidate;
    private boolean mLastBigSize = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        View view = inflater.inflate(R.layout.f_log, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onViewCreated");
        super.onViewCreated(view, savedInstanceState);
        mLogTv = view.findViewById(R.id.tv_log);
        mVisibilityTv = view.findViewById(R.id.tv_visibility);
        mInvalidateTv = view.findViewById(R.id.tv_invalidate);
        mRequestLayoutTv = view.findViewById(R.id.tv_requestLayout);

        mVisibilityTv.setOnClickListener(this);
        mInvalidateTv.setOnClickListener(this);
        mRequestLayoutTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_visibility:
                onClickVisibility();
                break;
            case R.id.tv_invalidate:
                onClickInvalidate();
                break;
            case R.id.tv_requestLayout:
                onClickRequestLayout();
                break;
            default:
                break;
        }
    }

    private void onClickRequestLayout() {
        Log.d(TAG, "onClickRequestLayout when mLastBigSize:" + mLastBigSize);
        if (mLastBigSize) {
            mLastBigSize = false;
            int size = UiUtils.dip2px(getContext(), 180);
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mLogTv.getLayoutParams();
            lp.width = size;
            lp.height = size;
            mLogTv.requestLayout();
        } else {
            mLastBigSize = true;
            int size = UiUtils.dip2px(getContext(), 200);
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mLogTv.getLayoutParams();
            lp.width = size;
            lp.height = size;
            mLogTv.requestLayout();
        }
    }

    private void onClickInvalidate() {
        Log.d(TAG, "onClickInvalidate when mLastInvalidate:" + mLastInvalidate);
        if (mLastInvalidate) {
            mLastInvalidate =false;
            mLogTv.postInvalidate();
        } else {
            mLastInvalidate = true;
            mLogTv.invalidate();
        }
    }

    private void onClickVisibility() {
        Log.d(TAG, "onClickVisibility when " + Views.getVisibility(mLogTv));
        if (mLogTv.getVisibility() == View.VISIBLE) {
            mLogTv.setVisibility(View.INVISIBLE);
        } else if (mLogTv.getVisibility() == View.INVISIBLE) {
            mLogTv.setVisibility(View.GONE);
        } else if (mLogTv.getVisibility() == View.GONE) {
            mLogTv.setVisibility(View.VISIBLE);
        }
    }
}
