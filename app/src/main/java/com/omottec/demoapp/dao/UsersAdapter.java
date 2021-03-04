package com.omottec.demoapp.dao;

import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.omottec.demoapp.R;
import com.omottec.demoapp.view.recycler.ClickRecyclerAdapter;
import com.omottec.demoapp.view.recycler.ClickViewHolder;
import com.omottec.demoapp.view.recycler.OnItemClickListener;
import com.omottec.demoapp.view.recycler.OnItemLongClickListener;

import java.util.ArrayList;
import java.util.List;

public class UsersAdapter extends ClickRecyclerAdapter<UsersAdapter.UserViewHolder> {

    private List<User> dataset;

    static class UserViewHolder extends ClickViewHolder {

        public TextView text;
        public TextView desc;

        public UserViewHolder(View itemView, OnItemClickListener onItemClickListener, OnItemLongClickListener onItemLongClickListener) {
            super(itemView, onItemClickListener, onItemLongClickListener);
            text = (TextView) itemView.findViewById(R.id.textViewGoodText);
            desc = (TextView) itemView.findViewById(R.id.textViewGoodDesc);
        }
    }

    public UsersAdapter() {
        this.dataset = new ArrayList<User>();
    }

    public void setUsers(@NonNull List<User> users) {
        dataset = users;
        notifyDataSetChanged();
    }

    public User getUser(int position) {
        return dataset.get(position);
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_note, parent, false);
        return new UserViewHolder(view, this, this);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        User user = dataset.get(position);
        holder.text.setText(user.getName());
//        holder.desc.setText(user.getComment());
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}
