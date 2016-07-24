package ru.moleculus.moveme.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.noisyz.customeelements.utils.SimpleAnimationUtils;

import ru.moleculus.moveme.BaseConstants;
import ru.moleculus.moveme.R;

public class SetLocationOnMapActivity extends AppCompatActivity implements OnMapReadyCallback,
        View.OnClickListener, GoogleMap.OnMapClickListener, BaseConstants, GoogleMap.OnCameraChangeListener {

    private GoogleMap mMap;
    private LatLng current;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_location_of_address);
        initToolbar();
        findViewById(R.id.btn_save_data).setOnClickListener(this);
        SimpleAnimationUtils.
                hideOutsideOfScreenBottomNow(findViewById(R.id.btn_save_data));
    }

    @Override
    public void onResume() {
        super.onResume();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnCameraChangeListener(this);
        if (!(ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            mMap.setMyLocationEnabled(true);
        }
        googleMap.setOnMapClickListener(this);
        Bundle extras = getIntent().getExtras();
        double latitude = extras.getDouble(EXTRA_LATITUDE);
        double longitude = extras.getDouble(EXTRA_LONGITUDE);
        if (latitude != INVALID_LOCATION && longitude != INVALID_LOCATION) {
            current = new LatLng(latitude, longitude);
            updateByCurrent();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save_data:
                Intent data = new Intent();
                data.putExtra(EXTRA_LATITUDE, current.latitude);
                data.putExtra(EXTRA_LONGITUDE, current.longitude);
                setResult(RESULT_OK, data);
                finish();
                break;
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        current = latLng;
        updateByCurrent();
    }

    private void updateByCurrent() {
        SimpleAnimationUtils.showOutsideOfScreen(findViewById(R.id.btn_save_data),
                ANIMATION_DURATION);
        mMap.clear();
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(current, 10));
    }

    private void initToolbar() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        current = cameraPosition.target;
    }
}
