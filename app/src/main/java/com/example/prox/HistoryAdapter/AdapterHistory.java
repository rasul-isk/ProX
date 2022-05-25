package com.example.prox.HistoryAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prox.R;

import java.util.List;

public class AdapterHistory extends RecyclerView.Adapter<ViewHolderHistory> {
    Context context;
    private List<HistoryItem> list;

    public AdapterHistory(Context context, List<HistoryItem> list) {
        this.context = context;
        this.list = list;
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
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
