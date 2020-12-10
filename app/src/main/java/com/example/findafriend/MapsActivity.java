package com.example.findafriend;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {
    private String nombre;
    private GoogleMap mMap;
    DatabaseReference db;
    private MarkerOptions options = new MarkerOptions();
    private ArrayList<LatLng> latlngs = new ArrayList<>();
    protected LocationManager locationManager;
    protected LocationListener locationListener;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Intent intent= getIntent();
        nombre= (String) intent.getStringExtra("nombre");
        iniciarBaseDeDatos();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-1.86,-80), 2));
        db.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot child : snapshot.getChildren()) {
                            String nombre= (String) child.getKey();
                            String latitud= (String) child.child("latitud").getValue();
                            String longitud= (String) child.child("longitud").getValue();
                            mMap.addMarker(new MarkerOptions().position((new LatLng(Double.valueOf(latitud),Double.valueOf(longitud)))).title(nombre));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                }
        );
        obtenerAmigos();
    }

    private void iniciarBaseDeDatos() {
        db = FirebaseDatabase.getInstance().getReference().child("users");
    }

    private void obtenerAmigos() {
        db.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        mMap.clear();
                        for (DataSnapshot child : snapshot.getChildren()) {
                            String nombre= (String) child.getKey();
                            String latitud= (String) child.child("latitud").getValue();
                            String longitud= (String) child.child("longitud").getValue();
                            mMap.addMarker(new MarkerOptions().position((new LatLng(Double.valueOf(latitud),Double.valueOf(longitud)))).title(nombre));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                }
        );
    }
    public void guardarUbicacion(String latitud,String longitud){
        Usuario usuario= new Usuario(latitud,longitud);
        DatabaseReference hijop=db.child(nombre);
        hijop.setValue(usuario);
        }

    @Override
    public void onLocationChanged(@NonNull Location location) {
            guardarUbicacion(Double.toString(location.getLatitude()),Double.toString(location.getLongitude()));
    }
}