package com.omottec.demoapp.view.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.omottec.demoapp.app.MyApplication;

import java.util.List;

/**
 * Created by qinbingbing on 10/03/2017.
 */

public class MultiRecyclerAdapter extends RecyclerView.Adapter {
    public static final int VIEW_TYPE_SIMPLE = 0;
    public static final int VIEW_TYPE_MULTI = 1;
    private Context mContext;
    private List<String> mData;

    public MultiRecyclerAdapter(Context context, List<String> data) {
        mContext = context;
        mData = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_SIMPLE:
                return new SimpleViewHolder(View.inflate(mContext, android.R.layout.simple_list_item_1, null));
            default:
            case VIEW_TYPE_MULTI:
                return new MultiViewHolder(View.inflate(mContext, android.R.layout.activity_list_item, null));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SimpleViewHolder) {
            SimpleViewHolder simpleViewHolder = (SimpleViewHolder) holder;
            simpleViewHolder.mTv.setText(mData.get(position));
        } else if (holder instanceof MultiViewHolder) {
            MultiViewHolder multiViewHolder = (MultiViewHolder) holder;
            multiViewHolder.mIv.setImageResource(android.R.drawable.sym_def_app_icon);
            multiViewHolder.mTv.setText(mData.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position % 2 == 0 ? VIEW_TYPE_SIMPLE : VIEW_TYPE_MULTI;
    }

    static class MultiViewHolder extends RecyclerView.ViewHolder {
        ImageView mIv;
        TextView mTv;

        public MultiViewHolder(View itemView) {
            super(itemView);
            mIv = (ImageView) itemView.findViewById(android.R.id.icon);
            mTv = (TextView) itemView.findViewById(android.R.id.text1);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String toast = new StringBuilder()
                            .append("ItemViewType:").append(getItemViewType())
                            .append(", AdapterPosition:").append(getAdapterPosition())
                            .append(", LayoutPosition:" + getLayoutPosition())
                            .toString();
                    Toast.makeText(MyApplication.getContext(), toast, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    static class SimpleViewHolder extends RecyclerView.ViewHolder {
        TextView mTv;

        public SimpleViewHolder(View itemView) {
            super(itemView);
            mTv = (TextView) itemView.findViewById(android.R.id.text1);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String toast = new StringBuilder()
                            .append("ItemViewType:").append(getItemViewType())
                            .append(", AdapterPosition:").append(getAdapterPosition())
                            .append(", LayoutPosition:" + getLayoutPosition())
                            .toString();
                    Toast.makeText(MyApplication.getContext(), toast, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
