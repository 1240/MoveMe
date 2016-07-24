package ru.moleculus.moveme.presenters.impl;

import ru.moleculus.moveme.callbacks.BaseRequestCallback;
import ru.moleculus.moveme.net.ApiService;
import ru.moleculus.moveme.net.beans.googlemaps.AddressResponse;
import ru.moleculus.moveme.presenters.GeocodeViewPresenter;
import ru.moleculus.moveme.view.GeocodeView;

/**
 * Created by Oleg on 02.04.2016.
 */
public class GeocodeViewPresenterImpl implements GeocodeViewPresenter, BaseRequestCallback<AddressResponse> {

    private GeocodeView geocodeView;
    private Double latitude, longitude;

    public GeocodeViewPresenterImpl(GeocodeView geocodeView) {
        this.geocodeView = geocodeView;
    }

    @Override
    public void onRequestError(String message) {
        geocodeView.showErrorMessage(message);
        geocodeView.hideProgressView();
    }

    @Override
    public void onRequestSuccess(AddressResponse addressResponse) {
        geocodeView.showAddress(addressResponse.getFormattedAddress(), latitude, longitude);
        geocodeView.hideProgressView();
    }

    @Override
    public void findAddress(double latitude, double longitude) {
        geocodeView.showProgressView();
        this.latitude = latitude;
        this.longitude = longitude;
        requestInteractor.makeRequest(ApiService.getInstance().getGoogleMapsApi().
                        getAddress(String.valueOf(latitude)
                                + "," + String.valueOf(longitude)), this);
    }
}
