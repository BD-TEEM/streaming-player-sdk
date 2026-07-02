package com.mox_flim.me;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class M3uAdapter extends RecyclerView.Adapter<M3uAdapter.ViewHolder> {

    private final List<M3uSource> m3uList;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(M3uSource source);
    }

    public M3uAdapter(List<M3uSource> m3uList, OnItemClickListener listener) {
        this.m3uList = m3uList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int range) {
        M3uSource source = m3uList.get(range);
        holder.textView.setText(source.getName());
        holder.itemView.setOnClickListener(v -> listener.onItemClick(source));
    }

    @Override
    public int getItemCount() {
        return m3uList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(android.R.id.text1);
        }
    }
}
