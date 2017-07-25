package com.omottec.demoapp.view.recycler;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.omottec.demoapp.Tag;
import com.omottec.demoapp.app.MyApplication;
import com.omottec.demoapp.utils.TouchUtils;
import com.omottec.demoapp.utils.UiUtils;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by qinbingbing on 22/03/2017.
 */

public class PtrRecyclerView extends PullToRefreshBase<RecyclerView> {
    public static final String TAG = "PtrRecyclerView";

    public static final int PRE_FETCH_COUNT = 10;

    private OnRefreshListener2<RecyclerView> mOnRefreshListener2;

    private volatile boolean mLoadingData = false;

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

    public final void _setOnRefreshListener(OnRefreshListener2<RecyclerView> listener) {
        mOnRefreshListener2 = listener;
        setOnRefreshListener(listener);
    }

    @Override
    public Orientation getPullToRefreshScrollDirection() {
        return Orientation.VERTICAL;
    }

    @Override
    protected RecyclerView createRefreshableView(Context context, AttributeSet attrs) {
        RecyclerView recyclerView = new RecyclerView(context);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                Log.d(Tag.PTR_RECYCLER, "onScrollStateChanged " + TouchUtils.getRecyclerScrollState(newState));
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_DRAGGING:
                        break;
                    case RecyclerView.SCROLL_STATE_SETTLING:
                        break;
                    case RecyclerView.SCROLL_STATE_IDLE:
                        break;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                Log.d(Tag.PTR_RECYCLER, "onScrolled dy:" + dy + ", mLoadingData:" + mLoadingData);
                if (dy <= 0
                        || mOnRefreshListener2 == null
                        || !getMode().showFooterLoadingLayout()
                        || mLoadingData == true
                        || !isReadyForPullEnd())
                    return;
                getFooterLayout().refreshing();
                smoothScrollTo(getFooterSize(), null);
                mLoadingData = true;
                Log.d(Tag.PTR_RECYCLER, "onScrolled onPullUpToRefresh");
                mOnRefreshListener2.onPullUpToRefresh(PtrRecyclerView.this);
            }
        });
        return recyclerView;
    }

    @Override
    protected boolean isReadyForPullEnd() {
        RecyclerView.LayoutManager layoutManager = getRefreshableView().getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager llm = (LinearLayoutManager) layoutManager;
            int itemCount = llm.getItemCount();
            int lastCompletelyVisibleItemPosition = llm.findLastCompletelyVisibleItemPosition();
            Log.d(Tag.PTR_RECYCLER, "isReadyForPullEnd itemCount:" + itemCount
                    + ", lastCompletelyVisibleItemPosition:" + lastCompletelyVisibleItemPosition);
            if (mOnRefreshListener2 != null)
                return lastCompletelyVisibleItemPosition >= itemCount - PRE_FETCH_COUNT;
            else
                return lastCompletelyVisibleItemPosition == itemCount - 1;
        } else {
            int itemCount = getRefreshableView().getAdapter().getItemCount();
            View lastChild = getRefreshableView().getChildAt(getRefreshableView().getChildCount() - 1);
            int lastVisiblePosition = getRefreshableView().getChildAdapterPosition(lastChild);
            Log.d(Tag.PTR_RECYCLER, "isReadyForPullEnd itemCount:" + itemCount
                    + ", lastVisiblePosition:" + lastVisiblePosition);
            if (mOnRefreshListener2 != null)
                return lastVisiblePosition >= itemCount - PRE_FETCH_COUNT;
            else
                return lastVisiblePosition == itemCount - 1;
        }
    }

    @Override
    protected boolean isReadyForPullStart() {
        RecyclerView.LayoutManager layoutManager = getRefreshableView().getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager llm = (LinearLayoutManager) layoutManager;
            int firstCompletelyVisibleItemPosition = llm.findFirstCompletelyVisibleItemPosition();
            Log.d(Tag.PTR_RECYCLER, "isReadyForPullStart firstCompletelyVisibleItemPosition:" + firstCompletelyVisibleItemPosition);
            return firstCompletelyVisibleItemPosition == 0;
        } else {
            View firstChild = getRefreshableView().getChildAt(0);
            int firstVisiblePosition = getRefreshableView().getChildAdapterPosition(firstChild);
            Log.d(Tag.PTR_RECYCLER, "isReadyForPullStart firstVisiblePosition:" + firstVisiblePosition
                    + ", firstChild.getTop:" + firstChild.getTop());
            return firstVisiblePosition == 0 && firstChild.getTop() == 0;
        }
    }

    public void _onRefreshComplete() {
        State state = getState();
        Log.d(Tag.PTR_RECYCLER, "_onRefreshComplete state:" + state.name());
        if (state == State.REFRESHING || !mLoadingData) {
            super.onRefreshComplete();
        } else {
            mLoadingData = false;
            smoothScrollTo(0, null);
            getRefreshableView().scrollBy(0, UiUtils.dip2px(MyApplication.getContext(), 50));
        }
    }
}
