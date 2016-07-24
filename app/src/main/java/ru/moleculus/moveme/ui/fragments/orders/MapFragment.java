package ru.moleculus.moveme.ui.fragments.orders;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.noisyz.customeelements.utils.SimpleAnimationUtils;

import java.util.ArrayList;

import ru.moleculus.moveme.R;
import ru.moleculus.moveme.data.beans.Location;
import ru.moleculus.moveme.data.beans.Order;
import ru.moleculus.moveme.presenters.GeocodeViewPresenter;
import ru.moleculus.moveme.presenters.impl.GeocodeViewPresenterImpl;
import ru.moleculus.moveme.ui.activity.CreateOrderActivity;
import ru.moleculus.moveme.ui.fragments.BaseFragment;
import ru.moleculus.moveme.view.GeocodeView;

/**
 * Created by Oleg on 02.03.2016.
 */
public class MapFragment extends OrdersListFragment implements GeocodeView, GoogleMap.OnMarkerClickListener,
        View.OnClickListener, Toolbar.OnMenuItemClickListener {

    private MapView mMapView;
    private Bundle mBundle;
    private ArrayList<Order> orders;
    private MenuItem actionAdd;
    private ArrayList<MarkerWrapper> wrappers;
    private GoogleMap googleMap;
    private int type = ORDER_TYPE_EVAC;
    private GeocodeViewPresenter presenter;

    public MapFragment() {
    }

    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mode = ORDERS_ALL;
        this.mBundle = savedInstanceState;
        presenter = new GeocodeViewPresenterImpl(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.map_fragment, null);
        view.findViewById(R.id.btn_order_create).setOnClickListener(this);
        SimpleAnimationUtils.
                hideOutsideOfScreenBottomNow(view.findViewById(R.id.btn_save_data));
        mMapView = (MapView) view.findViewById(R.id.map);
        mMapView.onCreate(mBundle);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
        setUpMapIfNeeded(getView());
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }

    @Override
    protected Toolbar initToolbar() {
        Toolbar toolbar = (Toolbar) getView().findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu_map_client_side_toolbar);
        actionAdd = toolbar.getMenu().findItem(R.id.action_add_order);
        toolbar.setOnMenuItemClickListener(this);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle(R.string.orders);
        return toolbar;
    }

    @Override
    public View getEmptyListTooltip() {
        return null;
    }

    private void setUpMapIfNeeded(View inflatedView) {
        if (googleMap == null) {
            googleMap = ((MapView) inflatedView.findViewById(R.id.map)).getMap();
            if (googleMap != null &&
                    !(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                            ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
                googleMap.setMyLocationEnabled(true);
                googleMap.setOnMarkerClickListener(this);
            }
        }
    }

    @Override
    public void showOrders(ArrayList<Order> itemList) {
        this.orders = itemList;
        updateByOrders();
    }

    @Override
    public void showProgressView() {
    }

    @Override
    public void hideProgressView() {
    }

    private void updateByOrders() {
        googleMap.clear();
        wrappers = new ArrayList<>();
        for (Order order : orders) {
            Location location = order.getLocations().get(0);
            if (location.getLatitude() != INVALID_LOCATION && location.getLongitude() != INVALID_LOCATION) {
                BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(),
                        R.drawable.pin_me, bitmapOptions);
                MarkerOptions options = new MarkerOptions().icon(
                        BitmapDescriptorFactory.fromBitmap(bitmap)
                ).position(new LatLng(location.getLatitude(), location.getLongitude()));

                Marker marker = googleMap.addMarker(options);
                wrappers.add(new MarkerWrapper(order, marker));
            }
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        showOrder(getOrderByMarker(wrappers, marker));
        return false;
    }

    private void showOrder(Order order) {
        onItemClick(0, order);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_order_create:
                sendCoordinates(googleMap.getCameraPosition().target.latitude,
                        googleMap.getCameraPosition().target.longitude);
                break;
        }

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_evac:
                actionAdd.setIcon(R.drawable.evacuator_white);
                type = ORDER_TYPE_EVAC;
                break;
            case R.id.action_pass:
                actionAdd.setIcon(R.drawable.bus_white);
                type = ORDER_TYPE_PASS;
                break;
            case R.id.action_cargo:
                actionAdd.setIcon(R.drawable.truck_white);
                type = ORDER_TYPE_CARGO;
                break;
            case R.id.action_search:
                search();
                break;
        }
        return false;
    }

    @Override
    public void sendCoordinates(double latitude, double longitude) {
        presenter.findAddress(latitude, longitude);
    }

    @Override
    public void showAddress(String address, double latitude, double longitude) {
        Location location = new Location();
        location.setAddress(address);
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        Intent intent = new Intent(getContext(), CreateOrderActivity.class);
        intent.putExtra(EXTRA_ORDER_TYPE, type);
        intent.putExtra(EXTRA_LOCATION, location);
        getContext().startActivity(intent);
    }

    private final class MarkerWrapper {

        private Order order;
        private Marker marker;

        public MarkerWrapper(Order order, Marker marker) {
            this.marker = marker;
            this.order = order;
        }

        private Marker getMarker() {
            return marker;
        }

        private Order getOrder() {
            return order;
        }

    }

    public static Order getOrderByMarker(ArrayList<MarkerWrapper> wrappers, Marker marker) {
        for (MarkerWrapper wrapper : wrappers)
            if (wrapper.getMarker().equals(marker)) {
                return wrapper.getOrder();
            }
        return null;
    }


    private void search() {
        try {
            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .build(getActivity());
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(getActivity(), data);
                showAddress(place.getAddress().toString(), place.getLatLng().latitude, place.getLatLng().longitude);
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(getActivity(), data);
                showErrorMessage(status.getStatusMessage());
            }
        }
    }

}
