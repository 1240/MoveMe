package ru.moleculus.moveme.ui.fragments.user.converters;

import android.util.Log;

import com.noisyz.databindinglibrary.conversion.Converter;

import ru.moleculus.moveme.BaseConstants;
import ru.moleculus.moveme.R;

/**
 * Created by Oleg on 18.03.2016.
 */
public class TypeOfAccountConverter implements BaseConstants, Converter<Integer, Integer> {
    @Override
    public Integer getConvertedValue(Integer integer) {
        Integer value = -1;
        switch (integer) {
            case TYPE_ACCOUNT_WORKER:
                value = R.string.type_acc_worker;
                break;
            case TYPE_ACCOUNT_CLIENT:
                value = R.string.type_acc_client;
                break;
        }
        return value;
    }
}
