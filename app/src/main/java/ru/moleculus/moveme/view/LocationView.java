package ru.moleculus.moveme.view;

/**
 * Created by Oleg on 24.03.2016.
 */
public interface LocationView extends BaseView {

    void requestCoordinates(String address);

    void setLatLng(String address, double latitude, double longitude);

}
