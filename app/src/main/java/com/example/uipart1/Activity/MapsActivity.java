package com.example.uipart1.Activity;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uipart1.R;
import com.example.uipart1.databinding.ActivityMainBinding;
import com.example.uipart1.databinding.ActivityMapsBinding;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import Modules.DirectionFinder;
import Modules.DirectionFinderListener;
import Modules.Route;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback{

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private List<Marker> originMarkers = new ArrayList<>();
    private ProgressDialog progressDialog;
    private Button btnFindPath;
    private EditText etOrigin;
    private EditText etDestination;
    String sType;
    double lat1 = 0, long1 = 0, lat2 = 0, long2 = 0;
    int flag = 0;
    private boolean permissionDenied = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        btnFindPath = (Button) findViewById(R.id.btnFindPath);
        etOrigin = (EditText) findViewById(R.id.etOrigin);
        etDestination = (EditText) findViewById(R.id.etDestination);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Places.initialize(getApplicationContext(), "AIzaSyAqpw5OxlsWVduDAxzVMUrS7tmQohJMzQA");
        PlacesClient placesClient = Places.createClient(this);
        etDestination.setFocusable(false);
        etOrigin.setFocusable(false);
        etDestination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sType = "yourAddress";
                List<Place.Field> fields = Arrays.asList(Place.Field.ADDRESS
                        , Place.Field.LAT_LNG);
                Intent intent = new Autocomplete.IntentBuilder(
                        AutocompleteActivityMode.OVERLAY, fields
                ).build(MapsActivity.this);
                startActivityForResult(intent, 1000);
            }
        });
        etOrigin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sType = "shop";
                List<Place.Field> fields = Arrays.asList(Place.Field.ADDRESS
                        , Place.Field.LAT_LNG);
                Intent intent = new Autocomplete.IntentBuilder(
                        AutocompleteActivityMode.OVERLAY, fields
                ).build(MapsActivity.this);
                startActivityForResult(intent, 1000);
            }
        });

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
//    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng aptech = new LatLng(10.806947294704349, 106.7141768);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(aptech, 20));
        originMarkers.add(mMap.addMarker(new MarkerOptions()
                .title("Aptech Computer Education")
                .position(aptech)));
//        shop_address.setText("" + aptech);
//        mMap.setMyLocationEnabled(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == RESULT_OK) {
            Place place = Autocomplete.getPlaceFromIntent(data);

            if (sType.equals("yourAddress")) {
                flag++;
                etDestination.setText(place.getAddress());
                String your = String.valueOf(place.getLatLng());
                your = your.replaceAll("lat/lng;", "");
                your = your.replace("(", "");
                your = your.replace(")", "");
                String[] split = your.split(",");
                lat2 = Double.parseDouble(split[0]);
                long2 = Double.parseDouble(split[1]);
            } else {
                flag++;
                etOrigin.setText(place.getAddress());
                String your = String.valueOf(place.getLatLng());
                your = your.replaceAll("lat/lng;", "");
                your = your.replace("(", "");
                your = your.replace(")", "");
                String[] split = your.split(",");
                lat1 = Double.parseDouble(split[0]);
                long1 = Double.parseDouble(split[1]);
            }

            if (flag >= 2) {
                distance(lat1, long1, lat2, long2);
            }

        } else if (requestCode == AutocompleteActivity.RESULT_ERROR) {
            Status status = Autocomplete.getStatusFromIntent(data);

            Toast.makeText(getApplicationContext(), status.getStatusMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void distance(double lat1, double long1, double lat2, double long2) {
        double longDiff = long1 - long2;
        double distance = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(longDiff));
        distance = Math.acos(distance);
        distance = rad2deg(distance);
        distance = distance * 60 * 1.1515;
        distance = distance * 1.609344;
        TextView tvDistance = (TextView) findViewById(R.id.tvDistance);
        tvDistance.setText(String.format(Locale.US, "%2f Kilometers", distance));
    }

    private double rad2deg(double distance) {
        return (distance * 180.0 / Math.PI);
    }

    private double deg2rad(double lat1) {
        return (lat1 * Math.PI / 180.0);
    }



}