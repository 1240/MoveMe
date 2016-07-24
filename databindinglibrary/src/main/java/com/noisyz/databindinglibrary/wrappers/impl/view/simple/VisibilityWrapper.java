package com.noisyz.databindinglibrary.wrappers.impl.view.simple;

import android.view.View;

import com.noisyz.databindinglibrary.wrappers.ObjectBinder;
import com.noisyz.databindinglibrary.wrappers.impl.view.AbsViewWrapper;

/**
 * Created by Oleg on 18.03.2016.
 */
public class VisibilityWrapper extends AbsViewWrapper {

    public VisibilityWrapper(View view, ObjectBinder objectBinder) {
        super(view, objectBinder);
    }

    @Override
    public void bindUI(Object object) {
        if (object != null) {
            boolean value = Boolean.valueOf(object.toString());
            if (value) {
                getView().setVisibility(View.VISIBLE);
            } else {
                getView().setVisibility(View.GONE);
            }
        }
    }

    @Override
    public Object getViewValue() {
        return getView().getVisibility() == View.VISIBLE;
    }
}
