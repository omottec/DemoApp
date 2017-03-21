package com.omottec.demoapp.view.ptr;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.omottec.demoapp.app.MyApplication;
import com.omottec.demoapp.utils.UiUtils;

/**
 * Created by qinbingbing on 21/03/2017.
 */

public class Pull2RefreshLinearLayout extends LinearLayout {
    public static final String TAG = "Pull2Refresh";
    private static final int ITEM_HEIGHT = UiUtils.dip2px(MyApplication.getContext(), 70);
    public static final int FRICTION = 2;
    private TextView mHeaderTv;
    private TextView mBodyTv;
    private TextView mFooterTv;
    private float mLastX, mLastY;


    public Pull2RefreshLinearLayout(Context context) {
        super(context);
        init();
    }

    public Pull2RefreshLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Pull2RefreshLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // padding
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = event.getX();
                mLastY = event.getY();
                return true;
            case MotionEvent.ACTION_MOVE:
                float x = event.getX();
                float y = event.getY();
                if (Math.abs(mLastY - y) <= Math.abs(mLastX - x)) return false;
                if (mLastY > y) {
                    int delta = (int) Math.min((mLastY - y)/2, ITEM_HEIGHT);
                    int paddingBottom = Math.min(getPaddingBottom() + delta, 0);
                    setPadding(0, getPaddingTop(), 0, paddingBottom);
                } else {
                    int delta = (int) Math.min((y - mLastY)/2, ITEM_HEIGHT);
                    int paddingTop = Math.min(0, getPaddingTop() + delta);
                    setPadding(0, paddingTop, 0, getPaddingBottom());
                }
                mLastY = y;
                mLastX = x;
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mLastY = mLastX = 0;
                setPadding(0, -ITEM_HEIGHT, 0, -ITEM_HEIGHT);
                return false;
        }
        return super.onTouchEvent(event);


    }

    private void init() {
        mHeaderTv = new TextView(getContext());
        mHeaderTv.setGravity(Gravity.CENTER);
        mHeaderTv.setText("Header");
        mHeaderTv.setBackgroundResource(android.R.color.holo_red_light);
        addView(mHeaderTv, LayoutParams.MATCH_PARENT, ITEM_HEIGHT);

        mBodyTv = new TextView(getContext());
        mBodyTv.setGravity(Gravity.CENTER);
        mBodyTv.setText("Body");
        mBodyTv.setBackgroundResource(android.R.color.holo_green_light);
        LinearLayout.LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        lp.weight = 1;
        addView(mBodyTv, lp);

        mFooterTv = new TextView(getContext());
        mFooterTv.setGravity(Gravity.CENTER);
        mFooterTv.setText("Footer");
        mFooterTv.setBackgroundResource(android.R.color.holo_blue_light);
        addView(mFooterTv, LayoutParams.MATCH_PARENT, ITEM_HEIGHT);

        postDelayed(new Runnable() {
            @Override
            public void run() {
                // padding
                Log.d(TAG, getPadding());
                setPadding(0, -ITEM_HEIGHT, 0, -ITEM_HEIGHT);
                Log.d(TAG, getPadding());

                // scroll
                /*Log.d(TAG, getScroll());
//                scrollBy(0, ITEM_HEIGHT);
                scrollTo(0, -ITEM_HEIGHT);
                Log.d(TAG, getScroll());*/

                // margin
                /*Log.d(TAG, "mHeaderTv:" + getMargin(mHeaderTv));
                LinearLayout.LayoutParams lp = (LayoutParams) mHeaderTv.getLayoutParams();
                lp.topMargin = -ITEM_HEIGHT;
                mHeaderTv.requestLayout();
                Log.d(TAG, "mHeaderTv:" + getMargin(mHeaderTv));

                Log.d(TAG, "mFooterTv:" + getMargin(mFooterTv));
                lp = (LayoutParams) mFooterTv.getLayoutParams();
                lp.bottomMargin = -ITEM_HEIGHT;
                mFooterTv.requestLayout();
                Log.d(TAG, "mFooterTv:" + getMargin(mFooterTv));*/
            }
        }, 5 * 1000);
    }

    private String getPadding() {
        return "PaddingTop:" + getPaddingTop() + ", PaddingBottom:" + getPaddingBottom();
    }

    private String getScroll() {
        return "ScrollX:" + getScrollX();
    }

    private String getMargin(View v) {
        LinearLayout.LayoutParams lp = (LayoutParams) v.getLayoutParams();
        return "topMargin:" + lp.topMargin + ", bottomMargin:" + lp.bottomMargin;
    }
}
