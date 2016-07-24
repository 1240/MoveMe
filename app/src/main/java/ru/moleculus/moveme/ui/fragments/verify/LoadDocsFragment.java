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
public class LoadDocsFragment extends BaseFragment implements View.OnClickListener{


    @SimpleFieldType(type.BOOLEAN)
    private boolean isFirstPageLoaded, isRegistrationPageLoaded;

    public LoadDocsFragment(){}

    public static LoadDocsFragment newInstance(){
        return new LoadDocsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.load_docs_fragment, null);
        view.findViewById(R.id.btn_send_docs).setOnClickListener(this);
        view.findViewById(R.id.first_page).setOnClickListener(this);
        view.findViewById(R.id.registration_page).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_send_docs:
                break;
        }
    }
}
