package com.bikash.bloodbank;
import android.*;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.annotation.IntegerRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.snapshot.DoubleNode;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.location.LocationManager.GPS_PROVIDER;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    public static GoogleMap mMap;
    public static int n = 10;
    public static Map<String, Coordinate> locationsMap;

    GoogleApiClient mGoogleApiClient;
    LocationManager locationManager;
    LocationListener locationListener;
    public static LatLng currentLocation;
    public static MarkerOptions currentMurker;
    int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    public static ArrayList<LatLng> Locations;
    public static ArrayList<MarkerOptions> LocationMarkers;
    public static ArrayList<Coordinate> BusArray;
    public static ArrayList<String> buses;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        locationsMap = new HashMap<>();
        currentLocation = new LatLng(22.89701915346086, 89.50657311826944);
        currentMurker = new MarkerOptions().position(currentLocation).title("Your Location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
        BusArray = new ArrayList<>();




        /**
         * Getting current location
         */
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks((GoogleApiClient.ConnectionCallbacks) MapsActivity.this)
                    .addOnConnectionFailedListener((GoogleApiClient.OnConnectionFailedListener) this)
                    .addApi(LocationServices.API)
                    .build();
        }

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Double lat = location.getLatitude();
                Double lng = location.getLongitude();

                currentLocation = new LatLng(lat, lng);
                currentMurker = new MarkerOptions().position(currentLocation).title("Your Location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));

                Log.i("LOCATION4", String.valueOf(lat));
                Log.i("LOCATION5", String.valueOf(lng));
                locationUpdate();

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };


        for(int i=0; i<DonorList.donorInfo.size(); i++){
            Donor donor = DonorList.donorInfo.get(i);
            Double lat = new Double(donor.lat);
            Double lng = new Double(donor.lan);
            Coordinate coordinate = new Coordinate(lat,lng);
            String donorName = donor.name+ " " + donor.contuctNumber;
            locationsMap.put(donorName,coordinate);

            LatLng l = new LatLng(coordinate.getLat(), coordinate.getLan());
            MarkerOptions m = new MarkerOptions().position(l).title(donorName);
            mMap.addMarker(m);
        }



        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if(!locationManager.isProviderEnabled(GPS_PROVIDER)){
            Toast.makeText(MapsActivity.this, "Please Turn on Location", Toast.LENGTH_LONG).show();
            Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            MapsActivity.this.startActivity(myIntent);
        }


        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            /**
             * Crating a location request
             */



            ActivityCompat.requestPermissions(MapsActivity.this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);



            return;
        }
        locationManager.requestLocationUpdates(GPS_PROVIDER, 1000, 1, locationListener);

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

    public static void locationUpdate(){
        mMap.clear();

        mMap.addMarker(currentMurker);


        buses = new ArrayList<>();
        for(int i=1; i<=n; i++){
            String s = "Bus "+i;
            buses.add(s);
        }

        Locations = new ArrayList<>();
        LocationMarkers = new ArrayList<>();
        for (int i=0; i<locationsMap.size(); i++){
            Coordinate c = locationsMap.get(buses.get(i));
            LatLng l = new LatLng(c.getLat(), c.getLan());
            Locations.add(l);
            MarkerOptions m = new MarkerOptions().position(l).title("Location"+i);
            LocationMarkers.add(m);
            Log.i("PUT", String.valueOf(i));

        }

        for(int i=0; i<LocationMarkers.size(); i++){
            mMap.addMarker(LocationMarkers.get(i));
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        mMap.addMarker(currentMurker);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation,15));

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}