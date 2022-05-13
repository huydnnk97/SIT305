package com.example.lostandfoundapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import com.example.lostandfoundapp.data.DatabaseHelper;
import com.example.lostandfoundapp.model.Item;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ShowActivity extends FragmentActivity implements OnMapReadyCallback {
    GoogleMap map;
    SupportMapFragment mapFragment;
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        db=new DatabaseHelper(this);
        mapFragment=(SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);
        mapFragment.getMapAsync(this);
    }
    public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }

            Address location = address.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map=googleMap;
        ArrayList<Item>arrayList= (ArrayList<Item>) db.fetchAllItem();
        ArrayList<String> locations=new ArrayList<>();
        for(int i=0;i<arrayList.size();i++){
            locations.add(arrayList.get(i).getLocation());
        }
        ArrayList<LatLng>list=new ArrayList<>();
        for(int i=0;i<arrayList.size();i++){
            list.add(getLocationFromAddress(this,arrayList.get(i).getLocation()));
            map.addMarker(new MarkerOptions().position(getLocationFromAddress(this,arrayList.get(i).getLocation())).title("Marker in Deakin").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)));


        }
        LatLng Melbourne=new LatLng(-37.840935, 144.946457);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(Melbourne,19));




    }
}