package ru.moleculus.moveme.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;
import ru.moleculus.moveme.data.beans.Location;
import ru.moleculus.moveme.net.beans.BaseResponse;

/**
 * Created by Oleg on 03.10.2015.
 */
public class ApiService {

    private static ApiService instance;

    public static ApiService getInstance() {
        if (instance == null)
            instance = new ApiService();
        return instance;
    }

    private MoveMeApi moveMeApi;
    private GoogleMapsApi googleMapsApi;

    private ApiService() {
        Gson gson = new GsonBuilder().registerTypeAdapter(Location.class, new LocationDeserializer()).
                registerTypeAdapter(Object.class, new CustomDeserializer<Object>()).create();
        final RestAdapter restAdapter = new RestAdapter.Builder().
                setEndpoint(ApiConst.BASE_URL).
                setClient(getHttpClient()).setConverter(new GsonConverter(gson)).
                setLogLevel(RestAdapter.LogLevel.FULL).
                build();

        final RestAdapter googleMapsAdapter = new RestAdapter.Builder().
                setEndpoint(ApiConst.GOOGLE_MAPS_GEOCODE_URL).setClient(getHttpClient())
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addQueryParam("language", "ru-RU");
                    }
                })
                .setLogLevel(RestAdapter.LogLevel.FULL).build();

        moveMeApi = restAdapter.create(MoveMeApi.class);
        googleMapsApi = googleMapsAdapter.create(GoogleMapsApi.class);
    }


    private OkClient getHttpClient() {
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(10000, TimeUnit.MILLISECONDS);
        return new OkClient(okHttpClient);
    }


    public MoveMeApi getMoveMeApi() {
        return moveMeApi;
    }

    public GoogleMapsApi getGoogleMapsApi() {
        return googleMapsApi;
    }


}
