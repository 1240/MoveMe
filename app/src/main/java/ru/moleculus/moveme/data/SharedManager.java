package ru.moleculus.moveme.data;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Oleg on 25.02.2016.
 */
public class SharedManager {

    public static final String SHARED_KEY = "moveMeSharedPreferences";

    public static final String KEY_TOKEN = "moveMeToken";
    public static final String KEY_REGISTERED = "registered";

    public static void saveToken(Context context, String token){
        SharedPreferences sp = context.getSharedPreferences(SHARED_KEY, Context.MODE_PRIVATE);
        sp.edit().putString(KEY_TOKEN, token).apply();
    }

    public static String getToken(Context context){
        return context.getSharedPreferences(SHARED_KEY, Context.MODE_PRIVATE).getString(KEY_TOKEN,"");
    }

    public static boolean isRegistered(Context context){
        return context.getSharedPreferences(SHARED_KEY, Context.MODE_PRIVATE).getBoolean(KEY_REGISTERED, false);
    }

    public static void setRegistered(Context context, boolean isRegistered){
        SharedPreferences sp = context.getSharedPreferences(SHARED_KEY, Context.MODE_PRIVATE);
        sp.edit().putBoolean(KEY_REGISTERED, isRegistered).apply();
    }
}
