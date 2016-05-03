package com.kreativeco.sysbioscience.farmer.properties;

import android.app.Dialog;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.kreativeco.sysbioscience.R;
import com.kreativeco.sysbioscience.SectionActivity;

/**
 * Created by JuanC on 02/05/2016.
 */
public class CoordinatesActivity extends SectionActivity implements OnMapReadyCallback {

    MapFragment map;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private boolean mPermissionDenied = false;

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinates);
        overridePendingTransition(R.anim.slide_left_from, R.anim.slide_left);
        setStatusBarColor(SectionActivity.STATUS_BAR_COLOR);

        runMap();
    }

    public void runMap() {
        int code = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (code == ConnectionResult.SUCCESS) {

            ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);

        } else if (code == ConnectionResult.SERVICE_MISSING || code == ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED || code == ConnectionResult.SERVICE_DISABLED) {
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(code, this, 1);
            dialog.show();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(0, 0))
                .title("Marker"));


    }

}
