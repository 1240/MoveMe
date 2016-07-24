package ru.moleculus.moveme;

/**
 * Created by Oleg on 25.02.2016.
 */
public interface BaseConstants {

    int ANIMATION_DURATION = 500;

    int BASE_SMS_WAITING_TIME = 20;

    String PHONE_CODE = "+7";

    int TYPE_ACCOUNT_INVALID = -1;

    int TYPE_ACCOUNT_CLIENT = 0;

    int TYPE_ACCOUNT_WORKER = 1;

    String TYPE_CARD = "card";

    String SIGN_NUMBER = "№";

    String PARAMS_DIVIDER = "&";

    String PARAMS_EQ = "=";

    double INVALID_LOCATION = -200;

    int LOCATION_REQUEST_CODE = 2;
    int CARGO_REQUEST_CODE = 3;
    int MAP_REQUEST_CODE = 4;
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 5;

    int RESULT_DELETE = 100;

    String EXTRA_LOCATION = "extra_location";

    int ORDER_TYPE_EVAC = 3;
    int ORDER_TYPE_PASS = 1;
    int ORDER_TYPE_CARGO = 0;

    String EXTRA_ORDER_TYPE = "extra_order_type";
    String EXTRA_ORDER = "extra_order";
    String EXTRA_IMAGE = "extra_image_url";
    String EXTRA_MODE = "extra_mode";
    String EXTRA_CARGO = "extra_cargo";
    String EXTRA_POSITION = "extra_position";
    String EXTRA_ID = "extra_id";
    String EXTRA_LATITUDE = "extra_latitude";
    String EXTRA_LONGITUDE = "extra_longitude";
    String ACTION_LOGIN = "ru.moleculus.moveme.ACTION_LOGIN";

    int MIN_TEMPERATURE = -30;

    int MAX_TEMPERATURE = 30;

    int ORDERS_MY = 0;
    int ORDERS_ALL = 1;
    int ORDERS_TEMPLATE = 2;

}
