package com.example.lostandfoundapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class PlacePicking extends FragmentActivity implements OnMapReadyCallback,LocationListener {

    GoogleMap map;
    SupportMapFragment mapFragment;
    Button button_location;
    EditText search_et;
    public String choosingLocation;
    public LatLng choosingPlace;
    LocationManager locationManager;

    FusedLocationProviderClient fusedLocationProviderClient;
    public void AddClick(View view){
        Intent intent2=new Intent();
        intent2.putExtra("value",choosingLocation);
        setResult(RESULT_OK, intent2);
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_picking);

        mapFragment=(SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);
        Places.initialize((getApplicationContext()),"AIzaSyCTz75NW9m9CdTf2F17aG0SRc9C_usYl0s");
        search_et=findViewById(R.id.search);
        search_et.setFocusable(false);
        search_et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Place.Field>fieldList= Arrays.asList(Place.Field.ADDRESS,Place.Field.LAT_LNG,Place.Field.NAME);
                Intent intent=new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,fieldList).build(PlacePicking.this);

                startActivityForResult(intent,100);
            }
        });
        mapFragment.getMapAsync(this);
        button_location=findViewById(R.id.button_location);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        if(ContextCompat.checkSelfPermission(PlacePicking.this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(PlacePicking.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},100);
        }
        button_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLocation();
            }
        });

    }
    @SuppressLint("MissingPermission")
    private void getLocation(){
        try{
            locationManager=(LocationManager)getApplicationContext().getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,5,PlacePicking.this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if(location!=null){
                    Geocoder geocoder=new Geocoder(PlacePicking.this,Locale.getDefault());
                    try {
                        List<Address>addresses=geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                        map.clear();
                        search_et.setText(addresses.get(0).getAddressLine(0));
                        choosingLocation= addresses.get(0).getAddressLine(0);
                        choosingPlace=new LatLng(location.getLatitude(),location.getLongitude());
                        map.addMarker(new MarkerOptions().position(choosingPlace).title(choosingLocation));
                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(choosingPlace,10));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100&&resultCode==RESULT_OK){
            Place place=Autocomplete.getPlaceFromIntent(data);
            map.clear();
            search_et.setText(place.getAddress());
            choosingLocation= place.getAddress();
            choosingPlace=place.getLatLng();
            map.addMarker(new MarkerOptions().position(choosingPlace).title(choosingLocation));
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(choosingPlace,10));


        }else if(resultCode== AutocompleteActivity.RESULT_ERROR){
            Status status=Autocomplete.getStatusFromIntent(data);
            Toast.makeText(getApplicationContext(),status.getStatusMessage(),Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map=googleMap;
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        LocationListener.super.onStatusChanged(provider, status, extras);
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        LocationListener.super.onProviderEnabled(provider);
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        LocationListener.super.onProviderDisabled(provider);
    }
}