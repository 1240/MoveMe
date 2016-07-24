package com.noisyz.customeelements.dialog;

import android.app.DatePickerDialog;
import android.content.Context;

/**
 * Created by Oleg on 05.03.2016.
 */
public class SetDateDialog extends DatePickerDialog {

    private SetDateDialogListener listener;

    public SetDateDialog(Context context, SetDateDialogListener listener) {
        super(context, listener, 1990, 0, 1);
        this.listener = listener;
    }

    public SetDateDialog(Context context, SetDateDialogListener listener, int year, int month, int dayOfMonth) {
        super(context, listener, year, month, dayOfMonth);
        this.listener = listener;
    }


    public void show(int fieldId) {
        listener.setFieldId(fieldId);
        super.show();
    }

}
