package com.omottec.demoapp.fragment;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.omottec.demoapp.R;

/**
 * Created by qinbingbing on 19/09/2017.
 */

public class LayoutParamFragment extends Fragment {
    public static final String TAG = "LayoutParamFragment";
    private View mRootView;
    private TextView mBodyTv;
    private Button mLeftBtn;
    private Button mRightBtn;
    private LinearLayout mBottomLl;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.f_layout_param, container, false);
        mBodyTv = (TextView) mRootView.findViewById(R.id.tv_body);
        mLeftBtn = (Button) mRootView.findViewById(R.id.btn_left);
        mRightBtn = (Button) mRootView.findViewById(R.id.btn_right);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mRootView.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            Log.d(TAG, "onGlobalLayout");
            logViewLocation(mBodyTv);
        });
        logViewLocation(mBodyTv);
        mBodyTv.setOnClickListener(v -> {
            mLeftBtn.setVisibility(View.GONE);
            mRightBtn.setVisibility(View.GONE);
        });
    }

    private void logViewLocation(View v) {
        String location = new StringBuilder().append("width:").append(v.getWidth())
                .append(", height:").append(v.getHeight())
                .append(", left:").append(v.getLeft())
                .append(", top:").append(v.getTop())
                .append(", right:").append(v.getRight())
                .append(", bottom:").append(v.getBottom())
                .toString();
        Log.d(TAG, location);
    }
}
