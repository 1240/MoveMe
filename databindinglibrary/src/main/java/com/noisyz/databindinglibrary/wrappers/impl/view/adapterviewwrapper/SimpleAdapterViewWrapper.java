package com.noisyz.databindinglibrary.wrappers.impl.view.adapterviewwrapper;

import android.os.Build;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;

import com.noisyz.databindinglibrary.wrappers.ObjectBinder;
import com.noisyz.databindinglibrary.wrappers.impl.view.AbsViewWrapper;

/**
 * Created by Oleg on 18.03.2016.
 */
public class SimpleAdapterViewWrapper extends AbsViewWrapper<AdapterView>
        implements AdapterView.OnItemSelectedListener, ViewTreeObserver.OnWindowAttachListener {

    private int indent;

    public SimpleAdapterViewWrapper(AdapterView adapterView, ObjectBinder objectBinder, int indent) {
        super(adapterView, objectBinder);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            adapterView.getViewTreeObserver().addOnWindowAttachListener(this);
        }
        this.indent = indent;
        adapterView.setOnItemSelectedListener(this);
    }


    @Override
    public void bindUI(Object value) {
        if (value != null) {
            getView().setSelection(Integer.parseInt(value.toString()) - indent);
        }
    }

    @Override
    public Object getViewValue() {
        return getView().getSelectedItemPosition() + indent;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        bindObject();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @Override
    public void onWindowAttached() {

    }

    @Override
    public void onWindowDetached() {

    }
}
