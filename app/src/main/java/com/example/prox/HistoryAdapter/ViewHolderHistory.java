package com.example.prox.HistoryAdapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prox.R;

public class ViewHolderHistory extends RecyclerView.ViewHolder {
    public TextView textSearch, textFound;
    public CardView cardView;

    public ViewHolderHistory(@NonNull View itemView) {
        super(itemView);

        textSearch = itemView.findViewById(R.id.search_item_list);
        textFound = itemView.findViewById(R.id.found_item_list);
        cardView = itemView.findViewById(R.id.history_container);
    }
}
