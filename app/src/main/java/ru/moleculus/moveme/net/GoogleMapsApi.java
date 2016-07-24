package ru.moleculus.moveme.net;

import retrofit.http.GET;
import retrofit.http.Query;
import ru.moleculus.moveme.net.beans.googlemaps.AddressResponse;
import ru.moleculus.moveme.net.beans.googlemaps.LocationResponse;
import rx.Observable;

/**
 * Created by Oleg on 24.03.2016.
 */
public interface GoogleMapsApi {

    @GET("/json")
    Observable<LocationResponse> getLocation(@Query("address") String address);

    @GET("/json")
    Observable<AddressResponse> getAddress(@Query("latlng") String latlng);
}
