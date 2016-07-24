package ru.moleculus.moveme.ui.fragments.orders;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edmodo.rangebar.RangeBar;
import com.noisyz.customeelements.dialog.AlertDialogFactory;
import com.noisyz.customeelements.utils.SimpleImageUtils;
import com.noisyz.databindinglibrary.annotations.field.SimpleFieldType;
import com.noisyz.databindinglibrary.annotations.type;
import com.noisyz.databindinglibrary.bind.base.impl.ObjectDataBinder;
import com.noisyz.databindinglibrary.bind.base.UIBinder;
import com.noisyz.databindinglibrary.callback.DataUpdatedCallback;

import java.io.File;
import java.lang.reflect.Field;

import ru.moleculus.moveme.R;
import ru.moleculus.moveme.data.beans.Cargo;
import ru.moleculus.moveme.ui.fragments.user.AbsUserFragment;

/**
 * Created by Oleg on 24.03.2016.
 */
public class SetCargoFragment extends AbsUserFragment implements View.OnClickListener, DataUpdatedCallback, RangeBar.OnRangeBarChangeListener {

    private DataUpdatedCallback<SetCargoFragment, Boolean> switchDataUpdatedCallback =
            new DataUpdatedCallback<SetCargoFragment, Boolean>() {
                @Override
                public void onDataUpdated(UIBinder uiBinder, SetCargoFragment object, String propertyName, Boolean value) {
                    getDataBinder().bindUI();
                }
            };

    private Cargo cargo;

    @SimpleFieldType(type.BOOLEAN)
    private boolean refrigerator, insurance;

    public SetCargoFragment() {
    }

    public static SetCargoFragment newInstance(Cargo cargo) {
        Bundle arguments = new Bundle();
        arguments.putSerializable(EXTRA_CARGO, cargo);
        SetCargoFragment fragment = new SetCargoFragment();
        fragment.setArguments(arguments);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            cargo = (Cargo) getArguments().getSerializable(EXTRA_CARGO);
            if (cargo == null) {
                cargo = new Cargo();
            }
            refrigerator = (cargo.getTemperatureFrom() != MIN_TEMPERATURE)
                    && (cargo.getTemperatureTo() != MAX_TEMPERATURE);
            insurance = !TextUtils.isEmpty(cargo.getPrice());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_cargo_fragment, null);
        view.findViewById(R.id.btn_cargo_save).setOnClickListener(this);
        view.findViewById(R.id.cargo_pic).setOnClickListener(this);
        RangeBar rangeBar = (RangeBar) view.findViewById(R.id.range_seek_bar);
        rangeBar.setTickCount(62);
        rangeBar.setThumbIndices(cargo.getTemperatureFrom() + 30, cargo.getTemperatureTo() + 30);
        rangeBar.setOnRangeBarChangeListener(this);
        view.findViewById(R.id.btn_cargo_save).setEnabled(false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDataBinder().newBinder("fields", new ObjectDataBinder<>(this).registerView(this).
                setDataUpdatedCallback(switchDataUpdatedCallback)).
                newBinder("cargo", new ObjectDataBinder<>(cargo).registerView(this));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cargo_save:
                Intent intent = new Intent();
                intent.putExtra(EXTRA_CARGO, cargo);
                getActivity().setResult(Activity.RESULT_OK, intent);
                getActivity().finish();
                break;
            case R.id.cargo_pic:
                AlertDialogFactory.initDialogPickImage(this).show();
                break;
        }
    }


    @Override
    public void onDataUpdated(UIBinder uiBinder, Object object, String propertyName, Object value) {
        if (getView() != null) {
            getView().findViewById(R.id.btn_cargo_save).setEnabled(cargo.isObjectValid());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        SimpleImageUtils.handleOnImagePickActivityResult(getContext(), requestCode, resultCode, data,
                new SimpleImageUtils.OnImagePickedCallback() {
                    @Override
                    public void onImagePicked(File file) {
                        sendUserFile(file);
                    }
                });
    }

    @Override
    public void onFileSent(String url) {
        cargo.setPic(url);
        getDataBinder().bindUI();
    }

    @Override
    public void onIndexChangeListener(RangeBar rangeBar, int left, int right) {
        int leftCel = left - 30;
        int rightCel = right - 30;
        cargo.setTemperatureFrom(leftCel);
        cargo.setTemperatureTo(rightCel);
        getDataBinder().bindUI();
    }
}
