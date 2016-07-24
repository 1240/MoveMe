package com.noisyz.databindinglibrary.wrappers.impl.view.image;

import android.view.View;

import com.noisyz.databindinglibrary.callback.imageproperty.ImageProvider;
import com.noisyz.databindinglibrary.wrappers.impl.view.AbsViewWrapper;

/**
 * Created by Oleg on 18.03.2016.
 */
public class ImageViewWrapper extends AbsViewWrapper<View> {

    private ImageProvider provider;

    public ImageViewWrapper(ImageProvider provider, View imageView) {
        super(imageView, null);
        this.provider = provider;
    }

    @Override
    public void bindUI(Object value) {
        if (value != null && provider != null) {
            provider.provideData(getView(), value);
        }
    }

    @Override
    public Object getViewValue() {
        return null;
    }
}
