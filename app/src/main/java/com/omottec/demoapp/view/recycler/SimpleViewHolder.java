package com.omottec.demoapp.view.recycler;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.omottec.demoapp.app.MyApplication;

/**
 * Created by qinbingbing on 23/03/2017.
 */

public class SimpleViewHolder extends ClickViewHolder {
    TextView mTv;

    public SimpleViewHolder(View itemView) {
        super(itemView, null, null);
        mTv = (TextView) itemView.findViewById(android.R.id.text1);
        mTv.setTextColor(Color.BLACK);
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

    public SimpleViewHolder(View itemView, OnItemClickListener onItemClickListener, OnItemLongClickListener onItemLongClickListener) {
        super(itemView, onItemClickListener, onItemLongClickListener);
        mTv = (TextView) itemView.findViewById(android.R.id.text1);
        mTv.setTextColor(Color.BLACK);
    }
}
