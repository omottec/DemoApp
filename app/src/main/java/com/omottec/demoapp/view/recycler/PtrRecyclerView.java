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

    private OnRefreshListener2<RecyclerView> mOnRefreshListener2;
    private OnRefreshListener<RecyclerView> mOnRefreshListener;

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
                        || !getMode().showFooterLoadingLayout()
                        || mOnRefreshListener2 == null
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
//            int childCount = llm.getChildCount();
            int itemCount = llm.getItemCount();
            int lastVisibleItemPosition = llm.findLastVisibleItemPosition();
            Log.d(Tag.PTR_RECYCLER, "isReadyForPullEnd itemCount:" + itemCount
                    + ", lastVisibleItemPosition:" + lastVisibleItemPosition);
            return lastVisibleItemPosition >= itemCount - 5;
        } else {
            int itemCount = getRefreshableView().getAdapter().getItemCount();
            View lastChild = getRefreshableView().getChildAt(getRefreshableView().getChildCount() - 1);
            int lastVisiblePosition = getRefreshableView().getChildAdapterPosition(lastChild);
            Log.d(Tag.PTR_RECYCLER, "isReadyForPullEnd itemCount:" + itemCount
                    + ", lastVisiblePosition:" + lastVisiblePosition
                    + ", lastChild.getBottom:" + lastChild.getBottom()
                    + ", getBottom:" + getBottom()
                    + ", getHeight:" + getHeight());
            return lastVisiblePosition >= itemCount-5
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
            Log.d(Tag.PTR_RECYCLER, "isReadyForPullStart firstCompletelyVisibleItemPosition:" + firstCompletelyVisibleItemPosition);
            return firstCompletelyVisibleItemPosition == 0;
        } else {
            int itemCount = getRefreshableView().getAdapter().getItemCount();
            View firstChild = getRefreshableView().getChildAt(0);
            int firstVisiblePosition = getRefreshableView().getChildAdapterPosition(firstChild);
            Log.d(Tag.PTR_RECYCLER, "isReadyForPullStart isReadyForPullStart itemCount:" + itemCount
                    + ", firstVisiblePosition:" + firstVisiblePosition
                    + ", firstChild.getTop:" + firstChild.getTop()
                    + ", getTop:" + getTop());
            return firstVisiblePosition == 0 && firstChild.getTop() == 0;
        }
    }

    public void _onRefreshComplete() {
        Log.d(Tag.PTR_RECYCLER, "_onRefreshComplete");
        mLoadingData = false;
        if (getMode().showFooterLoadingLayout()) {
            getFooterLayout().reset();
            smoothScrollTo(0, null);
        }
        getRefreshableView().scrollBy(0, UiUtils.dip2px(MyApplication.getContext(), 50));
        super.onRefreshComplete();
    }
}
