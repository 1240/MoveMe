package ru.moleculus.moveme.data.beans;

import android.text.TextUtils;

import com.noisyz.customeelements.utils.SimpleTextUtils;
import com.noisyz.databindinglibrary.annotations.converters.Conversion;
import com.noisyz.databindinglibrary.annotations.field.ImageField;
import com.noisyz.databindinglibrary.annotations.field.SimpleAdapterViewField;
import com.noisyz.databindinglibrary.annotations.field.SimpleFieldType;
import com.noisyz.databindinglibrary.annotations.type;
import com.noisyz.databindinglibrary.utils.ReflectionUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;

import ru.moleculus.moveme.BaseConstants;
import ru.moleculus.moveme.R;
import ru.moleculus.moveme.customview.MoveMeImageProvider;
import ru.moleculus.moveme.ui.fragments.orders.converters.TemperatureConverter;

/**
 * Created by Oleg on 11.02.2016.
 */
public class Cargo implements BaseConstants, Serializable, BaseMoveMeObject {
    @ImageField(imageProvider = MoveMeImageProvider.class)
    private String pic;

    @SimpleFieldType(type.TEXT)
    private String title, weight, price;

    @SimpleFieldType(type.FLOAT_TEXT)
    private String size_x, size_y, size_z;

    @SimpleFieldType(value = type.TEXT, twoWayConverter = @Conversion(value = TemperatureConverter.class))
    private int temperature_from = MIN_TEMPERATURE, temperature_to = MAX_TEMPERATURE;

    @SimpleAdapterViewField(resourceArray = R.array.packaging_type, layoutResID = R.layout.list_item)
    private int packing_type = -1;

    public int getPackingType() {
        return packing_type;
    }

    public String getWeight() {
        return weight;
    }

    public int getTemperatureFrom() {
        return temperature_from;
    }

    public int getTemperatureTo() {
        return temperature_to;
    }

    public String getPic() {
        return pic;
    }

    public String getTitle() {
        return title;
    }

    public String getSizeX() {
        return size_x;
    }

    public String getSizeY() {
        return size_y;
    }

    public String getSizeZ() {
        return size_z;
    }

    public String getPrice() {
        return price;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public void setTemperatureFrom(int temperature_from) {
        this.temperature_from = temperature_from;
    }

    public void setTemperatureTo(int temperature_to) {
        this.temperature_to = temperature_to;
    }

    public String toString(int index) {
        StringBuilder builder = new StringBuilder();
        for (Field field : getClass().getDeclaredFields()) {
            builder.append(PARAMS_DIVIDER + "cargo[" + index + "][" + field.getName() + "]" +
                    PARAMS_EQ + ReflectionUtils.getVariableValue(field, this));
        }
        return builder.toString();
    }

    @Override
    public boolean isObjectValid() {
        boolean isValid = true;
        isValid &= !TextUtils.isEmpty(title);
        isValid &= !TextUtils.isEmpty(weight);
        isValid &= !TextUtils.isEmpty(size_x);
        isValid &= !TextUtils.isEmpty(size_y);
        isValid &= !TextUtils.isEmpty(size_z);
        isValid &= (packing_type != -1);
        return isValid;
    }

    @Override
    public HashMap<String, String> getRequestHashMap(int objectIndex) {
        HashMap<String, String> hashMap = new HashMap<>();
        for (Field field : getClass().getDeclaredFields()) {
            String value = String.valueOf(ReflectionUtils.getVariableValue(field, this));
            if (!SimpleTextUtils.isFieldEmpty(value)) {
                hashMap.put("cargo[" + objectIndex + "][" + field.getName() + "]", value);
            }
        }
        return hashMap;
    }
}
