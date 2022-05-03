package com.example.lostandfoundapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.lostandfoundapp.data.DatabaseHelper;
import com.example.lostandfoundapp.model.Item;

public class ItemDetail extends AppCompatActivity {
    TextView statusTv,nameTv,phoneTv,descriptionTv,dateTv,locationTv;
    Item item;
    DatabaseHelper db;
    Button removeBtn;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        Intent intent=getIntent();
        id=intent.getIntExtra("item id",0);
        db=new DatabaseHelper(this);
        item=db.fetchItem(id);
        statusTv=findViewById(R.id.textView);
        nameTv=findViewById(R.id.textView2);
        phoneTv=findViewById(R.id.textView3);
        descriptionTv=findViewById(R.id.textView4);
        dateTv=findViewById(R.id.textView5);
        locationTv=findViewById(R.id.textView6);

        statusTv.setText(item.getLost());
        nameTv.setText(item.getName());
        phoneTv.setText(item.getPhone());
        descriptionTv.setText(item.getDescription());
        dateTv.setText(item.getDate());
        locationTv.setText(item.getLocation());
        removeBtn=findViewById(R.id.removeBtn);
        removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.deleteItem(id);
                Intent intent1=new Intent(getApplicationContext(),ListItem.class);
                startActivity(intent1);
                finish();
            }
        });
    }
}