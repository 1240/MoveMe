package com.noisyz.databindinglibrary.callback.imageproperty;

/**
 * Created by Oleg on 18.03.2016.
 */
public interface BitmapLoader<T extends Object> {

    void loadBitmap(T t);

}
