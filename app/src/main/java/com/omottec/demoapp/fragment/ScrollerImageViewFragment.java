package com.omottec.demoapp.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.omottec.demoapp.R;
import com.omottec.demoapp.Tag;
import com.omottec.demoapp.view.ScrollerMoveImageView;
import com.omottec.demoapp.view.ScrollerMoveTextView;

/**
 * Created by qinbingbing on 8/25/16.
 */
public class ScrollerImageViewFragment extends Fragment {
    private View mRootView;
    private ScrollerMoveImageView mIv;
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
        mRootView = inflater.inflate(R.layout.f_iv, null);
        mIv = (ScrollerMoveImageView) mRootView.findViewById(R.id.iv);
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
        mIv.logParams();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mIv.logParams();
                mIv.smoothScrollTo(mIv.getScrollX() + mIv.getWidth() / 2, mIv.getScrollY());
            }
        }, 2000);
    }



}
