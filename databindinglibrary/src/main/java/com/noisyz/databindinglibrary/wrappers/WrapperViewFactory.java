package com.noisyz.databindinglibrary.wrappers;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.noisyz.databindinglibrary.annotations.converters.Conversion;
import com.noisyz.databindinglibrary.annotations.converters.ConvertToObject;
import com.noisyz.databindinglibrary.annotations.converters.ConvertToUI;
import com.noisyz.databindinglibrary.annotations.field.ImageField;
import com.noisyz.databindinglibrary.annotations.field.SimpleAdapterViewField;
import com.noisyz.databindinglibrary.annotations.field.SimpleFieldType;
import com.noisyz.databindinglibrary.annotations.methods.GetterMethod;
import com.noisyz.databindinglibrary.annotations.methods.ImageGetterMethod;
import com.noisyz.databindinglibrary.annotations.methods.SetterMethod;
import com.noisyz.databindinglibrary.annotations.methods.SimpleAdapterViewGetter;
import com.noisyz.databindinglibrary.annotations.methods.SimpleAdapterViewSetter;
import com.noisyz.databindinglibrary.annotations.type;
import com.noisyz.databindinglibrary.conversion.Converter;
import com.noisyz.databindinglibrary.conversion.EmptyConverter;
import com.noisyz.databindinglibrary.conversion.TwoWayConverter;
import com.noisyz.databindinglibrary.wrappers.impl.fields.FieldPropertyViewWrapper;
import com.noisyz.databindinglibrary.wrappers.impl.methods.GetterPropertyViewWrapper;
import com.noisyz.databindinglibrary.wrappers.impl.methods.MethodPropertyViewWrapper;
import com.noisyz.databindinglibrary.wrappers.impl.view.AbsViewWrapper;
import com.noisyz.databindinglibrary.wrappers.impl.view.adapterviewwrapper.SimpleAdapterViewWrapper;
import com.noisyz.databindinglibrary.wrappers.impl.view.image.ImageViewWrapper;
import com.noisyz.databindinglibrary.wrappers.impl.view.simple.ChangebleRatingBarWrapper;
import com.noisyz.databindinglibrary.wrappers.impl.view.simple.CompoundButtonWrapper;
import com.noisyz.databindinglibrary.wrappers.impl.view.simple.FloatTextWrapper;
import com.noisyz.databindinglibrary.wrappers.impl.view.simple.ProgressViewWrapper;
import com.noisyz.databindinglibrary.wrappers.impl.view.simple.RatingBarWrapper;
import com.noisyz.databindinglibrary.wrappers.impl.view.simple.SeekBarWrapper;
import com.noisyz.databindinglibrary.wrappers.impl.view.simple.TextViewWrapper;
import com.noisyz.databindinglibrary.wrappers.impl.view.simple.VisibilityWrapper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by Oleg on 17.03.2016.
 */
public class WrapperViewFactory {

    public static PropertyViewWrapper getSimplePropertyViewWrapper(SimpleFieldType fieldType, View view, Object object, Field field) {
        PropertyViewWrapper propertyViewWrapper = new FieldPropertyViewWrapper(object, field);
        AbsViewWrapper viewWrapper = getViewWrapper(fieldType.value(), view, propertyViewWrapper);
        propertyViewWrapper.setViewWrapper(viewWrapper);
        setConversion(fieldType, propertyViewWrapper);
        return propertyViewWrapper;
    }


    public static PropertyViewWrapper getSimplePropertyViewWrapper(GetterMethod getterMethod, SetterMethod setterMethod,
                                                                   View view, Object object, Method getter, Method setter) {
        PropertyViewWrapper propertyViewWrapper = new MethodPropertyViewWrapper(object, getter, setter);
        AbsViewWrapper viewWrapper = getViewWrapper(getterMethod.value(), view, propertyViewWrapper);
        propertyViewWrapper.setViewWrapper(viewWrapper);
        setConversion(getterMethod, setterMethod, propertyViewWrapper);
        return propertyViewWrapper;
    }

    private static AbsViewWrapper getViewWrapper(type type, View view, ObjectBinder objectBinder) {
        AbsViewWrapper viewWrapper = null;
        try {
            switch (type) {
                case BOOLEAN:
                    if (view instanceof CompoundButton)
                        viewWrapper = new CompoundButtonWrapper((CompoundButton) view, objectBinder);
                    else viewWrapper = new VisibilityWrapper(view, objectBinder);
                    break;
                case TEXT:
                    viewWrapper = new TextViewWrapper((TextView) view, objectBinder);
                    break;
                case PROGRESS:
                    viewWrapper = new ProgressViewWrapper((ProgressBar) view, objectBinder);
                    break;
                case PROGRESS_CHANGEBLE:
                    viewWrapper = new SeekBarWrapper((SeekBar) view, objectBinder);
                    break;
                case RATING:
                    viewWrapper = new RatingBarWrapper((RatingBar) view, objectBinder);
                    break;
                case RATING_CHANGEBLE:
                    viewWrapper = new ChangebleRatingBarWrapper((RatingBar) view, objectBinder);
                    break;
                case VISIBILITY:
                    viewWrapper = new VisibilityWrapper(view, objectBinder);
                    break;
                case FLOAT_TEXT:
                    viewWrapper = new FloatTextWrapper((TextView) view, objectBinder);
                    break;
            }
        } catch (ClassCastException e) {
            e.printStackTrace();
            return null;
        }
        return viewWrapper;
    }

    private static void setConversion(SimpleFieldType fieldType, PropertyViewWrapper viewWrapper) {
        try {
            Conversion conversion = fieldType.twoWayConverter();
            TwoWayConverter converter = conversion.value().newInstance();
            if (!(converter instanceof EmptyConverter)) {
                viewWrapper.setUpdateUIConverter(converter.getConverterToUi());
                viewWrapper.setUpdateObjectConverter(converter.getConverterToObject());
            } else {
                ConvertToObject convertToObject = fieldType.convertToObject();
                if (convertToObject != null) {
                    Converter converterToObject = convertToObject.value().newInstance();
                    viewWrapper.setUpdateObjectConverter(converterToObject);
                }

                ConvertToUI convertToUI = fieldType.convertToUI();
                if (convertToUI != null) {
                    Converter converterToUI = convertToUI.value().newInstance();
                    viewWrapper.setUpdateUIConverter(converterToUI);
                }
            }

        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private static void setConversion(GetterMethod getterMethod, SetterMethod setterMethod, PropertyViewWrapper viewWrapper) {
        try {
            if (getterMethod != null) {
                ConvertToUI convertToUI = getterMethod.convertToUI();
                if (convertToUI != null) {
                    viewWrapper.setUpdateUIConverter(convertToUI.value().newInstance());
                }
            }
            if (setterMethod != null) {
                ConvertToObject convertToObject = setterMethod.convertToObject();
                if (convertToObject != null) {
                    viewWrapper.setUpdateObjectConverter(convertToObject.value().newInstance());
                }
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }


    }

    public static PropertyViewWrapper getImagePropertyViewWrapper(ImageField fieldType, View view, Object object, Field field) {
        try {
            PropertyViewWrapper propertyViewWrapper = new FieldPropertyViewWrapper(object, field);
            AbsViewWrapper viewWrapper = new ImageViewWrapper(fieldType.imageProvider().newInstance(), view);
            propertyViewWrapper.setViewWrapper(viewWrapper);
            return propertyViewWrapper;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static PropertyViewWrapper getImagePropertyViewWrapper(ImageGetterMethod fieldType, View view, Object object, Method method) {
        try {
            PropertyViewWrapper propertyViewWrapper = new GetterPropertyViewWrapper(object, method);
            AbsViewWrapper viewWrapper = new ImageViewWrapper(fieldType.imageProvider().newInstance(), view);
            propertyViewWrapper.setViewWrapper(viewWrapper);
            return propertyViewWrapper;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static PropertyViewWrapper getSimpleAdapterViewWrapper(SimpleAdapterViewField fieldType, View view, Object object, Field field) {
        int indent = fieldType.indent();
        String[] values = view.getContext().getResources().getStringArray(fieldType.resourceArray());
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(view.getContext(), fieldType.layoutResID(), values);
        ((AdapterView) view).setAdapter(spinnerArrayAdapter);
        PropertyViewWrapper fieldPropertyViewWrapper = new FieldPropertyViewWrapper(object, field);
        SimpleAdapterViewWrapper wrapper = new SimpleAdapterViewWrapper((AdapterView) view, fieldPropertyViewWrapper, indent);
        fieldPropertyViewWrapper.setViewWrapper(wrapper);
        return fieldPropertyViewWrapper;
    }

    public static PropertyViewWrapper getSimpleAdapterViewWrapper(SimpleAdapterViewGetter getterType,
                                                                  SimpleAdapterViewSetter setterType,
                                                                  AdapterView view, Object object, Method getter, Method setter) {

        int indent = 0, layoutResID = 0, resourceArray = 0, indentGetter = 0, indentSetter = 0,
                getterLayoutResId = 0, setterLayoutResId = 0, getterArray = 0, setterArray = 0;


        if (getterType != null) {
            indentGetter = getterType.indent();
            getterLayoutResId = getterType.layoutResID();
            getterArray = getterType.resourceArray();
        }
        if (setterType != null) {
            indentSetter = setterType.indent();
            setterLayoutResId = setterType.layoutResID();
            setterArray = setterType.resourceArray();
        }
        indent = indentGetter != 0 ? indentGetter : indentSetter != 0 ? indentSetter : 0;
        layoutResID = getterLayoutResId != 0 ? getterLayoutResId : setterLayoutResId != 0 ? setterLayoutResId : 0;
        resourceArray = getterArray != 0 ? getterArray : setterArray != 0 ? setterArray : 0;
        String[] values = view.getContext().getResources().getStringArray(resourceArray);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(view.getContext(), layoutResID, values);
        view.setAdapter(spinnerArrayAdapter);
        PropertyViewWrapper fieldPropertyViewWrapper = new MethodPropertyViewWrapper(object, getter, setter);
        SimpleAdapterViewWrapper wrapper = new SimpleAdapterViewWrapper((AdapterView) view, fieldPropertyViewWrapper, indent);
        fieldPropertyViewWrapper.setViewWrapper(wrapper);
        return fieldPropertyViewWrapper;
    }
}
