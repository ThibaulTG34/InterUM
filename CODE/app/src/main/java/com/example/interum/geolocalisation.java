package com.example.interum;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.GoogleMap;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class geolocalisation extends AppCompatActivity {

    LocationListener androidLocationListener;
    TextView t;

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.geolocalisation);

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Button refuser = findViewById(R.id.button14);
        Button accepter = findViewById(R.id.button10);
        t = findViewById(R.id.textView20);

        refuser.setOnClickListener(view -> {
            Intent intention = new Intent(geolocalisation.this, ListOffre.class);
            intention.putExtra("location", "");
            startActivity(intention);
        });

        accepter.setOnClickListener(view -> {
            int LOCATION_REQUEST = 100;

            ActivityCompat.requestPermissions(geolocalisation.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    Location loc;
                    loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (loc != null) {
                        // Localisation disponible
                        t.setText("Vous étiez récemment ici : " + loc.getLatitude() + " / " + loc.getLongitude());
                    }
                }
            }else {
                Toast.makeText(this, "Votre appareil n'est pas compatible !", Toast.LENGTH_SHORT).show();
            }

            androidLocationListener = new LocationListener() {
                public void onLocationChanged(Location loc) {
                    Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                    List<Address> addresses = null;
                    try {
                        addresses = geocoder.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    assert addresses != null;
                    String city = addresses.get(0).getLocality();
                    t.setText("Vous êtes actuellement à : "+ city);

                    Intent intention = new Intent(geolocalisation.this, ListOffre.class);
                    intention.putExtra("location", city);
                    startActivity(intention);
                }

                public void onStatusChanged(String provider, int status, Bundle extras) {}
                public void onProviderEnabled(String provider) {}
                public void onProviderDisabled(String provider) {}
            };


            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    1000, // en millisecondes
                    1, // en mètres
                    androidLocationListener);
        });
    }
}

