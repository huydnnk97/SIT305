package com.example.myapplication;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button startBtn;
    EditText yourname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startBtn=findViewById(R.id.button);
        yourname=findViewById(R.id.editTextTextPersonName);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName=yourname.getText().toString();


//                Intent intent1=getIntent();
//                String getName =intent1.getStringExtra("name2");
//                if(getName!=null){yourname.setText(getName);}

                if(userName.isEmpty()){
                    Toast.makeText(MainActivity.this, "Please enter your name", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent =new Intent(getApplicationContext(),QuizActivity.class);
                    intent.putExtra("yourname",userName);
                    startActivity(intent);
//                    finish();
                }
            }
        });
    }

}