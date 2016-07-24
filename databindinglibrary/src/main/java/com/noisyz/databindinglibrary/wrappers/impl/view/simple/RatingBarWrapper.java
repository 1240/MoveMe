package com.noisyz.databindinglibrary.wrappers.impl.view.simple;

import android.widget.RatingBar;

import com.noisyz.databindinglibrary.wrappers.ObjectBinder;
import com.noisyz.databindinglibrary.wrappers.impl.view.AbsViewWrapper;

/**
 * Created by Oleg on 18.03.2016.
 */
public class RatingBarWrapper extends AbsViewWrapper<RatingBar> {
    public RatingBarWrapper(RatingBar ratingBar, ObjectBinder objectBinder) {
        super(ratingBar, objectBinder);
    }

    @Override
    public void bindUI(Object object) {
        if (object != null) {
            getView().setRating((Float) object);
        }
    }

    @Override
    public Object getViewValue() {
        return getView().getRating();
    }
}
