package com.noisyz.databindinglibrary.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Oleg on 17.03.2016.
 */
public class ReflectionUtils {


    public static void invokeSetterMethod(Method method, Object object, Object value){
        try {
            method.setAccessible(true);
            if (method.isAccessible()) {
                method.invoke(object);
            }

        } catch (SecurityException | IllegalArgumentException | InvocationTargetException | IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static Object invokeGetterMethod(Method method, Object object) {

        Object result = null;
        try {
            method.setAccessible(true);
            if (method.isAccessible()) {
                result = method.invoke(object);
            }

        } catch (SecurityException | IllegalArgumentException | InvocationTargetException | IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    public static Object getVariableValue(Field f, Object object) {

        Object result = null;
        try {
            f.setAccessible(true);
            if (f.isAccessible()) {
                result = f.get(object);
            }

        } catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    public static void setVariableValue(Field f, Object parentObject, Object value) {
        try {
            f.set(parentObject, value);
        } catch (IllegalAccessException | SecurityException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }


}