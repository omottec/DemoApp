package com.omottec.demoapp.view.recycler;

import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.omottec.demoapp.R;
import com.omottec.demoapp.app.MyApplication;

/**
 * Created by qinbingbing on 23/03/2017.
 */

public class PtrRecyclerViewAdapter<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter {
    public static final int VIEW_TYPE_HEADER = Integer.MIN_VALUE;
    public static final int VIEW_TYPE_FOOTER = Integer.MIN_VALUE + 1;
    private int mHeaderViewId = -1;
    private int mFooterViewId = -1;
    private boolean mShowHeader = false;
    private boolean mShowFooter = false;
    private RecyclerView.Adapter<T> mAdapter;

    public PtrRecyclerViewAdapter(RecyclerView.Adapter<T> adapter, boolean includeHeader, boolean includeFooter) {
        mAdapter = adapter;
        if (includeHeader) mHeaderViewId = android.R.layout.simple_list_item_1;
        if (includeFooter) mFooterViewId = android.R.layout.simple_list_item_1;

    }

    public PtrRecyclerViewAdapter(RecyclerView.Adapter<T> adapter, int headerViewId, int footerViewId) {
        mAdapter = adapter;
        mHeaderViewId = headerViewId;
        mFooterViewId = footerViewId;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
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
                }
            });
        }
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
        if (holder.getItemViewType() == VIEW_TYPE_HEADER) {
            TextView tv = (TextView) holder.itemView;
            tv.setText("Header");
            tv.setTextColor(Color.BLACK);
            tv.setBackgroundColor(Color.RED);
            tv.setGravity(Gravity.CENTER);
        } else if (holder.getItemViewType() == VIEW_TYPE_FOOTER) {
            TextView tv = (TextView) holder.itemView;
            tv.setText("Footer");
            tv.setTextColor(Color.BLACK);
            tv.setBackgroundColor(Color.BLUE);
            tv.setGravity(Gravity.CENTER);
        } else {
            mAdapter.onBindViewHolder((T)holder, showHeader() ? position-1 : position);
        }
    }

    @Override
    public int getItemCount() {
        int itemCount = mAdapter.getItemCount();
        if (showHeader()) itemCount++;
        if (showFooter()) itemCount++;
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
        if (mHeaderViewId == -1) return;
        mShowHeader = showHeader;
        if (showHeader)
            notifyItemInserted(0);
        else
            notifyItemRemoved(0);
    }

    public void setShowFooter(boolean showFooter) {
        if (mFooterViewId == -1) return;
        mShowFooter = showFooter;
        if (showFooter)
            notifyItemInserted(getItemCount()-1);
        else
            notifyItemRemoved(getItemCount()-1);
    }

    private boolean showHeader() {
        return mHeaderViewId != -1 && mShowHeader;
    }

    private boolean showFooter() {
        return mFooterViewId != -1 && mShowFooter;
    }
}
