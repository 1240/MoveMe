package ru.moleculus.moveme.ui.fragments.user.converters;

import android.util.Log;

import com.noisyz.databindinglibrary.conversion.Converter;

import ru.moleculus.moveme.BaseConstants;

/**
 * Created by Oleg on 18.03.2016.
 */
public class IdConverter implements Converter<Long, String>, BaseConstants {

    @Override
    public String getConvertedValue(Long s) {
        return ((SIGN_NUMBER) + " " + s);
    }
}
