package com.omottec.demoapp.view.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.omottec.demoapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qinbingbing on 25/07/2017.
 */

public class PicRecyclerAdapter extends RecyclerView.Adapter<PicRecyclerAdapter.PicHolder> {
    private List<PicItem> mData = new ArrayList<>();

    public void addDataAtLast(List<PicItem> data) {
        int oldSize = mData.size();
        mData.addAll(data);
        notifyItemRangeInserted(oldSize, data.size());
//        notifyDataSetChanged();
    }

    public void reset(List<PicItem> data) {
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }

    public PicRecyclerAdapter(List<PicItem> data) {
        mData.addAll(data);
    }

    public PicRecyclerAdapter() {
    }

    @Override
    public PicHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PicHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.l_pic_item, parent, false));
    }

    @Override
    public void onBindViewHolder(PicHolder holder, int position) {
        holder.mSdv.setImageURI(mData.get(position).picUrl);
        holder.mTv.setText(mData.get(position).title);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    static class PicHolder extends RecyclerView.ViewHolder {
        private SimpleDraweeView mSdv;
        private TextView mTv;

        public PicHolder(View itemView) {
            super(itemView);
            mSdv = (SimpleDraweeView) itemView.findViewById(R.id.sdv);
            mTv = (TextView) itemView.findViewById(R.id.tv);
        }
    }

    public static class PicItem {
        public PicItem(String picUrl, String title) {
            this.picUrl = picUrl;
            this.title = title;
        }

        public String picUrl;
        public String title;
    }
}
