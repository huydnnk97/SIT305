package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    EditText editTextNumber;
    ImageButton metreButton;
    ImageButton celciusButton;
    ImageButton kilogramButton;
    int unitnumber=-1;
    TextView textView1;
    TextView textView2;
    TextView textView3;
    TextView Result1;
    TextView Result2;
    TextView Result3;
    int a=0;
    public void ChangeMetre(){
        textView1=findViewById(R.id.textView2);
        textView2=findViewById(R.id.textView9);
        textView3=findViewById(R.id.textView8);
        textView1.setText("Centimetre");
        textView2.setText("Foot");
        textView3.setText("inch");

    }
    public void ChangeCelcius(){
        textView1=findViewById(R.id.textView2);
        textView2=findViewById(R.id.textView9);
        textView3=findViewById(R.id.textView8);
        textView1.setText("Fahrenhei");
        textView2.setText("Kelvin");
        textView3.setText("");
    }
    public void ChangeKilogram(){
        textView1=findViewById(R.id.textView2);
        textView2=findViewById(R.id.textView9);
        textView3=findViewById(R.id.textView8);
        textView1.setText("Gram");
        textView2.setText("Ounce(Oz)");
        textView3.setText("Pound");

    }
    public void ChangeUnit(int i){
//        if(CheckEmptyInput()){
            if(i==0)ChangeMetre();
            if(i==1)ChangeCelcius();
            if(i==2)ChangeKilogram();
//        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_main);
        Spinner unitSpinner=findViewById((R.id.unitSpinner));
        ArrayList<String> unitList =new ArrayList<>();
        unitList.add("Metre");
        unitList.add("Celcius");
        unitList.add("Kilogram");
        ArrayAdapter adapter=new ArrayAdapter(this, android.R.layout.simple_spinner_item,unitList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unitSpinner.setAdapter(adapter);
        Result1=findViewById(R.id.textView4);
        Result2=findViewById(R.id.textView11);
        Result3=findViewById(R.id.textView10);
        editTextNumber=findViewById(R.id.editTextNumber);

        metreButton=findViewById(R.id.imageButton4);
        metreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(unitnumber==0){
                    if(!TextUtils.isEmpty(editTextNumber.getText().toString())){
                        Float val= Float.parseFloat(editTextNumber.getText().toString());
                        Result1.setText(String.format("%.0f", val * 100));
                        Result2.setText(String.format("%.2f", val / 0.3048));
                        Result3.setText(String.format("%.2f", val / 0.0254));
                        ChangeUnit(0);
                    }

                }else
                Toast.makeText(getApplicationContext(), "Please select the correct conversion icon", Toast.LENGTH_SHORT).show();
            }
        });
        celciusButton=findViewById(R.id.imageButton2);
        celciusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(unitnumber==1){
                    if(!TextUtils.isEmpty(editTextNumber.getText().toString())){
                        Float val= Float.parseFloat(editTextNumber.getText().toString());
                        Result1.setText(String.format("%.2f",val*9/5+32));
                        Result2.setText(String.format("%.2f",val+ 273.15));
                        Result3.setText("");
                    }

                }
                else{Toast.makeText(getApplicationContext(), "Please select the correct conversion icon", Toast.LENGTH_SHORT).show();}


            }
        });
        kilogramButton=findViewById(R.id.imageButton3);
        kilogramButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(unitnumber==2) {
                    if(!TextUtils.isEmpty(editTextNumber.getText().toString())) {
                        Float val = Float.parseFloat(editTextNumber.getText().toString());
                        Result1.setText(String.format("%.0f", val * 1000));
                        Result2.setText(String.format("%.2f", val * 35.274));
                        Result3.setText(String.format("%.2f", val * 2.2046));
                    }
                }
                else
                    Toast.makeText(getApplicationContext(), "Please select the correct conversion icon", Toast.LENGTH_SHORT).show();
            }
        });
        unitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(!TextUtils.isEmpty(editTextNumber.getText().toString())){
                    ChangeUnit(i);
                }

                unitnumber=i;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });



    }
}