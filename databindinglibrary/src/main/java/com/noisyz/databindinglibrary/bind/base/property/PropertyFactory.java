package com.noisyz.databindinglibrary.bind.base.property;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.noisyz.databindinglibrary.annotations.field.ImageField;
import com.noisyz.databindinglibrary.annotations.field.SimpleAdapterViewField;
import com.noisyz.databindinglibrary.annotations.field.SimpleFieldType;
import com.noisyz.databindinglibrary.annotations.methods.GetterMethod;
import com.noisyz.databindinglibrary.annotations.methods.ImageGetterMethod;
import com.noisyz.databindinglibrary.annotations.methods.SetterMethod;
import com.noisyz.databindinglibrary.annotations.methods.SimpleAdapterViewGetter;
import com.noisyz.databindinglibrary.annotations.methods.SimpleAdapterViewSetter;
import com.noisyz.databindinglibrary.bind.base.AbsUIBinder;
import com.noisyz.databindinglibrary.wrappers.PropertyViewWrapper;
import com.noisyz.databindinglibrary.wrappers.WrapperViewFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Oleg on 05.04.2016.
 */
public class PropertyFactory {

    public static ArrayList<Property> getPropertyList(AbsUIBinder parentBinder, Object object, View parentView) {
        ArrayList<Property> properties = new ArrayList<>();
        properties.addAll(getFieldPropertyList(parentBinder, object, parentView));
        properties.addAll(getMethodPropertyList(parentBinder, object, parentView));
        return properties;
    }

    private static ArrayList<Property> getMethodPropertyList(AbsUIBinder parentBinder, Object object, View parentView) {
        ArrayList<Property> methodList = new ArrayList<>();
        for (Method method : object.getClass().getDeclaredMethods()) {
            methodList.addAll(getImageGetterMethodPropertyList(method, object, parentView));
            methodList.addAll(getSimpleGetterMethodPropertyList(parentBinder, method, object, parentView));
            methodList.addAll(getSimpleAdapterGetterMethodPropertyList(parentBinder, method, object, parentView));
        }
        return methodList;
    }

    private static ArrayList<Property> getSimpleAdapterGetterMethodPropertyList
            (AbsUIBinder parentBinder, Method getter, Object object, View parentView) {
        ArrayList<Property> properties = new ArrayList<>();
        SimpleAdapterViewGetter getterMethod = getter.getAnnotation(SimpleAdapterViewGetter.class);
        if (getterMethod != null) {
            String propertyKey = getterMethod.propertyKey();
            Method setter = getAdapterSetterMethod(object, propertyKey);
            SimpleAdapterViewSetter setterMethod = null;
            if (setter != null) {
                setterMethod = setter.getAnnotation(SimpleAdapterViewSetter.class);
            }
            ArrayList<View> views = getViewsByTag((ViewGroup) parentView, propertyKey);
            for (View view : views) {
                PropertyViewWrapper viewWrapper = WrapperViewFactory.
                        getSimpleAdapterViewWrapper(getterMethod, setterMethod,
                                (AdapterView) view, object, getter, setter);
                if (viewWrapper != null) {
                    properties.add(new Property(view.getTag().toString(), viewWrapper));
                    if (!viewWrapper.hasDataUpdatedCallback() && parentBinder.hasDataUpdatedCallback()) {
                        viewWrapper.setDataUpdatedCallback(parentBinder.getDataUpdatedCallback());
                    }
                }
            }
        }
        return properties;
    }

    private static Method getAdapterSetterMethod(Object object, String propertyKey) {
        for (Method method : object.getClass().getDeclaredMethods()) {
            SimpleAdapterViewSetter setterMethod = method.getAnnotation(SimpleAdapterViewSetter.class);
            if (setterMethod != null && setterMethod.propertyKey().equals(propertyKey)) {
                return method;
            }
        }
        return null;
    }


    private static ArrayList<Property> getSimpleGetterMethodPropertyList(AbsUIBinder parentBinder, Method getter, Object object, View parentView) {
        ArrayList<Property> properties = new ArrayList<>();
        GetterMethod getterMethod = getter.getAnnotation(GetterMethod.class);
        if (getterMethod != null) {
            String propertyKey = getterMethod.propertyKey();
            Method setter = getSetterMethod(object, propertyKey);
            SetterMethod setterMethod = null;
            if (setter != null) {
                setterMethod = setter.getAnnotation(SetterMethod.class);
            }
            ArrayList<View> views = getViewsByTag((ViewGroup) parentView, propertyKey);
            for (View view : views) {
                PropertyViewWrapper viewWrapper = WrapperViewFactory.
                        getSimplePropertyViewWrapper(getterMethod, setterMethod, view, object, getter, setter);
                if (viewWrapper != null) {
                    properties.add(new Property(view.getTag().toString(), viewWrapper));
                    if (!viewWrapper.hasDataUpdatedCallback() && parentBinder.hasDataUpdatedCallback()) {
                        viewWrapper.setDataUpdatedCallback(parentBinder.getDataUpdatedCallback());
                    }
                }
            }
        }
        return properties;
    }

    private static Method getSetterMethod(Object object, String propertyKey) {
        for (Method method : object.getClass().getDeclaredMethods()) {
            SetterMethod setterMethod = method.getAnnotation(SetterMethod.class);
            if (setterMethod != null && setterMethod.propertyKey().equals(propertyKey)) {
                return method;
            }
        }
        return null;
    }

    private static ArrayList<Property> getImageGetterMethodPropertyList(Method method, Object object, View parentView) {
        ArrayList<Property> properties = new ArrayList<>();
        ImageGetterMethod imageGetterMethod = method.getAnnotation(ImageGetterMethod.class);
        if (imageGetterMethod != null) {
            ArrayList<View> views = getViewsByTag((ViewGroup) parentView, method.getName());
            for (View view : views) {
                PropertyViewWrapper viewWrapper = WrapperViewFactory.getImagePropertyViewWrapper(imageGetterMethod, view, object, method);
                if (viewWrapper != null) {
                    properties.add(new Property((String) view.getTag(), viewWrapper));
                }
            }
        }
        return properties;
    }

    private static ArrayList<Property> getFieldPropertyList(AbsUIBinder parentBinder, Object object, View parentView) {
        ArrayList<Property> fieldList = new ArrayList<>();
        for (Field field : object.getClass().getDeclaredFields()) {
            ArrayList<View> views = getViewsByTag((ViewGroup) parentView, field.getName());
            for (View view : views) {
                AbsUIBinder viewWrapper = getFieldViewWrapper(parentBinder, field, view, object);
                if (viewWrapper != null) {
                    fieldList.add(new Property(view.getTag().toString(), viewWrapper));
                }
            }
        }
        return fieldList;
    }

    private static AbsUIBinder getFieldViewWrapper(AbsUIBinder parentBinder, Field objectField, View fieldView, Object o) {
        PropertyViewWrapper viewWrapper = null;
        SimpleFieldType simpleFieldType = objectField.getAnnotation(SimpleFieldType.class);
        if (simpleFieldType != null) {
            viewWrapper = WrapperViewFactory.getSimplePropertyViewWrapper(simpleFieldType, fieldView, o, objectField);
        }

        ImageField imageField = objectField.getAnnotation(ImageField.class);
        if (imageField != null) {
            viewWrapper = WrapperViewFactory.getImagePropertyViewWrapper(imageField, fieldView, o, objectField);
        }

        SimpleAdapterViewField simpleAdapterViewField = objectField.getAnnotation(SimpleAdapterViewField.class);
        if (simpleAdapterViewField != null) {
            viewWrapper = WrapperViewFactory.
                    getSimpleAdapterViewWrapper(simpleAdapterViewField, fieldView, o, objectField);
        }

        if (viewWrapper != null) {
            if (!viewWrapper.hasDataUpdatedCallback() && parentBinder.hasDataUpdatedCallback()) {
                viewWrapper.setDataUpdatedCallback(parentBinder.getDataUpdatedCallback());
            }
        }
        return viewWrapper;
    }

    private static ArrayList<View> getViewsByTag(ViewGroup root, String tag) {
        ArrayList<View> views = new ArrayList<>();
        final int childCount = root.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = root.getChildAt(i);
            if (child instanceof ViewGroup) {
                views.addAll(getViewsByTag((ViewGroup) child, tag));
            }

            final Object object = child.getTag();
            if (object != null) {
                final String tagObj = object.toString();
                boolean onlyOne = !tagObj.contains("#");
                boolean isField = onlyOne ? tagObj.equals(tag) : tagObj.contains(tag);
                if (!TextUtils.isEmpty(tagObj) && isField) {
                    views.add(child);
                }
            }
        }
        return views;
    }

}
