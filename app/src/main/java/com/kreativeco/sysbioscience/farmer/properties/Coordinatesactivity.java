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
import com.kreativeco.sysbioscience.farmer.currentdatas.CurrentDataProperties;
import com.kreativeco.sysbioscience.utils.ListIds;
import com.kreativeco.sysbioscience.utils.PermissionUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by JuanC on 02/05/2016.
 */
public class CoordinatesActivity extends AppCompatActivity implements LocationListener,
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback,
        GoogleMap.OnMarkerDragListener,
        GoogleMap.OnMarkerClickListener{

    LocationManager locationManager = null;
    private boolean mShowPermissionDeniedDialog = false;
    Location myLastLocation;
    GoogleMap mMap;
    private LatLng firstPin;
    private LatLng lastPin;
    private LatLng currentPin;

    ArrayList<Marker> markerArrayList = new ArrayList<>();
    ArrayList<Polyline> polylineArrayList = new ArrayList<>();

    private int pinsCounter = 0;
    private int option = 0;

    private int currentApiVersion;
    private boolean isLocationEnable = false;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private static final int RESULT_OK = 10;

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
        if (currentApiVersion >= Build.VERSION_CODES.KITKAT) {

            getWindow().getDecorView().setSystemUiVisibility(flags);

            final View decorView = getWindow().getDecorView();
            decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {

                @Override
                public void onSystemUiVisibilityChange(int visibility) {
                    if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                        decorView.setSystemUiVisibility(flags);
                    }
                }
            });
        }

        Intent intent = getIntent();
        if (intent != null) {
            option = intent.getIntExtra("option", 0);
        }

        setContentView(R.layout.activity_coordinates);
        overridePendingTransition(R.anim.slide_left_from, R.anim.slide_left);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    public void preparePins() {

        String points = CurrentDataProperties.getPropertyPoligono();
        if(points.contains("POINT")){
            points = points.replace("POINT", "");
        } else if(points.contains("POLYGON")){
            points = points.replace("POLYGON", "");
            points = points.replace(",", "");
        }

        points = points.replace("(", "");
        points = points.replace(")", "");
        points = points.replaceFirst(" ", "");

        String [] arrayCoordinates = points.split(" ");

        if (arrayCoordinates.length == 2){

            firstPin = new LatLng(Double.parseDouble(arrayCoordinates[1]), Double.parseDouble(arrayCoordinates[0]));
            currentPin = firstPin;
            addNewPin(null);

        }else if(arrayCoordinates.length > 2){

            for(int i = 0; i < arrayCoordinates.length; i += 2){

                firstPin = new LatLng(Double.parseDouble(arrayCoordinates[ i + 1]), Double.parseDouble(arrayCoordinates[i]));
                currentPin = firstPin;
                addNewPin(null);

            }

            for (int i = 0; i < markerArrayList.size(); i++) {

                if (i != (markerArrayList.size() - 1)) {
                    Polyline line = mMap.addPolyline(new PolylineOptions()
                            .add(new LatLng(markerArrayList.get(i).getPosition().latitude,
                                            markerArrayList.get(i).getPosition().longitude),
                                    new LatLng(markerArrayList.get(i + 1).getPosition().latitude,
                                            markerArrayList.get(i + 1).getPosition().longitude))
                            .width(5)
                            .color(Color.parseColor("#74C302")));

                    polylineArrayList.add(line);
                } else {
                    Polyline line = mMap.addPolyline(new PolylineOptions()
                            .add(new LatLng(markerArrayList.get(i).getPosition().latitude,
                                            markerArrayList.get(i).getPosition().longitude),
                                    new LatLng(markerArrayList.get(0).getPosition().latitude,
                                            markerArrayList.get(0).getPosition().longitude))
                            .width(5)
                            .color(Color.parseColor("#74C302")));
                    polylineArrayList.add(line);
                }

            }

        }



    }


    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        mMap.setOnMarkerDragListener(this);
        mMap.setOnMarkerClickListener(this);
        updateMyLocation();
    }

    private boolean checkReady() {
        if (mMap == null) {
            //Toast.makeText(this, R.string.txt_gps_alert, Toast.LENGTH_SHORT).show();
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

    public void launchLocationManager() {
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
        /*if (mShowPermissionDeniedDialog) {
            PermissionUtils.PermissionDeniedDialog.newInstance(false).show(getSupportFragmentManager(), "dialog");
            mShowPermissionDeniedDialog = false;
        }*/
    }

    @Override
    public void onLocationChanged(Location location) {

        locationManager.removeUpdates(this);
        lastPin = new LatLng(location.getLatitude(), location.getLongitude());
        firstPin = lastPin;
        currentPin = lastPin;

        preparePins();

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


    public void addNewPin(View view) {

        if (firstPin == null) return;

        pinsCounter++;

        Marker marker = mMap.addMarker(new MarkerOptions().position(currentPin).title("pin " + pinsCounter).draggable(true).visible(true));

        markerArrayList.add(marker);

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

        if (pinsCounter == 1) {
            firstPin = marker.getPosition();
            lastPin = firstPin;
        } else {

            lastPin = marker.getPosition();
        }

        for (Polyline line : polylineArrayList) {
            line.remove();
        }

        polylineArrayList.clear();

        if (markerArrayList.size() >= 3) {

            for (int i = 0; i < markerArrayList.size(); i++) {

                if (i != (markerArrayList.size() - 1)) {
                    Polyline line = mMap.addPolyline(new PolylineOptions()
                            .add(new LatLng(markerArrayList.get(i).getPosition().latitude,
                                            markerArrayList.get(i).getPosition().longitude),
                                    new LatLng(markerArrayList.get(i + 1).getPosition().latitude,
                                            markerArrayList.get(i + 1).getPosition().longitude))
                            .width(5)
                            .color(Color.parseColor("#74C302")));

                    polylineArrayList.add(line);
                } else {
                    Polyline line = mMap.addPolyline(new PolylineOptions()
                            .add(new LatLng(markerArrayList.get(i).getPosition().latitude,
                                            markerArrayList.get(i).getPosition().longitude),
                                    new LatLng(markerArrayList.get(0).getPosition().latitude,
                                            markerArrayList.get(0).getPosition().longitude))
                            .width(5)
                            .color(Color.parseColor("#74C302")));
                    polylineArrayList.add(line);
                }

            }
        }


        Log.e("onMarkerDragEnd", marker.getPosition().toString());
        Log.e("onMarkerDragEnd", markerArrayList.size() + "");

    }

    public void validateCoordinates(View view) {
        if (markerArrayList.size() == 0) return;
        else if (markerArrayList.size() == 1) {

            String point = "POINT (" + markerArrayList.get(0).getPosition().longitude
                    + "," + markerArrayList.get(0).getPosition().latitude +")";

            ListIds.setStringPoints(point);
            ListIds.setStringCoordinatesCounter("Coodenadas - 1 Punto");

            setResult(RESULT_OK);
            finish();
            overridePendingTransition(R.anim.slide_right_from, R.anim.slide_right);

        } else if (markerArrayList.size() < 3) {

            new AlertDialog.Builder(this).setTitle(R.string.txt_error).setMessage("El poligono debe ser por lo menos de 3 puntos").setNeutralButton(R.string.bt_close, null).show();
            return;


        }else if(markerArrayList.size() >= 3){

            String points = "";
            for (int i = 0; i<markerArrayList.size(); i ++){

                if(i < (markerArrayList.size() - 1)){
                    points += markerArrayList.get(i).getPosition().latitude + " "
                            + markerArrayList.get(i).getPosition().longitude + ", ";
                }else {
                    points += markerArrayList.get(i).getPosition().latitude + " "
                            + markerArrayList.get(i).getPosition().longitude + ", "
                            + markerArrayList.get(0).getPosition().latitude + " "
                            + markerArrayList.get(0).getPosition().longitude + "";

                }
            }

            ListIds.setStringPoints("POLYGON (("+ points +"))");
            ListIds.setStringCoordinatesCounter("Coodenadas - " + markerArrayList.size() + " Puntos");

            setResult(RESULT_OK);
            finish();
            overridePendingTransition(R.anim.slide_right_from, R.anim.slide_right);

        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }
}
