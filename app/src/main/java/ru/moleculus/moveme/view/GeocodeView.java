package ru.moleculus.moveme.view;

/**
 * Created by Oleg on 02.04.2016.
 */
public interface GeocodeView extends BaseView{

    void sendCoordinates(double latitude, double longitude);

    void showAddress(String address, double latitude, double longitude);

}
