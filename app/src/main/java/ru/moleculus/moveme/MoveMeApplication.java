package ru.moleculus.moveme;

import android.app.Application;
import android.support.multidex.MultiDexApplication;

import com.crashlytics.android.Crashlytics;

import org.acra.ACRA;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

import io.fabric.sdk.android.Fabric;

/**
 * Created by Oleg on 11.03.2016.
 */

//@ReportsCrashes(formKey = "", // will not be used
//        mailTo = "nero232@ukr.net",
//        mode = ReportingInteractionMode.TOAST,
//        resToastText = R.string.error)

public class MoveMeApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
//        ACRA.init(this);
    }

}