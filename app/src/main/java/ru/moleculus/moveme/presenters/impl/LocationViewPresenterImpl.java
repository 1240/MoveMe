package ru.moleculus.moveme.presenters.impl;

import ru.moleculus.moveme.BaseConstants;
import ru.moleculus.moveme.callbacks.BaseRequestCallback;
import ru.moleculus.moveme.net.ApiService;
import ru.moleculus.moveme.net.beans.googlemaps.LocationResponse;
import ru.moleculus.moveme.presenters.LocationViewPresenter;
import ru.moleculus.moveme.view.LocationView;

/**
 * Created by Oleg on 24.03.2016.
 */
public class LocationViewPresenterImpl implements LocationViewPresenter, BaseRequestCallback<LocationResponse>, BaseConstants {

    private LocationView locationView;
    private String address;

    public LocationViewPresenterImpl(LocationView locationView) {
        this.locationView = locationView;
    }

    @Override
    public void loadLocation(String address) {
        this.address = address;
        requestInteractor.makeRequest(ApiService.getInstance().getGoogleMapsApi().getLocation(address), this);
    }

    @Override
    public void onRequestError(String message) {
        locationView.showErrorMessage(message);
    }

    @Override
    public void onRequestSuccess(LocationResponse locationResponse) {
        locationView.setLatLng(address, locationResponse.getLatitude(), locationResponse.getLongitude());
    }
}
