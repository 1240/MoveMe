package com.noisyz.databindinglibrary.wrappers.impl.view.simple;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.TextView;

import com.noisyz.databindinglibrary.wrappers.ObjectBinder;
import com.noisyz.databindinglibrary.wrappers.impl.view.AbsViewWrapper;

/**
 * Created by Oleg on 17.03.2016.
 */
public class TextViewWrapper extends AbsViewWrapper<TextView> {

    private String text;

    private SimpleTextWatcher textWatcher = new SimpleTextWatcher() {
        @Override
        void onTextChanged(CharSequence text) {
            TextViewWrapper.this.text = text.toString();
            bindObject();
        }
    };

    public TextViewWrapper(TextView textView, ObjectBinder objectBinder) {
        super(textView, objectBinder);
    }

    @Override
    public void bindUI(Object object) {
        getView().removeTextChangedListener(textWatcher);
        if (object instanceof Integer) {
            getView().setText(Integer.valueOf(object.toString()));
        } else if (object instanceof String) {
            String value = String.valueOf(object);
            if (!TextUtils.isEmpty(value) && !value.equals("null"))
                getView().setText(value);
            getView().setText(object.toString());
        }
        getView().addTextChangedListener(textWatcher);
    }

    @Override
    public Object getViewValue() {
        return text.trim();
    }


    public abstract class SimpleTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            onTextChanged(getView().getText());
        }

        abstract void onTextChanged(CharSequence text);
    }
}
