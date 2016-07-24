package ru.moleculus.moveme.ui.fragments.orders;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.moleculus.moveme.R;
import ru.moleculus.moveme.callbacks.BaseRequestCallback;
import ru.moleculus.moveme.data.SharedManager;
import ru.moleculus.moveme.interactors.impl.RequestInteractorImpl;
import ru.moleculus.moveme.net.ApiService;
import ru.moleculus.moveme.net.beans.moveme.BaseMoveMeResponse;
import ru.moleculus.moveme.ui.fragments.BaseFragment;

/**
 * Created by Oleg on 25.03.2016.
 */
public class CreateTemplateFragment extends BaseFragment implements BaseRequestCallback<BaseMoveMeResponse>, View.OnClickListener {

    private long id;

    public CreateTemplateFragment() {
    }

    public static CreateTemplateFragment newInstance(long id) {
        Bundle arguments = new Bundle();
        arguments.putLong(EXTRA_ID, id);
        CreateTemplateFragment fragment = new CreateTemplateFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getLong(EXTRA_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle onSavedInstanceState) {
        View view = inflater.inflate(R.layout.save_template_fragment, null);
        view.findViewById(R.id.btn_save_template).setOnClickListener(this);
        view.findViewById(R.id.btn_cancel_template).setOnClickListener(this);
        return view;
    }

    @Override
    public void onRequestError(String message) {
        showErrorMessage(message);
    }

    @Override
    public void onRequestSuccess(BaseMoveMeResponse response) {
        getActivity().finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save_template:
                String token = SharedManager.getToken(getActivity());
                new RequestInteractorImpl().makeRequest(ApiService
                        .getInstance().getMoveMeApi().saveOrderAsTemplate(id, token), this);
                break;
            case R.id.btn_cancel_template:
                getActivity().finish();
                break;
        }
    }
}
