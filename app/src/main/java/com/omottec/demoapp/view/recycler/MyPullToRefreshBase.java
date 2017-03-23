package com.omottec.demoapp.view.recycler;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.handmark.pulltorefresh.library.PullToRefreshBase;

/**
 * Created by qinbingbing on 23/03/2017.
 */

public abstract class MyPullToRefreshBase<T extends View> extends PullToRefreshBase<T> {
    public MyPullToRefreshBase(Context context) {
        super(context);
    }

    public MyPullToRefreshBase(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyPullToRefreshBase(Context context, Mode mode) {
        super(context, mode);
    }

    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {
        return super.onInterceptHoverEvent(event);
    }


}
