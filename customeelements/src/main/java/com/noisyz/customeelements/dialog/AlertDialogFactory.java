package com.noisyz.customeelements.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;

import com.noisyz.customeelements.R;
import com.noisyz.customeelements.utils.SimpleImageUtils;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;


/**
 * Created by Oleg on 10.03.2016.
 */
public class AlertDialogFactory {

    public static final int DIALOG_USER_IMAGE = 0;

    public static Dialog initDialog(Fragment context, int type) {
        Dialog dialog = null;
        AlertDialog.Builder adb = new AlertDialog.Builder(context.getActivity());
        switch (type) {
            case DIALOG_USER_IMAGE:
                dialog = initDialogUserImage(adb, context);
                break;

        }
        return dialog;
    }


    private static int imagePickMode;
    public static final int IMAGE_PICK_MODE_GALLERY = 0;
    public static final int IMAGE_PICK_MODE_CAMERA = 1;

    public static Dialog initDialogUserImage(final AlertDialog.Builder adb, final Fragment context) {
        adb.setSingleChoiceItems(adb.getContext().getResources().getStringArray(R.array.user_image_dialog), 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                imagePickMode = which;
            }
        });
        adb.setPositiveButton(adb.getContext().getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (imagePickMode) {
                    case IMAGE_PICK_MODE_GALLERY:
                        Intent intent = new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        context.startActivityForResult(intent, IMAGE_PICK_MODE_GALLERY);
                        break;
                    case IMAGE_PICK_MODE_CAMERA:
                        intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        File photo = new File(Environment.getExternalStorageDirectory(), "Pic.jpg");
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
                        context.startActivityForResult(intent, IMAGE_PICK_MODE_CAMERA);
                        break;
                }
                dialog.dismiss();
            }
        });
        return adb.create();
    }

    public static Dialog initDialogPickImage(final Fragment context) {
        AlertDialog.Builder adb = new AlertDialog.Builder(context.getActivity());
        adb.setSingleChoiceItems(adb.getContext().getResources().getStringArray(R.array.user_image_dialog), imagePickMode, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                imagePickMode = which;
            }
        });

        adb.setPositiveButton(adb.getContext().getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (imagePickMode) {
                    case IMAGE_PICK_MODE_GALLERY:
                        Intent intent = new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        context.startActivityForResult(intent, IMAGE_PICK_MODE_GALLERY);
                        break;
                    case IMAGE_PICK_MODE_CAMERA:
                        intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        File photo = new File(Environment.getExternalStorageDirectory(), "Pic.jpg");
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
                        context.startActivityForResult(intent, IMAGE_PICK_MODE_CAMERA);
                        break;
                }
                dialog.dismiss();
            }
        });
        return adb.create();
    }

    public static DatePickerDialog createDialogWithoutDateField(Context context, DatePickerDialog.OnDateSetListener listener) {
        DatePickerDialog dpd = new DatePickerDialog(context, listener, 2017, 0, 1);
        try {
            java.lang.reflect.Field[] datePickerDialogFields = dpd.getClass().getDeclaredFields();
            for (java.lang.reflect.Field datePickerDialogField : datePickerDialogFields) {
                if (datePickerDialogField.getName().equals("mDatePicker")) {
                    datePickerDialogField.setAccessible(true);
                    DatePicker datePicker = (DatePicker) datePickerDialogField.get(dpd);
                    java.lang.reflect.Field[] datePickerFields = datePickerDialogField.getType().getDeclaredFields();
                    for (java.lang.reflect.Field datePickerField : datePickerFields) {
                        Log.i("test", datePickerField.getName());
                        if ("mDaySpinner".equals(datePickerField.getName())) {
                            datePickerField.setAccessible(true);
                            Object dayPicker = datePickerField.get(datePicker);
                            ((View) dayPicker).setVisibility(View.GONE);
                        }
                    }
                }
            }
        } catch (Exception ex) {
        }
        return dpd;
    }

    public static SetDateDialog getSetDateDialog(Context context, SetDateDialogListener listener) {
        return new SetDateDialog(context, listener);
    }

    public static SetDateDialog getSetDateDialogWithDate(Context context, SetDateDialogListener listener, int year, int month, int day) {
        return new SetDateDialog(context, listener, year, month, day);
    }

    public static SetDateDialog getSetDateDialogWithTime(Context context, SetDateDialogListener listener, long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(time));
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return new SetDateDialog(context, listener, year, month, day);
    }

    public static SetDateDialog getSetDateDialogWithCurrentDate(Context context, SetDateDialogListener listener) {
        return getSetDateDialogWithTime(context, listener, System.currentTimeMillis());
    }
}
