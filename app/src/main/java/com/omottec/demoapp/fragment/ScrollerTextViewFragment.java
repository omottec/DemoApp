package com.omottec.demoapp.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.omottec.demoapp.R;
import com.omottec.demoapp.Tag;
import com.omottec.demoapp.view.ScrollerMoveImageView;
import com.omottec.demoapp.view.ScrollerMoveTextView;

/**
 * Created by qinbingbing on 8/25/16.
 */
public class ScrollerTextViewFragment extends Fragment {
    private View mRootView;
    private ScrollerMoveTextView mTv;
    private Handler mHandler = new Handler();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(Tag.MOVE_VIEW_BY_SCROLLER, "onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(Tag.MOVE_VIEW_BY_SCROLLER, "onCreateView");
        mRootView = inflater.inflate(R.layout.f_tv, null);
        mTv = (ScrollerMoveTextView) mRootView.findViewById(R.id.tv);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(Tag.MOVE_VIEW_BY_SCROLLER, "onViewCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(Tag.MOVE_VIEW_BY_SCROLLER, "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(Tag.MOVE_VIEW_BY_SCROLLER, "onResume");
        mTv.logParams();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mTv.logParams();
                mTv.smoothScrollTo(mTv.getScrollX() + mTv.getWidth() / 2, mTv.getScrollY());
            }
        }, 2000);
    }



}
