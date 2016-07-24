package com.noisyz.databindinglibrary.bind.base;

import com.noisyz.databindinglibrary.callback.DataUpdatedCallback;

/**
 * Created by Oleg on 24.03.2016.
 */
public abstract class AbsUIBinder<O extends Object, V extends Object> implements UIBinder<O> {

    private DataUpdatedCallback dataUpdatedCallback;
    private O object;

    public AbsUIBinder setDataUpdatedCallback(DataUpdatedCallback callback) {
        this.dataUpdatedCallback = callback;
        return this;
    }

    public void setObject(O o) {
        this.object = o;
    }

    protected void onObjectUpdated(O object, String propertyName, V value) {
        if (dataUpdatedCallback != null) {
            dataUpdatedCallback.onDataUpdated(this, object, propertyName, value);
        }
    }

    public boolean hasDataUpdatedCallback() {
        return dataUpdatedCallback != null;
    }

    public DataUpdatedCallback getDataUpdatedCallback() {
        return dataUpdatedCallback;
    }

}
