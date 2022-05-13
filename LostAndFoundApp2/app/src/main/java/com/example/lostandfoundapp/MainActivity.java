package com.example.lostandfoundapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btnOne,btnTwo,btnThree;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnOne=findViewById(R.id.button);
        btnTwo=findViewById(R.id.button2);
        btnThree=findViewById(R.id.button3);
        btnOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(getApplicationContext(),NewAdvertActivity.class);
                startActivity(intent1);
            }
        });
        btnTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2=new Intent(getApplicationContext(),ListItem.class);
                startActivity(intent2);
            }
        });
        btnThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3=new Intent(getApplicationContext(),ShowActivity.class);
                startActivity(intent3);
            }
        });
    }
}