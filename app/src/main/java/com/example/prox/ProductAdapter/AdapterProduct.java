package com.example.prox.ProductAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prox.R;

import java.util.List;

public class AdapterProduct extends RecyclerView.Adapter<ViewHolderProduct> {
    Context context;
    private List<Product> list;
    private ListenerProduct listener;

    public AdapterProduct(Context context, List<Product> list, ListenerProduct listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolderProduct onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolderProduct(LayoutInflater.from(context).inflate(R.layout.item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderProduct holder, @SuppressLint("RecyclerView") int position) {
        holder.textName.setText(list.get(position).getName());
        holder.textCategory.setText(list.get(position).getStore());
        holder.textPrice.setText(list.get(position).getPrice());

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
