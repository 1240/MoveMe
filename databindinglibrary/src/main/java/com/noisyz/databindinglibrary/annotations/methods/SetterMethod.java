package com.noisyz.databindinglibrary.annotations.methods;

import com.noisyz.databindinglibrary.annotations.converters.ConvertToObject;
import com.noisyz.databindinglibrary.annotations.type;
import com.noisyz.databindinglibrary.conversion.EmptyConverter;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Oleg on 17.03.2016.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface SetterMethod {
    type value();

    String propertyKey();

    ConvertToObject convertToObject() default @ConvertToObject(EmptyConverter.class);
}
