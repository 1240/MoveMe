package com.noisyz.databindinglibrary.annotations.methods;

import com.noisyz.databindinglibrary.annotations.converters.ConvertToUI;
import com.noisyz.databindinglibrary.annotations.type;
import com.noisyz.databindinglibrary.conversion.EmptyConverter;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Oleg on 17.03.2016.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface GetterMethod {

    type value();

    String propertyKey();

    ConvertToUI convertToUI() default @ConvertToUI(EmptyConverter.class);

}
