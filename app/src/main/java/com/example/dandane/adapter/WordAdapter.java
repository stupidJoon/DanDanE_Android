package com.example.dandane.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.dandane.item.WordItem;

import java.util.ArrayList;

public class WordAdapter extends RecyclerView.Adapter<VocaAdapter.ItemViewHolder> {
    ArrayList<WordItem> wordItems;

    public WordAdapter( ArrayList<WordItem> wordItems) {
        this.wordItems = wordItems;
    }
    @NonNull
    @Override
    public VocaAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }
    @Override
    public void onBindViewHolder(@NonNull VocaAdapter.ItemViewHolder itemViewHolder, int i) {

    }
    @Override
    public int getItemCount() { return wordItems.size(); }
}
