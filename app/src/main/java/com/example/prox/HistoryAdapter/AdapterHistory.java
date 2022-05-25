package com.example.prox.HistoryAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prox.R;

import java.util.List;

public class AdapterHistory extends RecyclerView.Adapter<ViewHolderHistory> {
    Context context;
    private List<HistoryItem> list;
    private ListenerHistory listener;

    public AdapterHistory(Context context, List<HistoryItem> list, ListenerHistory listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolderHistory onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolderHistory(LayoutInflater.from(context).inflate(R.layout.history, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderHistory holder, @SuppressLint("RecyclerView") int position) {
        holder.textSearch.setText(list.get(position).getSearch());
        holder.textFound.setText(list.get(position).getFound());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClicked(list.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
