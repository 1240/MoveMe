package com.noisyz.databindinglibrary.wrappers.impl.view;

import android.view.View;

import com.noisyz.databindinglibrary.wrappers.ObjectBinder;

/**
 * Created by Oleg on 05.04.2016.
 */
public abstract class AbsViewWrapper<V extends View> {

    private V v;
    private ObjectBinder objectBinder;

    public AbsViewWrapper(V v, ObjectBinder objectBinder) {
        this.v = v;
        this.objectBinder = objectBinder;
    }

    protected void bindObject() {
        if (objectBinder != null) {
            objectBinder.bindObject(getViewValue());
        }
    }

    public abstract void bindUI(Object value);

    public abstract Object getViewValue();

    protected V getView() {
        return v;
    }

}
