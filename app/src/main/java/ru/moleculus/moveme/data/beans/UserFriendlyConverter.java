package ru.moleculus.moveme.data.beans;

import com.noisyz.customeelements.utils.SimpleDateUtils;
import com.noisyz.databindinglibrary.conversion.TwoWayConverter;

import java.text.ParseException;

/**
 * Created by Oleg on 23.03.2016.
 */
public class UserFriendlyConverter extends TwoWayConverter<Long, String> {
    @Override
    public String getConvertedValueToUI(Long aLong) {
        if (aLong == 0)
            aLong = System.currentTimeMillis() / 1000L;
        return SimpleDateUtils.ORDERS_DATE_FORMAT.format(aLong * 1000L);
    }

    @Override
    public Long getConvertedValueToObject(String s) {
        try {
            return SimpleDateUtils.ORDERS_DATE_FORMAT.parse(s).getTime() / 1000L;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Long.valueOf(System.currentTimeMillis()/1000L);
    }
}
