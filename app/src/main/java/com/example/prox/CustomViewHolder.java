package com.example.prox;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class CustomViewHolder extends RecyclerView.ViewHolder {
    public TextView textName, textCategory, textPrice;
    public CardView cardView;

    public CustomViewHolder(@NonNull View itemView) {
        super(itemView);

        textName = itemView.findViewById(R.id.name_item_list);
        textCategory = itemView.findViewById(R.id.store_item_list);
        textPrice = itemView.findViewById(R.id.price_item_list);
        cardView = itemView.findViewById(R.id.items_container);
    }
}
