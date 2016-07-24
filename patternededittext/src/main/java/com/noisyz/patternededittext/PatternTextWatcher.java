package com.noisyz.patternededittext;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Oleg on 14.03.2016.
 */
public class PatternTextWatcher implements TextWatcher, PatternConstants {
    private ArrayList<PatternChar> patternChars;
    private String patternedText;
    private int patternLength;
    private EditText outputView;
    private PatternedEditText.TextValidationListener textValidationListener;

    public PatternTextWatcher(EditText outputView, String pattern) {
        this(outputView, pattern, DEFAULT_CHAR);
    }

    public PatternTextWatcher(EditText outputView, String pattern, String default_char) {
        patternChars = PatternUtils.createPatternCollection(pattern, default_char);
        this.outputView = outputView;
        this.patternLength = pattern.length();
        initStartTextValue(pattern, default_char);
    }

    private void initStartTextValue(String pattern, String default_char) {
        int index = pattern.indexOf(default_char);
        outputView.setText(pattern.substring(0, index));
    }

    public void setTextValidationListener(PatternedEditText.TextValidationListener listener) {
        this.textValidationListener = listener;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String patternedText = PatternUtils.makeStringPatterned(s, patternChars);
        if (!TextUtils.equals(patternedText, s) &&
                !TextUtils.equals(patternedText, this.patternedText) &&
                patternedText.length() < patternLength) {
            outputView.setText(patternedText);
            this.patternedText = patternedText;
        }
        if (s.length() > patternLength) {
            outputView.setText(s.toString().substring(0, patternLength));
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        outputView.setSelection(outputView.getText().length());
        if (textValidationListener != null) {
            textValidationListener.onTextChanged(outputView.getText().length() == patternLength);
        }
    }
}
