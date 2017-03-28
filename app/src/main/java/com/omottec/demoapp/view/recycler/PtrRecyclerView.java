package com.omottec.demoapp.view.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.handmark.pulltorefresh.library.PullToRefreshBase;

/**
 * Created by qinbingbing on 22/03/2017.
 */

public class PtrRecyclerView extends PullToRefreshBase<RecyclerView> {
    public static final String TAG = "PtrRecyclerView";

    public PtrRecyclerView(Context context) {
        super(context);
    }

    public PtrRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PtrRecyclerView(Context context, Mode mode) {
        super(context, mode);
    }

    public PtrRecyclerView(Context context, Mode mode, AnimationStyle animStyle) {
        super(context, mode, animStyle);
    }

    @Override
    public Orientation getPullToRefreshScrollDirection() {
        return Orientation.VERTICAL;
    }

    @Override
    protected RecyclerView createRefreshableView(Context context, AttributeSet attrs) {
        return new RecyclerView(context);
    }

    @Override
    protected boolean isReadyForPullEnd() {
        int itemCount = getRefreshableView().getAdapter().getItemCount();
        View lastChild = getRefreshableView().getChildAt(getRefreshableView().getChildCount() - 1);
        int lastVisiblePosition = getRefreshableView().getChildAdapterPosition(lastChild);
        Log.d(TAG, "isReadyForPullEnd itemCount:" + itemCount
                + ", lastVisiblePosition:" + lastVisiblePosition
                + ", lastChild.getBottom:" + lastChild.getBottom()
                + ", getBottom:" + getBottom());
        return lastVisiblePosition == itemCount-1;
    }

    @Override
    protected boolean isReadyForPullStart() {
        int itemCount = getRefreshableView().getAdapter().getItemCount();
        View firstChild = getRefreshableView().getChildAt(0);
        int firstVisiblePosition = getRefreshableView().getChildAdapterPosition(firstChild);
        Log.d(TAG, "isReadyForPullStart itemCount:" + itemCount
                + ", firstVisiblePosition:" + firstVisiblePosition
                + ", firstChild.getTop:" + firstChild.getTop()
                + ", getTop:" + getTop());
        return firstVisiblePosition == 0 && firstChild.getTop() == 0;
    }
}
