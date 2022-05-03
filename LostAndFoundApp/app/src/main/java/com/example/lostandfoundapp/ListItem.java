package com.example.lostandfoundapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lostandfoundapp.data.DatabaseHelper;
import com.example.lostandfoundapp.model.Item;

import java.util.ArrayList;
import java.util.List;

public class ListItem extends AppCompatActivity implements ItemAdapter.OnRowClickListener{
    RecyclerView recyclerView;
    List<Item> itemList=new ArrayList<>();
    ItemAdapter adapter;
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_item);
        db=new DatabaseHelper(this);
        itemList=db.fetchAllItem();
        recyclerView=findViewById(R.id.rvItem);
        adapter=new ItemAdapter(itemList,ListItem.this,this);
        RecyclerView.LayoutManager vertical_manager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(vertical_manager);
    }

    @Override
    public void onItemClick(int id) {
        Intent intent=new Intent(getApplicationContext(),ItemDetail.class);
        intent.putExtra("item id",id);
        startActivity(intent);

    }
}