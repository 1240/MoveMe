package ru.moleculus.moveme.net;

import java.text.SimpleDateFormat;

/**
 * Created by Oleg on 03.10.2015.
 */
public class ApiConst {

    public static final String BASE_URL = "http://moveme.su/api/v2/";
    public static final String GOOGLE_MAPS_GEOCODE_URL = "http://maps.google.com/maps/api/geocode";
    public static final String MULTIPART_DATA = "multipart/form-data";
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat DATE_FORMAT_USER_FRIENDLY = new SimpleDateFormat("dd.MM.yyyy");

    public static final String EMPTY_DATA = "null";

}
