package com.example.trucksharingapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.location.LocationListener;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.Arrays;
import java.util.List;

public class Add_order extends AppCompatActivity  {
    CalendarView simpleCalendarView;
    Button nextPagebtn;
    String Date;
    String Time;
    String Location;
    String ToLocation;
    EditText timeEt;
    EditText LocationEt;
    EditText ToLocationEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);
        simpleCalendarView = (CalendarView) findViewById(R.id.simpleCalendarView); // get the reference of CalendarView
        simpleCalendarView.setFocusedMonthDateColor(Color.RED); // set the red color for the dates of  focused month
        simpleCalendarView.setUnfocusedMonthDateColor(Color.BLUE); // set the yellow color for the dates of an unfocused month
        simpleCalendarView.setSelectedWeekBackgroundColor(Color.RED); // Thiết lập màu đỏ cho các tuần, Từ API 23 trở lên mới hỗ trợ
        simpleCalendarView.setWeekSeparatorLineColor(Color.GREEN); // Thiết lập cho đường khoảng cách giữa các tuần là màu xanh
        // perform setOnDateChangeListener event on CalendarView
        nextPagebtn=findViewById(R.id.btn_next);
        simpleCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

                Date=String.valueOf(dayOfMonth)+"/"+month+"/"+year;
            }
        });
        timeEt=findViewById(R.id.et_time);
        LocationEt=findViewById(R.id.et_location);
        ToLocationEt=findViewById(R.id.et_toLocation);
        Places.initialize(getApplicationContext(),"AIzaSyCTz75NW9m9CdTf2F17aG0SRc9C_usYl0s");
        LocationEt.setFocusable(false);
        LocationEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Place.Field> fieldList= Arrays.asList(Place.Field.ADDRESS,Place.Field.LAT_LNG,Place.Field.NAME);
                Intent intent=new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,fieldList).build(Add_order.this);

                startActivityForResult(intent,100);
            }
        });
        ToLocationEt.setFocusable(false);
        ToLocationEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Place.Field> fieldList= Arrays.asList(Place.Field.ADDRESS,Place.Field.LAT_LNG,Place.Field.NAME);
                Intent intent=new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,fieldList).build(Add_order.this);

                startActivityForResult(intent,50);
            }
        });

        nextPagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Time=timeEt.getText().toString();
                Location=LocationEt.getText().toString();
                ToLocation=ToLocationEt.getText().toString();


                if(Date==null||TextUtils.isEmpty(timeEt.getText().toString())||TextUtils.isEmpty(LocationEt.getText().toString())){
                    Toast.makeText(Add_order.this,"PLease fill all detail",Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent=new Intent(Add_order.this,add_new_delivery_order.class);
                    intent.putExtra("date",Date);
                    intent.putExtra("time",Time);
                    intent.putExtra("location",Location);
                    intent.putExtra("to location",ToLocation);
                    startActivity(intent);
                }

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK)
        {
            if(requestCode==100){

                Place place=Autocomplete.getPlaceFromIntent(data);

                LocationEt.setText(place.getAddress());
            }
            if(requestCode==50){
                Place place=Autocomplete.getPlaceFromIntent(data);

                ToLocationEt.setText(place.getAddress());
            }
        }else if(resultCode== AutocompleteActivity.RESULT_ERROR){
            Status status=Autocomplete.getStatusFromIntent(data);
            Log.d("getlocation", status.getStatusMessage());

        }
    }
}