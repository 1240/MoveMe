package ru.moleculus.moveme.ui.fragments.orders.converters;

import com.noisyz.databindinglibrary.conversion.TwoWayConverter;

/**
 * Created by Oleg on 25.03.2016.
 */
public class TemperatureConverter extends TwoWayConverter<Integer, String> {
    @Override
    public String getConvertedValueToUI(Integer integer) {
        return integer + "°C";
    }

    @Override
    public Integer getConvertedValueToObject(String s) {
        return Integer.valueOf(s.substring(s.indexOf("°C")));
    }
}
