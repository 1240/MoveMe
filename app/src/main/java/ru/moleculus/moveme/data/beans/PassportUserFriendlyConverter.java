package ru.moleculus.moveme.data.beans;

import com.noisyz.customeelements.utils.SimpleDateUtils;
import com.noisyz.databindinglibrary.conversion.TwoWayConverter;

import java.text.ParseException;

import ru.moleculus.moveme.net.ApiConst;

/**
 * Created by Oleg on 23.03.2016.
 */
public class PassportUserFriendlyConverter extends TwoWayConverter<String, String> {

    @Override
    public String getConvertedValueToUI(String s) {
        try {
            long time = ApiConst.DATE_FORMAT.parse(s).getTime();
            s = ApiConst.DATE_FORMAT_USER_FRIENDLY.format(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return s;
    }

    @Override
    public String getConvertedValueToObject(String s) {
        try {
            long time = ApiConst.DATE_FORMAT_USER_FRIENDLY.parse(s).getTime();
            s = ApiConst.DATE_FORMAT.format(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return s;
    }
}
