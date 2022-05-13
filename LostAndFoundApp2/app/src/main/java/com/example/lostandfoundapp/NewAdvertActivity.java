package com.example.lostandfoundapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lostandfoundapp.data.DatabaseHelper;
import com.example.lostandfoundapp.model.Item;

public class NewAdvertActivity extends AppCompatActivity {
    TextView nameTv,phoneTv,descriptionTv,dateTv,locationTv;
    Button saveBtn;
    String status="",name,phone,description,date,location;
    DatabaseHelper db;
    boolean check;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_advert);
        nameTv=findViewById(R.id.tv_name);
        phoneTv=findViewById(R.id.tv_phone);
        descriptionTv=findViewById(R.id.tv_description);
        dateTv=findViewById(R.id.tv_date);
        locationTv=findViewById(R.id.tv_location);
        saveBtn=findViewById(R.id.btnSave);
        db=new DatabaseHelper(this);
        locationTv.setFocusable(false);
        locationTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(getApplicationContext(),PlacePicking.class);
                startActivityForResult(intent1,100);
            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name=nameTv.getText().toString();
                phone=phoneTv.getText().toString();
                description=descriptionTv.getText().toString();
                date=dateTv.getText().toString();
                location=locationTv.getText().toString();
                if(!name.isEmpty()&&!phone.isEmpty()&&!description.isEmpty()&&!date.isEmpty()&&!location.isEmpty()&&status!="") {
                    Item item = new Item();
                    item.setLost(status);
                    item.setName(name);
                    item.setPhone(phone);
                    item.setDescription(description);
                    item.setDate(date);
                    item.setLocation(location);
                    Log.d("abc",item.getName());
                    db.insertItem(item);
                    finish();
                }
                else {
                    Toast.makeText(NewAdvertActivity.this,"please fill all detail",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.lost:
                if (checked){
                    status="lost";
                    ((CheckBox)findViewById(R.id.found)).setChecked(false);}

                break;
            case R.id.found:
                if (checked){
                    status="found";
                    ((CheckBox)findViewById(R.id.lost)).setChecked(false);}

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK&&requestCode==100){
            String location=data.getStringExtra("value");
            locationTv.setText(location);
        }
        else if (resultCode ==RESULT_CANCELED)
        {
            Toast.makeText(this, "Please enter your expense.", Toast.LENGTH_LONG ).show();

        }
    }
}