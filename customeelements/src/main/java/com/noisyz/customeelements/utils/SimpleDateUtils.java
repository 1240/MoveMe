package com.noisyz.customeelements.utils;

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Oleg on 12.02.2016.
 */
public class SimpleDateUtils {
    public static SimpleDateFormat ORDERS_DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
    public static SimpleDateFormat YESTERDAY_OR_TODAY_FORMAT = new SimpleDateFormat("dd MMM, hh:mm");

    public static String formatToYesterdayOrToday(long datetime) {
        long time = datetime * 1000L;
        Date dateTime = new Date(time);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateTime);
        Calendar today = Calendar.getInstance();
        Calendar yesterday = Calendar.getInstance();
        yesterday.add(Calendar.DATE, -1);
        DateFormat timeFormatter = new SimpleDateFormat("hh:mm");
        if (calendar.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
                calendar.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR)) {
            return "Сегодня, " + timeFormatter.format(dateTime);
        } else if (calendar.get(Calendar.YEAR) == yesterday.get(Calendar.YEAR) && calendar.get(Calendar.DAY_OF_YEAR) == yesterday.get(Calendar.DAY_OF_YEAR)) {
            return "Завтра, " + timeFormatter.format(dateTime);
        } else {
            return YESTERDAY_OR_TODAY_FORMAT.format(new Date(time));
        }
    }
}
