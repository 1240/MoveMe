package ru.moleculus.moveme.ui.fragments.verify;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.moleculus.moveme.R;
import ru.moleculus.moveme.ui.activity.LoadDocsActivity;
import ru.moleculus.moveme.ui.activity.SendVerifyCodeActivity;
import ru.moleculus.moveme.ui.fragments.BaseFragment;

/**
 * Created by Oleg on 04.04.2016.
 */
public class VerifyMainFragment extends BaseFragment implements View.OnClickListener{

    public VerifyMainFragment(){}

    public static VerifyMainFragment newInstance(){
        return new VerifyMainFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.verify_main_fragment, null);
        view.findViewById(R.id.btn_load_docs).setOnClickListener(this);
        view.findViewById(R.id.btn_enter_code).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()){
            case R.id.btn_load_docs:
                intent = new Intent(getContext(), LoadDocsActivity.class);
                break;
            case R.id.btn_enter_code:
                intent = new Intent(getContext(), SendVerifyCodeActivity.class);
                break;
        }
        startActivity(intent);
    }
}
