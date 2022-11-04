package com.example.splashscreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class Gmap extends AppCompatActivity {
    private FusedLocationProviderClient client;
    private SupportMapFragment mapFragment;
    private int REQUEST_CODE=111;
    private ConnectivityManager manager;
    private NetworkInfo networkInfo;
    private GoogleMap mMap;
    private Geocoder geocoder;
    private double selectedLat,selectedLng;
    private List<Address> addresses;
    private String selectedAddress;
    private TextView textview;
    private ImageView imageView;
    private AppCompatButton btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gmap);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        textview= findViewById(R.id.locationfield);
        btn = findViewById(R.id.button_map);
        String ServicePrice  = getIntent().getStringExtra("ServicePrice");
        String ProviderName  = getIntent().getStringExtra("ProviderName");
        String ProviderPhone  = getIntent().getStringExtra("ProviderPhone");
        String ServiceType  = getIntent().getStringExtra("ServiceType");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Gmap.this,ServicePrice,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Gmap.this, Address_details.class);
                intent.putExtra("address",selectedAddress);
                intent.putExtra("ServicePrice",ServicePrice);
                intent.putExtra("ProviderName", ProviderName);
                intent.putExtra("ProviderPhone",  ProviderPhone );
                intent.putExtra("ServiceType", ServiceType);


                startActivity(intent);

            }
        });



        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.maps);
        client = LocationServices.getFusedLocationProviderClient(Gmap.this);
        if (ActivityCompat.checkSelfPermission(Gmap.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation();
        }
        else
        {
            ActivityCompat.requestPermissions(Gmap.this,new String[]{ Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
        }

    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location!=null)
                {

                    mapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(@NonNull GoogleMap googleMap) {
                            mMap=googleMap;


                            LatLng latlng = new LatLng(location.getLatitude(),location.getLongitude());
                            MarkerOptions markerOptions = new MarkerOptions().position(latlng).title("You are here");
                            GetAddress(location.getLatitude(),location.getLongitude());
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng,14));
                            googleMap.addMarker(markerOptions).showInfoWindow();

                            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                                @Override
                                public void onMapClick(@NonNull LatLng latLng) {
                                    CheckConnection();

                                    if(networkInfo.isConnected() && networkInfo.isAvailable()){
                                        selectedLat=latLng.latitude;
                                        selectedLng=latLng.longitude;

                                        GetAddress(selectedLat,selectedLng);

                                    }else{
                                        Toast.makeText(Gmap.this, "Please Check Connection", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
              getCurrentLocation();

            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private  void CheckConnection(){
        manager = (ConnectivityManager) getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
        networkInfo=manager.getActiveNetworkInfo();
    }

    private void GetAddress(double mLat,double mLng){
        geocoder= new Geocoder(Gmap.this, Locale.getDefault());

        if(mLat!=0){
            try{
                addresses=geocoder.getFromLocation(mLat,mLng,1);
            }catch (IOException e){
                e.printStackTrace();
            }
            if(addresses!=null){
                String mAddress=addresses.get(0).getAddressLine(0);

                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName();
                String dis = addresses.get(0).getSubAdminArea();

                selectedAddress=mAddress;


                if(mAddress!=null){
                    MarkerOptions markerOptions=new MarkerOptions();
                    LatLng latLng = new LatLng(mLat,mLng);


                    markerOptions.position(latLng).title(selectedAddress);
                    textview.setText(selectedAddress);


                    mMap.addMarker(markerOptions).showInfoWindow();


                }else{
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }

            }else{
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "LatLng null", Toast.LENGTH_SHORT).show();
        }
    }
}