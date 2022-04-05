package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    String a;
    String b;
    Button button1;
    Button button2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Intent intent=getIntent();
        a=intent.getStringExtra("yourname");
        b=intent.getStringExtra("score");
        button1=findViewById(R.id.button3);
        button2=findViewById(R.id.button4);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent1=new Intent(getApplicationContext(),MainActivity.class);
//                startActivity(intent1);
//                intent1.putExtra("name2",a);
                finish();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAffinity();

            }
        });
        TextView Con=findViewById(R.id.textView3);
        TextView textView=findViewById(R.id.textView4);
        TextView score=findViewById(R.id.textView5);
        Con.setText("Congratulation "+a);
        textView.setText("YOURSCORE");
        score.setText(b+"/5");
    }
}