package com.example.dandane.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dandane.R;
import com.example.dandane.listner.VocaListner;
import com.example.dandane.item.VocaItem;

import java.util.ArrayList;

public class VocaAdapter extends RecyclerView.Adapter<VocaAdapter.ItemViewHolder> {
    ArrayList<VocaItem> vocaItems;
    VocaListner vocaListner;

    public VocaAdapter(ArrayList<VocaItem> vocaItems, VocaListner vocaListner) {
        this.vocaItems = vocaItems;
        this.vocaListner = vocaListner;
    }
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.voca_recycler, viewGroup, false);
        return new ItemViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, final int i) {
        itemViewHolder.titleTextView.setText(vocaItems.get(i).getTitle());
        itemViewHolder.descTextView.setText(vocaItems.get(i).getDesc());
        itemViewHolder.wordCountTextView.setText(Integer.toString(vocaItems.get(i).getVoca().size()));
        itemViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { vocaListner.responseListner(i); }
        });
    }
    @Override
    public int getItemCount() {
        return vocaItems.size();
    }
    class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView;
        private TextView descTextView;
        private TextView wordCountTextView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.vocaTitle);
            descTextView = itemView.findViewById(R.id.vocaDesc);
            wordCountTextView = itemView.findViewById(R.id.vocaWordCount);
        }
    }
}
