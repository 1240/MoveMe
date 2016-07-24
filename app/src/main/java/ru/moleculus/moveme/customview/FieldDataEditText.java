package ru.moleculus.moveme.customview;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.noisyz.patternededittext.PatternedEditText;

import ru.moleculus.moveme.R;

/**
 * Created by Oleg on 21.03.2016.
 */
public class FieldDataEditText extends PatternedEditText implements View.OnFocusChangeListener, View.OnClickListener {


    private static boolean isNeverClicked = true;

    public FieldDataEditText(Context context) {
        super(context);
        init();
    }

    public FieldDataEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FieldDataEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setCursorVisible(false);
        setBackgroundDrawable(getResources().getDrawable(R.drawable.default_item_selector));
        setTextColor(Color.BLACK);
        setOnFocusChangeListener(this);
        setOnClickListener(this);
    }

    @Override
    public void onAttachedToWindow(){
        super.onAttachedToWindow();
        isNeverClicked = true;
    }

    @Override
    public void onDetachedFromWindow(){
        super.onDetachedFromWindow();
        isNeverClicked = true;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
       setCursorVisible(hasFocus&&!isNeverClicked);
    }

    @Override
    public void onClick(View v) {
        isNeverClicked = false;
        setCursorVisible(true);
    }
}
