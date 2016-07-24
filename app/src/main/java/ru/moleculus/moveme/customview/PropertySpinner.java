package ru.moleculus.moveme.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Spinner;

/**
 * Created by Oleg on 31.03.2016.
 */
public class PropertySpinner extends Spinner {

    public PropertySpinner(Context context) {
        super(context);
        init();
    }

    public PropertySpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PropertySpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
    }

    @Override
    protected void onAttachedToWindow(){
        super.onAttachedToWindow();
        performClick();
    }
}

