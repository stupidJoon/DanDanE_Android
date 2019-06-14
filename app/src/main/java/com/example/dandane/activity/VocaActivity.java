package com.example.dandane.activity;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.dandane.R;
import com.example.dandane.adapter.VocaAdapter;
import com.example.dandane.adapter.WordAdapter;
import com.example.dandane.item.VocaItem;
import com.example.dandane.network.NetworkSingleton;

public class VocaActivity extends AppCompatActivity {
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    RecyclerView recyclerView;

    WordAdapter adapter;
    VocaItem vocaItem;
    Integer position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voca);

        Intent intent = getIntent();
        position = intent.getIntExtra("position", 1000000000);
        vocaItem = NetworkSingleton.getInstance().vocaItems.get(position);
        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(vocaItem.getTitle() + " 단어장");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
        drawerLayout = findViewById(R.id.drawerVoca);
        navigationView = findViewById(R.id.navigationVoca);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        recyclerView = findViewById(R.id.wordRecyclerView);
        recyclerView.setHasFixedSize(true);
        adapter = new WordAdapter(NetworkSingleton.getInstance().vocaItems.get(position).getVoca());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
