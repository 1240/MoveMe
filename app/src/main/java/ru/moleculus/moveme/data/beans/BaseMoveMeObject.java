package ru.moleculus.moveme.data.beans;

import java.util.HashMap;

/**
 * Created by Oleg on 25.03.2016.
 */
public interface BaseMoveMeObject {

    boolean isObjectValid();

    HashMap<String, String> getRequestHashMap(int objectIndex);
}
