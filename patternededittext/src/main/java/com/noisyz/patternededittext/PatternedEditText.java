package com.noisyz.patternededittext;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;

/**
 * Created by Oleg on 14.03.2016.
 */
public class PatternedEditText extends EditText {

    private PatternTextWatcher patternTextWatcher;

    public PatternedEditText(Context context) {
        super(context);
    }

    public PatternedEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        loadAttributes(context, attrs);
    }

    public PatternedEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        loadAttributes(context, attrs);
    }

    public void setPattern(String pattern) {
        if (!TextUtils.isEmpty(pattern)) {
            if (patternTextWatcher != null) {
                removeTextChangedListener(patternTextWatcher);
            }
            patternTextWatcher = new PatternTextWatcher(this, pattern);
            addTextChangedListener(patternTextWatcher);
        }
    }

    public void setPattern(String pattern, String defaultChar) {
        if (!TextUtils.isEmpty(pattern)) {
            if (patternTextWatcher != null) {
                removeTextChangedListener(patternTextWatcher);
            }
            patternTextWatcher = new PatternTextWatcher(this, pattern, defaultChar);
            addTextChangedListener(patternTextWatcher);
        }
    }

    public void setTextValidationListener(TextValidationListener listener){
        if(patternTextWatcher!=null){
            patternTextWatcher.setTextValidationListener(listener);
        }
    }

    private void loadAttributes(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.PatternedEditText, 0, 0);
        String pattern = array.getString(R.styleable.PatternedEditText_pattern);
        String defaultChar = array.getString(R.styleable.PatternedEditText_default_char);
        if (!TextUtils.isEmpty(pattern) && !TextUtils.isEmpty(defaultChar)) {
            setPattern(pattern, defaultChar);
        } else if (!TextUtils.isEmpty(pattern)) {
            setPattern(pattern);
        }
        array.recycle();
    }

    public interface TextValidationListener {
        void onTextChanged(boolean isValid);
    }
}
