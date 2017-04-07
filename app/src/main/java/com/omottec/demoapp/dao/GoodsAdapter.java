package com.omottec.demoapp.dao;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.omottec.demoapp.R;

import java.util.ArrayList;
import java.util.List;

public class GoodsAdapter extends RecyclerView.Adapter<GoodsAdapter.GoodViewHolder> {

    private GoodClickListener clickListener;
    private List<Good> dataset;

    public interface GoodClickListener {
        void onUserClick(int position);
    }

    static class GoodViewHolder extends RecyclerView.ViewHolder {

        public TextView text;
        public TextView desc;

        public GoodViewHolder(View itemView, final GoodClickListener clickListener) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.textViewGoodText);
            desc = (TextView) itemView.findViewById(R.id.textViewGoodDesc);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (clickListener != null) {
                        clickListener.onUserClick(getAdapterPosition());
                    }
                }
            });
        }
    }

    public GoodsAdapter(GoodClickListener clickListener) {
        this.clickListener = clickListener;
        this.dataset = new ArrayList<Good>();
    }

    public void setUsers(@NonNull List<Good> goods) {
        dataset = goods;
        notifyDataSetChanged();
    }

    public Good getGood(int position) {
        return dataset.get(position);
    }

    @Override
    public GoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_note, parent, false);
        return new GoodViewHolder(view, clickListener);
    }

    @Override
    public void onBindViewHolder(GoodViewHolder holder, int position) {
        Good good = dataset.get(position);
        holder.text.setText(good.getName());
        holder.desc.setText(good.getDesc());
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}
