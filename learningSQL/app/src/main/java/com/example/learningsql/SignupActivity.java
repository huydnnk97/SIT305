package com.example.learningsql;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.learningsql.data.DatabaseHelper;
import com.example.learningsql.model.User;

public class SignupActivity extends AppCompatActivity {
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        EditText sUsernameEditText=findViewById(R.id.sUsernameEditText);
        EditText sPasswordEditText=findViewById(R.id.sPasswordEditText);
        EditText confirmPasswordEditText=findViewById(R.id.confirmPasswordEditext);
        Button saveButton=findViewById(R.id.saveButton);
        db=new DatabaseHelper(this);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username=sUsernameEditText.getText().toString();
                String password=sPasswordEditText.getText().toString();
                String confirmPassword=confirmPasswordEditText.getText().toString();
                if(password.equals(confirmPassword)){
                    long result=db.insertUser(new User(username,password));
                    if(result>0){
                        Toast.makeText(SignupActivity.this,"Res S",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(SignupActivity.this,"Res e",Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(SignupActivity.this,"2 diff",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}