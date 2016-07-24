package com.noisyz.customeelements.utils;

import android.text.TextUtils;


/**
 * Created by Oleg on 11.02.2016.
 */
public class SimpleTextUtils {

    public static final String EMPTY_DATA = "null";

    public static boolean isFieldEmpty(String field) {
        return !(!TextUtils.isEmpty(field) && !TextUtils.equals(field, EMPTY_DATA));
    }

    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}
