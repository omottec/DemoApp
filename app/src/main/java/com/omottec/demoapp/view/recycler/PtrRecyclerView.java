package com.omottec.demoapp.view.recycler;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.omottec.demoapp.utils.TouchUtils;

/**
 * Created by qinbingbing on 22/03/2017.
 */

public class PtrRecyclerView extends PullToRefreshBase<RecyclerView> {
    public static final String TAG = "PtrRecyclerView";

    private OnRefreshListener2<RecyclerView> mOnRefreshListener2;
    private OnRefreshListener<RecyclerView> mOnRefreshListener;

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

    public final void _setOnRefreshListener(OnRefreshListener<RecyclerView> listener) {
        mOnRefreshListener = listener;
        mOnRefreshListener2 = null;
        setOnRefreshListener(listener);
    }

    public final void _setOnRefreshListener(OnRefreshListener2<RecyclerView> listener) {
        mOnRefreshListener2 = listener;
        mOnRefreshListener = null;
        setOnRefreshListener(listener);
    }

    @Override
    public Orientation getPullToRefreshScrollDirection() {
        return Orientation.VERTICAL;
    }

    @Override
    protected RecyclerView createRefreshableView(Context context, AttributeSet attrs) {
        RecyclerView recyclerView = new RecyclerView(context);
        /*recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                Log.d(TAG, "onScrollStateChanged " + TouchUtils.getRecyclerScrollState(newState)
                        + ", isReadyForPullEnd:" + isReadyForPullEnd());
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_DRAGGING:
                        break;
                    case RecyclerView.SCROLL_STATE_SETTLING:
                        boolean readyForPullEnd = isReadyForPullEnd();
                        if (readyForPullEnd && mOnRefreshListener2 != null)
                            mOnRefreshListener2.onPullUpToRefresh(PtrRecyclerView.this);
                        break;
                    case RecyclerView.SCROLL_STATE_IDLE:

                        break;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                Log.d(TAG, "onScrolled dy:" + dy);
                if (dy > 0 && (getMode() == Mode.BOTH)) {
                    boolean readyForPullEnd = isReadyForPullEnd();
                    State state = getState();
                    Log.d(TAG, "onScrolled isReadyForPullEnd:" + readyForPullEnd
                            + ", state:" + state.name());
                    if (readyForPullEnd
                            && mOnRefreshListener2 != null
                            && state == State.RESET)
                        mOnRefreshListener2.onPullUpToRefresh(PtrRecyclerView.this);
                }
            }
        });*/
        return recyclerView;
    }

    @Override
    protected boolean isReadyForPullEnd() {
        RecyclerView.LayoutManager layoutManager = getRefreshableView().getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager llm = (LinearLayoutManager) layoutManager;
//            int childCount = llm.getChildCount();
            int itemCount = llm.getItemCount();
            int lastVisibleItemPosition = llm.findLastVisibleItemPosition();
            Log.d(TAG, "isReadyForPullEnd childCount:" + 0
                    + ", itemCount:" + itemCount
                    + ", lastVisibleItemPosition:" + lastVisibleItemPosition);
            return lastVisibleItemPosition >= itemCount - 10;
        } else {
            int itemCount = getRefreshableView().getAdapter().getItemCount();
            View lastChild = getRefreshableView().getChildAt(getRefreshableView().getChildCount() - 1);
            int lastVisiblePosition = getRefreshableView().getChildAdapterPosition(lastChild);
            Log.d(TAG, "isReadyForPullEnd itemCount:" + itemCount
                    + ", lastVisiblePosition:" + lastVisiblePosition
                    + ", lastChild.getBottom:" + lastChild.getBottom()
                    + ", getBottom:" + getBottom()
                    + ", getHeight:" + getHeight());
            return lastVisiblePosition >= itemCount-10
                    /*&& lastChild != null
                    && lastChild.getBottom() <= getHeight()*/;
        }
    }

    @Override
    protected boolean isReadyForPullStart() {
        RecyclerView.LayoutManager layoutManager = getRefreshableView().getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager llm = (LinearLayoutManager) layoutManager;
//            int childCount = llm.getChildCount();
//            int itemCount = llm.getItemCount();
            int firstCompletelyVisibleItemPosition = llm.findFirstCompletelyVisibleItemPosition();
            Log.d(TAG, "firstCompletelyVisibleItemPosition:" + firstCompletelyVisibleItemPosition);
            return firstCompletelyVisibleItemPosition == 0;
        } else {
            int itemCount = getRefreshableView().getAdapter().getItemCount();
            View firstChild = getRefreshableView().getChildAt(0);
            int firstVisiblePosition = getRefreshableView().getChildAdapterPosition(firstChild);
            Log.d(TAG, "isReadyForPullStart itemCount:" + itemCount
                    + ", firstVisiblePosition:" + firstVisiblePosition
                    + ", firstChild.getTop:" + firstChild.getTop()
                    + ", getTop:" + getTop());
            return firstVisiblePosition == 0 && firstChild.getTop() == 0;
        }
//        return false;
    }
}
