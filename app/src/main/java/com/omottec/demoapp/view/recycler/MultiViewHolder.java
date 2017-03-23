package com.omottec.demoapp.view.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.omottec.demoapp.app.MyApplication;

/**
 * Created by qinbingbing on 23/03/2017.
 */

public class MultiViewHolder extends RecyclerView.ViewHolder {
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
