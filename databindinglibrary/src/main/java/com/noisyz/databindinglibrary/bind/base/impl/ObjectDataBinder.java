package com.noisyz.databindinglibrary.bind.base.impl;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;

import com.noisyz.databindinglibrary.annotations.field.ImageField;
import com.noisyz.databindinglibrary.annotations.field.SimpleAdapterViewField;
import com.noisyz.databindinglibrary.annotations.field.SimpleFieldType;
import com.noisyz.databindinglibrary.annotations.methods.GetterMethod;
import com.noisyz.databindinglibrary.bind.base.AbsUIBinder;
import com.noisyz.databindinglibrary.bind.base.property.Property;
import com.noisyz.databindinglibrary.bind.base.property.PropertyFactory;
import com.noisyz.databindinglibrary.callback.DataUpdatedCallback;
import com.noisyz.databindinglibrary.conversion.Converter;
import com.noisyz.databindinglibrary.wrappers.PropertyViewWrapper;
import com.noisyz.databindinglibrary.wrappers.WrapperViewFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Oleg on 17.03.2016.
 */
public class ObjectDataBinder<O extends Object> extends AbsUIBinder {

    private List<Property> propertyMap;

    private O o;

    private View parentView;

    public ObjectDataBinder(O o) {
        this();
        this.o = o;
    }

    public ObjectDataBinder() {
        propertyMap = new ArrayList<>();
    }

    public ObjectDataBinder registerView(Fragment fragment) {
        return registerView(fragment.getView());
    }

    public ObjectDataBinder registerView(Activity activity) {
        final View viewGroup = ((ViewGroup) activity
                .findViewById(android.R.id.content)).getChildAt(0);
        return registerView(viewGroup);
    }

    public View getViewParent() {
        return parentView;
    }


    public ObjectDataBinder registerView(View parentView) {
        this.parentView = parentView;
        propertyMap = PropertyFactory.getPropertyList(this, o, parentView);
        propertyMap.removeAll(Collections.singleton(null));
        return this;
    }

    public ObjectDataBinder setUpdateUIConverter(String fieldName, Converter converter) {
        if (getPropertyViewWrapper(fieldName) != null)
            getPropertyViewWrapper(fieldName).setUpdateUIConverter(converter);
        return this;
    }

    public ObjectDataBinder setUpdateObjectConverter(String fieldName, Converter converter) {
        if (getPropertyViewWrapper(fieldName) != null)
            getPropertyViewWrapper(fieldName).setUpdateObjectConverter(converter);
        return this;
    }

    private PropertyViewWrapper getPropertyViewWrapper(String key) {
        for (Property property : propertyMap) {
            if (property.getPropertyName().equals(key))
                return (PropertyViewWrapper) property.getPropertyBinder();
        }
        return null;
    }

    @Override
    public ObjectDataBinder setDataUpdatedCallback(DataUpdatedCallback callback) {
        super.setDataUpdatedCallback(callback);
        for (Property property : propertyMap)
            property.getPropertyBinder().setDataUpdatedCallback(callback);
        return this;
    }

    @Override
    public void bindUI() {
        for (Property property : propertyMap)
            property.getPropertyBinder().bindUI();
    }

    @Override
    public void setObject(Object object) {
        this.o = (O) object;
    }
}
