package com.example.prox;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<CustomViewHolder> {
    Context context;
    private List<Product> list;

    public Adapter(Context context, List<Product> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomViewHolder(LayoutInflater.from(context).inflate(R.layout.item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.textName.setText(list.get(position).getName());
        holder.textCategory.setText(list.get(position).getStore());
        holder.textPrice.setText(list.get(position).getPrice());

        //always cast data as string value of
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
