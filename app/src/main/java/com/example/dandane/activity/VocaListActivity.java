package com.example.dandane.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.dandane.dialog.CustomDialog;
import com.example.dandane.R;
import com.example.dandane.adapter.VocaAdapter;
import com.example.dandane.listner.VocaListner;
import com.example.dandane.item.VocaItem;
import com.example.dandane.network.NetworkSingleton;

import java.util.ArrayList;
import java.util.Collections;

public class VocaListActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, VocaListner {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;

    Boolean isEditing = false;
    ArrayList<Integer> isSelected = new ArrayList<>();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (isEditing) {
            getMenuInflater().inflate(R.menu.voca_toolbar_edit, menu);
        }
        else {
            getMenuInflater().inflate(R.menu.voca_toolbar, menu);
        }
        return true;
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        if (item.getTitle().equals("edit")) {
            isEditing = true;
            getSupportActionBar().setTitle("단어장 수정");
            invalidateOptionsMenu();
        }
        else if (item.getTitle().equals("setting")) {
            Intent intent = new Intent(VocaListActivity.this, SettingActivity.class);
            startActivity(intent);
        }
        else if (item.getTitle().equals("add")) {
            final CustomDialog dialog = new CustomDialog(VocaListActivity.this);
            dialog.setPositiveOnclickListner(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dialog.getTitle().equals("")) {
                        Toast.makeText(VocaListActivity.this, "단어장 제목을 입력해주세요", Toast.LENGTH_SHORT).show();
                    }
                    else if (dialog.getDesc().equals("")) {
                        Toast.makeText(VocaListActivity.this, "단어장 설명을 입력해주세요", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        NetworkSingleton.getInstance().vocaItems.add(new VocaItem(dialog.getTitle(), dialog.getDesc()));
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                }
            });
            dialog.setNegativeOnclickListner(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
        else if (item.getTitle().equals("delete")) {
            Log.e("List", Integer.toString(NetworkSingleton.getInstance().vocaItems.size()));
            isSelected.sort(null);
            Collections.reverse(isSelected);
            for (int position : isSelected) {
                RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(position);
                viewHolder.itemView.setBackgroundColor(Color.WHITE);
                NetworkSingleton.getInstance().vocaItems.remove(position);
            }
            isSelected = new ArrayList<>();
            adapter.notifyDataSetChanged();

        }
        else if (item.getTitle().equals("done")) {
            for (int position : isSelected) {
                RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(position);
                viewHolder.itemView.setBackgroundColor(Color.WHITE);
            }
            isEditing = false;
            isSelected = new ArrayList<>();
            getSupportActionBar().setTitle("단어장 선택");
            invalidateOptionsMenu();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voca_list);

        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("단어장 선택");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
        drawerLayout = findViewById(R.id.drawerVocaList);
        navigationView = findViewById(R.id.navigationVocaList);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        navigationView.setNavigationItemSelectedListener(this);

        recyclerView = findViewById(R.id.vocaRecyclerView);
        recyclerView.setHasFixedSize(true);
        adapter = new VocaAdapter(NetworkSingleton.getInstance().vocaItems, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        setRecyclerData();
    }

    private void setRecyclerData() {
        NetworkSingleton.getInstance().vocaItems.clear();
        NetworkSingleton.getInstance().vocaItems.add(new VocaItem("Title A", "Desc A"));
        NetworkSingleton.getInstance().vocaItems.add(new VocaItem("Title B", "Desc B"));
        NetworkSingleton.getInstance().vocaItems.add(new VocaItem("Title C", "Desc C"));
        NetworkSingleton.getInstance().vocaItems.add(new VocaItem("Title D", "Desc D"));
        adapter.notifyDataSetChanged();
    }
    private void addRecyclerData(String title, String desc) {
        NetworkSingleton.getInstance().vocaItems.add(new VocaItem(title, desc));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void responseListner(Integer position) {
        if (isEditing) {
            RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(position);
            if (isSelected.contains(position)) {
                viewHolder.itemView.setBackgroundColor(Color.WHITE);
                isSelected.remove(position);
            }
            else {
                viewHolder.itemView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
                isSelected.add(position);
            }
        }
        else {
            Intent intent = new Intent(VocaListActivity.this, VocaActivity.class);
            intent.putExtra("position", position);
            startActivity(intent);
        }
    }
}
