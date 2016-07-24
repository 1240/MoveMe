package ru.moleculus.moveme.data.beans;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;
import com.noisyz.customeelements.utils.SimpleTextUtils;
import com.noisyz.databindinglibrary.annotations.field.SimpleFieldType;
import com.noisyz.databindinglibrary.annotations.type;
import com.noisyz.databindinglibrary.utils.ReflectionUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;

import ru.moleculus.moveme.BaseConstants;

/**
 * Created by Oleg on 11.02.2016.
 */
public class Location implements Serializable, BaseConstants, BaseMoveMeObject{

    private String address;

    @SimpleFieldType(type.TEXT)
    private String comment, contact_name, contact_phone;

    private double lat = INVALID_LOCATION;
    @SerializedName("long")
    private double lon = INVALID_LOCATION;

    public Location(){}

    public void setLongitude(double lon){
        this.lon = lon;
    }

    public void setLatitude(double lat){
        this.lat = lat;
    }

    public double getLatitude(){
        return lat;
    }

    public double getLongitude(){
        return lon;
    }

    public String getAddress(){
        return address;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public String getContactPhone(){
        return contact_phone;
    }

    public String getContactName(){
        return contact_name;
    }

    public void setComment(String comment){
        this.comment = comment;
    }

    public void setContactName(String contact_name){
        this.contact_name = contact_name;
    }

    public void setContactPhone(String contactPhone){
        this.contact_phone = contactPhone;
    }

    @Override
    public boolean isObjectValid() {
        boolean isValid = true;
        isValid&=!TextUtils.isEmpty(address);
        isValid&=!TextUtils.isEmpty(contact_phone);
        isValid&=!TextUtils.isEmpty(contact_name);
        isValid&=(lat!=INVALID_LOCATION);
        isValid&=(lon!=INVALID_LOCATION);
        return isValid;
    }

    @Override
    public HashMap<String, String> getRequestHashMap(int objectIndex) {
        HashMap<String, String> hashMap = new HashMap<>();
        for (Field field : getClass().getDeclaredFields()) {
            String name = field.getName();
            if(name.equals("lon"))
                name="long";
            String value = String.valueOf(ReflectionUtils.getVariableValue(field, this));
            if(!SimpleTextUtils.isFieldEmpty(value)) {
                hashMap.put("locations[" + objectIndex + "][" + name + "]", value);
            }
        }
        return hashMap;
    }
}
