package com.example.prox.ReviewAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prox.R;

import java.util.List;

public class AdapterReview extends RecyclerView.Adapter<ViewHolderReview> {
    Context context;
    private List<Review> list;

    public AdapterReview(Context context, List<Review> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolderReview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolderReview(LayoutInflater.from(context).inflate(R.layout.item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderReview holder, @SuppressLint("RecyclerView") int position) {
        holder.textUsername.setText(list.get(position).getUsername());
        holder.textRating.setText(list.get(position).getRating());
        holder.textFeedback.setText(list.get(position).getFeedback());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

