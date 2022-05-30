package com.example.trucksharingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trucksharingapp.data.DatabaseHelper;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.List;

public class Estimate extends FragmentActivity implements OnMapReadyCallback,TaskLoadedCallback {
    GoogleMap map;
    SupportMapFragment mapFragment;
    DatabaseHelper db;
    TextView callTv;
    TextView payTv;
    private static final int REQUEST_CALL = 1;
    MarkerOptions place1,place2;
    Polyline currentPolyline;
    String location;
    String toLocation;
    LatLng locationLatLng;
    LatLng toLocationLatLng;
    float distance;
    TextView priceTv,timeTv;
    TextView fromTv,toTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estimate);
        db=new DatabaseHelper(this);
        mapFragment=(SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);
        mapFragment.getMapAsync(this);
        callTv=findViewById(R.id.button2);
        Intent intent=getIntent();
        location=intent.getStringExtra("location");
        toLocation=intent.getStringExtra("to location");
        priceTv=findViewById(R.id.price_tv);
        timeTv=findViewById(R.id.time_tv);
        fromTv=findViewById(R.id.from);
        toTv=findViewById(R.id.to);
        fromTv.setText("From: "+location);
        toTv.setText("To: "+toLocation);

        locationLatLng=getLocationFromAddress(this,location);
        toLocationLatLng=getLocationFromAddress(this,toLocation);

        callTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makePhoneCall();
            }
        });
        payTv=findViewById(R.id.button);
        payTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getApplicationContext(),Paypal.class);
                intent.putExtra("money",String.valueOf((int)distance/100));
                startActivity(intent);
            }
        });
        place1=new MarkerOptions().position(new LatLng(locationLatLng.latitude, locationLatLng.longitude)).title("deakin");
        place2=new MarkerOptions().position(new LatLng(toLocationLatLng.latitude, toLocationLatLng.longitude)).title("box hill");
        String url=getUrl(place1.getPosition(),place2.getPosition(),"driving");
        new FetchURL(Estimate.this).execute(url,"driving");


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map=googleMap;
        map.addMarker(place1);
        map.addMarker(place2);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(toLocationLatLng,19));
        double startLatitude=locationLatLng.latitude;
        double startLongitude=locationLatLng.longitude;
        double endLatitude=toLocationLatLng.latitude;
        double endLongitude=toLocationLatLng.longitude;
        float[] results=new float[1];
        Location.distanceBetween(startLatitude,startLongitude,endLatitude,endLongitude,results);
        distance=results[0];
        timeTv.setText("Time Travel: "+String.valueOf(distance/1000/100*60*5)+"min");
        priceTv.setText("Price: "+String.valueOf((int)distance/100)+"AUD");

    }
    private void makePhoneCall() {



            if (ContextCompat.checkSelfPermission(Estimate.this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(Estimate.this,
                        new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            } else {
                String dial = "tel:" + "687687";
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall();
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.google_maps_key);
        return url;
    }

    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyline != null)
            currentPolyline.remove();
        currentPolyline = map.addPolyline((PolylineOptions) values[0]);


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
}