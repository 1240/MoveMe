package ru.moleculus.moveme.net;

import android.util.Log;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import com.noisyz.customeelements.utils.SimpleTextUtils;

/**
 * Created by Oleg on 14.02.2016.
 */
public class CustomDeserializer<T extends Object> implements JsonDeserializer<T> {
    @Override
    public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (SimpleTextUtils.isFieldEmpty(json.getAsString()))
            return null;
        else return context.deserialize(json, typeOfT);
    }
}
