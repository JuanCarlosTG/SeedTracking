package com.kreativeco.sysbioscience.farmer.properties;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.kreativeco.sysbioscience.R;
import com.kreativeco.sysbioscience.utils.PermissionUtils;


/**
 * Created by JuanC on 02/05/2016.
 */
public class CoordinatesActivity extends AppCompatActivity implements LocationListener,
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback,
        GoogleMap.OnMarkerDragListener{

    LocationManager locationManager = null;
    private boolean mShowPermissionDeniedDialog = false;
    Location myLastLocation;
    GoogleMap mMap;
    private LatLng firstPin;
    private LatLng lastPin;
    private LatLng currentPin;

    private Marker firstMarker;
    private Marker lastMarker;
    private Marker currentMarker;

    private int pinsCounter = 0;

    private int currentApiVersion;
    private boolean isLocationEnable = false;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        currentApiVersion = Build.VERSION.SDK_INT;

        final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                //| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
                //| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                //| View.SYSTEM_UI_FLAG_FULLSCREEN
                //| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        // This work only for android 4.4+
        if(currentApiVersion >= Build.VERSION_CODES.KITKAT)
        {

            getWindow().getDecorView().setSystemUiVisibility(flags);

            final View decorView = getWindow().getDecorView();
            decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener()
                    {

                        @Override
                        public void onSystemUiVisibilityChange(int visibility)
                        {
                            if((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0)
                            {
                                decorView.setSystemUiVisibility(flags);
                            }
                        }
                    });
        }

        setContentView(R.layout.activity_coordinates);
        overridePendingTransition(R.anim.slide_left_from, R.anim.slide_left);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        mMap.setOnMarkerDragListener(this);
        updateMyLocation();
    }

    private boolean checkReady() {
        if (mMap == null) {
            Toast.makeText(this, R.string.txt_gps_alert, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    private void updateMyLocation() {
        if (!checkReady()) {
            return;
        }

        if (isLocationEnable) {
            mMap.setMyLocationEnabled(false);
            return;
        }

        // Enable the location layer. Request the location permission if needed.
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            launchLocationManager();

        } else {
            isLocationEnable = false;
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, false);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] results) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            Log.e("Entro", "entro" + requestCode);
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, results,
                Manifest.permission.ACCESS_FINE_LOCATION)) {

            Log.e("Entro", "entro 2" + requestCode);
            mMap.setMyLocationEnabled(true);
            launchLocationManager();
            isLocationEnable = true;

        } else {
            mShowPermissionDeniedDialog = true;
        }
    }

    public void launchLocationManager(){
        int code = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

        if (code == ConnectionResult.SUCCESS) {

            locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

        } else if (code == ConnectionResult.SERVICE_MISSING || code == ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED || code == ConnectionResult.SERVICE_DISABLED) {
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(code, this, 1);
            dialog.show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mShowPermissionDeniedDialog) {
            PermissionUtils.PermissionDeniedDialog.newInstance(false).show(getSupportFragmentManager(), "dialog");
            mShowPermissionDeniedDialog = false;
        }
    }

    @Override
    public void onLocationChanged(Location location) {

        locationManager.removeUpdates(this);
        lastPin = new LatLng(location.getLatitude(), location.getLongitude());
        firstPin = lastPin;
        currentPin = lastPin;

        myLastLocation = location;

        mMap.setMyLocationEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(firstPin, 14));

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    }

    @Override
    public void onProviderDisabled(String provider) {
        //if (progress != null) progress.dismiss();

        // TODO Auto-generated method stub
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String gpsAlert = getResources().getString(R.string.txt_gps_alert);
        String gpsActivate = getResources().getString(R.string.txt_gps_activate);

        builder.setMessage(gpsAlert).setCancelable(false).setPositiveButton(gpsActivate,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        Intent gpsOptionsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(gpsOptionsIntent);
                    }
                });

        builder.setNegativeButton(getResources().getString(R.string.bt_cancel),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }


    public void addNewPin(View view){

        if(firstPin == null) return;

        pinsCounter ++;

        MarkerOptions o = new MarkerOptions().position(currentPin).title("Pin uno").draggable(true).visible(true);
        mMap.addMarker(o);

    }

    @Override
    public void onMarkerDragStart(Marker marker) {
        Log.e("onMarkerDragStart", marker.getPosition().toString());
    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {

        if (pinsCounter == 1){
            firstPin = marker.getPosition();
            marker.setDraggable(false);
            lastPin = firstPin;
        } else{
            Polyline line = mMap.addPolyline(new PolylineOptions()
                    .add(new LatLng(lastPin.latitude, lastPin.longitude), new LatLng(marker.getPosition().latitude, marker.getPosition().longitude))
                    .width(5)
                    .color(Color.parseColor("#74C302")));
            lastPin = marker.getPosition();

        }

        Log.e("onMarkerDragEnd", marker.getPosition().toString());

    }
}
