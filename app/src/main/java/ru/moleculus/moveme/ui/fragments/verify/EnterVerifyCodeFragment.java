package ru.moleculus.moveme.ui.fragments.verify;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.noisyz.databindinglibrary.annotations.field.SimpleFieldType;
import com.noisyz.databindinglibrary.annotations.type;

import ru.moleculus.moveme.R;
import ru.moleculus.moveme.ui.fragments.BaseFragment;

/**
 * Created by Oleg on 04.04.2016.
 */
public class EnterVerifyCodeFragment extends BaseFragment implements View.OnClickListener{


    @SimpleFieldType(type.TEXT)
    private String verifyCode;

    public EnterVerifyCodeFragment(){}

    public static EnterVerifyCodeFragment newInstance(){
        return new EnterVerifyCodeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.send_verify_code_layout, null);
        view.findViewById(R.id.btn_submit).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_submit:
                break;
        }
    }
}
