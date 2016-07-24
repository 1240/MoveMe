package ru.moleculus.moveme.net;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import com.noisyz.customeelements.utils.SimpleTextUtils;
import ru.moleculus.moveme.data.beans.Location;

/**
 * Created by Oleg on 11.02.2016.
 */
public class LocationDeserializer implements JsonDeserializer<Location>{
    @Override
    public Location deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject object = json.getAsJsonObject();
        Location location = new Location();
        if (object.has("address")) {
            location.setAddress(object.get("address").getAsString());
        }
        if (object.has("comment")) {
            location.setComment(object.get("comment").getAsString());
        }
        if (object.has("contact_name")) {
            location.setContactName(object.get("contact_name").getAsString());
        }
        if (object.has("contact_phone")) {
            location.setContactPhone(object.get("contact_phone").getAsString());
        }
        if (object.has("lat") && !SimpleTextUtils.isFieldEmpty(object.get("lat").getAsString())) {
            location.setLatitude(object.get("lat").getAsDouble());
        }
        if (object.has("long") && !SimpleTextUtils.isFieldEmpty(object.get("long").getAsString())) {
            location.setLongitude(object.get("long").getAsDouble());
        }
        return location;
    }
}
