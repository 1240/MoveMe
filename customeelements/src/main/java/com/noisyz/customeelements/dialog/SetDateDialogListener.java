package com.noisyz.customeelements.dialog;

import android.app.DatePickerDialog;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by Oleg on 05.03.2016.
 */
public abstract class SetDateDialogListener implements DatePickerDialog.OnDateSetListener {

    private int fieldId;

    public void setFieldId(int id){
        this.fieldId = id;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, monthOfYear);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        fillTextField(fieldId, calendar.getTimeInMillis());
    }

    public abstract void fillTextField(int fieldId, long time);
}
