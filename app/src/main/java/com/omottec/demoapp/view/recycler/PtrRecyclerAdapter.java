package com.omottec.demoapp.view.recycler;

import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.omottec.demoapp.Tag;
import com.omottec.demoapp.app.MyApplication;

/**
 * Created by qinbingbing on 23/03/2017.
 */

public class PtrRecyclerAdapter<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter {
    public static final int VIEW_TYPE_HEADER = Integer.MIN_VALUE;
    public static final int VIEW_TYPE_FOOTER = Integer.MIN_VALUE + 1;
    private int mHeaderViewId = -1;
    private int mFooterViewId = -1;
    private boolean mShowHeader = false;
    private boolean mShowFooter = false;
    private RecyclerView.Adapter<T> mAdapter;
    private PtrRecyclerView mPtrRecyclerView;
    private RecyclerView.AdapterDataObserver mDataObserver;

    public PtrRecyclerAdapter(PtrRecyclerView ptrRecyclerView, RecyclerView.Adapter<T> adapter, boolean includeHeader, final boolean includeFooter) {
        mPtrRecyclerView = ptrRecyclerView;
        mAdapter = adapter;
        initDataObserver();
        if (includeHeader) mHeaderViewId = android.R.layout.simple_list_item_1;
        if (includeFooter) mFooterViewId = android.R.layout.simple_list_item_1;
    }

    public PtrRecyclerAdapter(PtrRecyclerView ptrRecyclerView, RecyclerView.Adapter<T> adapter, int headerViewId, int footerViewId) {
        mPtrRecyclerView = ptrRecyclerView;
        mAdapter = adapter;
        initDataObserver();
        mHeaderViewId = headerViewId;
        mFooterViewId = footerViewId;
    }

    private void initDataObserver() {
        mDataObserver = new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                PtrRecyclerAdapter.this.notifyDataSetChanged();
                /*MyApplication.getUiHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        PtrRecyclerAdapter.this.notifyDataSetChanged();
                    }
                });*/
            }

            @Override
            public void onItemRangeChanged(int positionStart, int itemCount) {
                PtrRecyclerAdapter.this.notifyItemRangeChanged(
                        showHeader() ? positionStart+1 : positionStart,
                        itemCount);
                /*MyApplication.getUiHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        PtrRecyclerAdapter.this.notifyItemRangeChanged(
                                showHeader() ? positionStart+1 : positionStart,
                                itemCount);
                    }
                });*/

            }

            @Override
            public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
                PtrRecyclerAdapter.this.notifyItemRangeChanged(
                        showHeader() ? positionStart+1 : positionStart,
                        itemCount,
                        payload);
                /*MyApplication.getUiHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        PtrRecyclerAdapter.this.notifyItemRangeChanged(
                                showHeader() ? positionStart+1 : positionStart,
                                itemCount,
                                payload);
                    }
                });*/
            }

            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                PtrRecyclerAdapter.this.notifyItemRangeInserted(
                        showHeader() ? positionStart+1 : positionStart,
                        itemCount);
                /*MyApplication.getUiHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        PtrRecyclerAdapter.this.notifyItemRangeInserted(
                                showHeader() ? positionStart+1 : positionStart,
                                itemCount);
                    }
                });*/
            }

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                PtrRecyclerAdapter.this.notifyItemRangeRemoved(
                        showHeader() ? positionStart+1 : positionStart,
                        itemCount);
                /*MyApplication.getUiHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        PtrRecyclerAdapter.this.notifyItemRangeRemoved(
                                showHeader() ? positionStart+1 : positionStart,
                                itemCount);
                    }
                });*/
            }

            @Override
            public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
                if (itemCount == 1) {
                    PtrRecyclerAdapter.this.notifyItemMoved(
                            showHeader() ? fromPosition+1 : fromPosition,
                            showHeader() ? toPosition+1 : toPosition);
                    /*MyApplication.getUiHandler().post(new Runnable() {
                        @Override
                        public void run() {
                            PtrRecyclerAdapter.this.notifyItemMoved(
                                    showHeader() ? fromPosition+1 : fromPosition,
                                    showHeader() ? toPosition+1 : toPosition);
                        }
                    });*/
                }
            }
        };
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mAdapter.registerAdapterDataObserver(mDataObserver);
        RecyclerView.LayoutManager lm = recyclerView.getLayoutManager();
        if (lm instanceof GridLayoutManager) {
            final GridLayoutManager glm = (GridLayoutManager) lm;
            final int spanCount = glm.getSpanCount();
            final GridLayoutManager.SpanSizeLookup spanSizeLookup = glm.getSpanSizeLookup();
            glm.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (showHeader() && position == 0) return spanCount;
                    if (showFooter() && position == getItemCount()-1) return spanCount;
                    return spanSizeLookup.getSpanSize(showHeader() ? position-1 : position);

//                    return position % 2 == 0 ? 1 : 2;

                    /*switch (getItemViewType(position)) {
                        case VIEW_TYPE_HEADER:
                            return spanCount;
                        case VIEW_TYPE_FOOTER:
                            return spanCount;
                        default:
                            return spanSizeLookup.getSpanSize(showHeader() ? position-1 : position);
                    }*/
                }
            });
            /*GridLayoutManager newGlm = new GridLayoutManager(recyclerView.getContext(), spanCount);
            newGlm.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (showHeader() && position == 0) return spanCount;
                    if (showFooter() && position == getItemCount()-1) return spanCount;
                    return spanSizeLookup.getSpanSize(showHeader() ? position-1 : position);
                }
            });
            recyclerView.setLayoutManager(newGlm);*/
        }
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        mAdapter.unregisterAdapterDataObserver(mDataObserver);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_HEADER) {
            return new HeaderFooterHolder(LayoutInflater.from(MyApplication.getContext()).inflate(mHeaderViewId, parent, false));
//            return new RecyclerView.ViewHolder(null) {};
        } else if (viewType == VIEW_TYPE_FOOTER) {
            return new HeaderFooterHolder(LayoutInflater.from(MyApplication.getContext()).inflate(mFooterViewId, parent, false));
        } else {
            return mAdapter.onCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == VIEW_TYPE_HEADER) {
            onBindHeaderViewHolder(holder);
        } else if (getItemViewType(position) == VIEW_TYPE_FOOTER) {
            onBindFooterViewHolder(holder);
        } else {
            mAdapter.onBindViewHolder((T)holder, showHeader() ? position-1 : position);
        }
    }

    protected void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        TextView tv = (TextView) holder.itemView;
        tv.setText("Header");
        tv.setTextColor(Color.BLACK);
        tv.setBackgroundColor(Color.RED);
        tv.setGravity(Gravity.CENTER);
    }

    protected void onBindFooterViewHolder(RecyclerView.ViewHolder holder) {
        TextView tv = (TextView) holder.itemView;
        tv.setText("Footer");
        tv.setTextColor(Color.BLACK);
        tv.setBackgroundColor(Color.BLUE);
        tv.setGravity(Gravity.CENTER);
        holder.itemView.setVisibility(showFooter() ? View.VISIBLE : View.VISIBLE);
    }

    @Override
    public int getItemCount() {
//        Log.d(Tag.PTR_RECYCLER, "PtrRecyclerAdapter.getItemCount");
        int itemCount = mAdapter.getItemCount();
        if (showHeader()) {
            itemCount++;
        }
        if (showFooter()) {
            itemCount++;
        }
        return itemCount;
    }

    @Override
    public int getItemViewType(int position) {
        if (showHeader() && position == 0) return VIEW_TYPE_HEADER;
        if (showFooter() && position == getItemCount()-1) return VIEW_TYPE_FOOTER;
        return mAdapter.getItemViewType(showHeader() ? position-1 : position);
    }

    static class HeaderFooterHolder extends RecyclerView.ViewHolder {
        public HeaderFooterHolder(View itemView) {
            super(itemView);
        }
    }

    public void setShowHeader(boolean showHeader) {
        if (mHeaderViewId == -1 || mShowHeader == showHeader) return;
        mShowHeader = showHeader;
        if (showHeader)
            notifyItemInserted(0);
        else
            notifyItemRemoved(0);
    }

    public void setShowFooter(boolean showFooter) {
        if (mFooterViewId == -1 || mShowFooter == showFooter) return;
        mShowFooter = showFooter;
        if (showFooter) {
            if (mPtrRecyclerView.getMode() == PullToRefreshBase.Mode.BOTH)
                mPtrRecyclerView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
            else
                mPtrRecyclerView.setMode(PullToRefreshBase.Mode.DISABLED);
//            notifyDataSetChanged();
            notifyItemInserted(getItemCount()-1);
//            notifyItemChanged(getItemCount()-1, null);
        } else {
            mPtrRecyclerView.setMode(PullToRefreshBase.Mode.BOTH);
            notifyItemRemoved(getItemCount()-1);
//            notifyItemChanged(getItemCount()-1, null);
        }
    }

    public boolean showHeader() {
        return mHeaderViewId != -1 && mShowHeader;
    }

    public boolean showFooter() {
        return mFooterViewId != -1 && mShowFooter;
    }
}
