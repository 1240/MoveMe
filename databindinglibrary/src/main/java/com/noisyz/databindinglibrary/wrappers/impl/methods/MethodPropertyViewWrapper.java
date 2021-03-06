package com.noisyz.databindinglibrary.wrappers.impl.methods;

import com.noisyz.databindinglibrary.utils.ReflectionUtils;
import com.noisyz.databindinglibrary.wrappers.PropertyViewWrapper;
import com.noisyz.databindinglibrary.wrappers.impl.view.AbsViewWrapper;

import java.lang.reflect.Method;

/**
 * Created by Oleg on 17.03.2016.
 */
public class MethodPropertyViewWrapper<V extends AbsViewWrapper> extends PropertyViewWrapper<V> {

    public static final int GETTER = 0;
    public static final int SETTER = 1;
    private Method setter, getter;

    public MethodPropertyViewWrapper(Object object, Method getter, Method setter) {
        super(object);
        this.getter = getter;
        this.setter = setter;
    }

    public MethodPropertyViewWrapper(Object object, Method method, int mode) {
        super(object);
        switch (mode) {
            case GETTER:
                getter = method;
                break;
            case SETTER:
                setter = method;
                break;
        }
    }

    @Override
    protected Object getUIBindPropertyValue() {
        return getter != null ? ReflectionUtils.invokeGetterMethod(getter, getObject()) : null;
    }

    @Override
    protected void updateObjectByValue(Object value) {
        if (setter != null)
            ReflectionUtils.invokeSetterMethod(setter, getObject(), value);
    }
}
