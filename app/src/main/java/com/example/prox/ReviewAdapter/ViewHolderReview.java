package com.example.prox.ReviewAdapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prox.R;

public class ViewHolderReview extends RecyclerView.ViewHolder {
    public TextView textUsername, textRating, textFeedback;
    public CardView cardView;

    public ViewHolderReview(@NonNull View itemView) {
        super(itemView);

        textUsername = itemView.findViewById(R.id.name_item_list);
        textRating = itemView.findViewById(R.id.store_item_list);
        textFeedback = itemView.findViewById(R.id.price_item_list);
        cardView = itemView.findViewById(R.id.items_container);
    }
}
