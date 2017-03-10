package com.omottec.demoapp.view.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by qinbingbing on 10/03/2017.
 */

public class SimpleRecyclerAdapter extends RecyclerView.Adapter<SimpleRecyclerAdapter.SimpleViewHolder> {
    public static final String TAG = "SimpleRecyclerAdapter";
    private Context mContext;
    private List<String> mData;

    public SimpleRecyclerAdapter(Context context, List<String> data) {
        mContext = context;
        mData = data;
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder viewType:" + viewType);
        return new SimpleViewHolder(View.inflate(mContext, android.R.layout.simple_list_item_1, null));
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder position:" + position);
        holder.mTv.setText("item " + mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class SimpleViewHolder extends RecyclerView.ViewHolder {
        private TextView mTv;

        public SimpleViewHolder(View itemView) {
            super(itemView);
            mTv = (TextView) itemView.findViewById(android.R.id.text1);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext,
                            "AdapterPosition:" + getAdapterPosition() + ", LayoutPosition:"+ getLayoutPosition(),
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
