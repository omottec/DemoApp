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

public class NotesAdapter extends ClickRecyclerAdapter<NotesAdapter.NoteViewHolder> {

    private List<Note> dataset;

    static class NoteViewHolder extends ClickViewHolder {

        public TextView text;
        public TextView comment;

        public NoteViewHolder(View itemView, OnItemClickListener onItemClickListener, OnItemLongClickListener onItemLongClickListener) {
            super(itemView, onItemClickListener, onItemLongClickListener);
            text = (TextView) itemView.findViewById(R.id.textViewGoodText);
            comment = (TextView) itemView.findViewById(R.id.textViewGoodDesc);
        }
    }

    public NotesAdapter() {
        this.dataset = new ArrayList<>();
    }

    public void setNotes(@NonNull List<Note> notes) {
        dataset = notes;
        notifyDataSetChanged();
    }

    public Note getNote(int position) {
        return dataset.get(position);
    }

    @Override
    public NotesAdapter.NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(view, this, this);
    }

    @Override
    public void onBindViewHolder(NotesAdapter.NoteViewHolder holder, int position) {
        Note note = dataset.get(position);
        holder.text.setText(note.getText());
        holder.comment.setText(note.getComment());
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}
