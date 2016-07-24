package ru.moleculus.moveme.presenters;

/**
 * Created by Oleg on 02.04.2016.
 */
public interface GeocodeViewPresenter extends BasePresenter{

    void findAddress(double latitude, double longitude);

}
