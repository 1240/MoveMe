package com.noisyz.databindinglibrary.wrappers.impl.methods;

import com.noisyz.databindinglibrary.utils.ReflectionUtils;
import com.noisyz.databindinglibrary.wrappers.PropertyViewWrapper;
import com.noisyz.databindinglibrary.wrappers.impl.view.AbsViewWrapper;

import java.lang.reflect.Method;

/**
 * Created by Oleg on 17.03.2016.
 */
public class SetterPropertyViewWrapper<V extends AbsViewWrapper> extends MethodPropertyViewWrapper<V> {

    public SetterPropertyViewWrapper(Object object, Method method) {
        super(object, method, SETTER);
    }
}
