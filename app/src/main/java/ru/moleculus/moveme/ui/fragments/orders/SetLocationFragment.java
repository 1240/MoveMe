package ru.moleculus.moveme.ui.fragments.orders;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.noisyz.databindinglibrary.annotations.field.SimpleFieldType;
import com.noisyz.databindinglibrary.annotations.type;
import com.noisyz.databindinglibrary.bind.BindingManager;
import com.noisyz.databindinglibrary.bind.base.impl.ObjectDataBinder;
import com.noisyz.databindinglibrary.bind.base.UIBinder;
import com.noisyz.databindinglibrary.callback.DataUpdatedCallback;

import java.lang.reflect.Field;

import ru.moleculus.moveme.R;
import ru.moleculus.moveme.data.beans.Location;
import ru.moleculus.moveme.presenters.LocationViewPresenter;
import ru.moleculus.moveme.presenters.impl.LocationViewPresenterImpl;
import ru.moleculus.moveme.ui.activity.SetLocationOnMapActivity;
import ru.moleculus.moveme.ui.fragments.BaseFragment;
import ru.moleculus.moveme.view.LocationView;

/**
 * Created by Oleg on 24.03.2016.
 */
public class SetLocationFragment extends BaseFragment implements LocationView, View.OnClickListener, DataUpdatedCallback {

    private LocationViewPresenter presenter;

    private BindingManager bindingManager;

    private Location location;

    @SimpleFieldType(type.TEXT)
    private String city, street, house, block, block_number;

    public SetLocationFragment() {
    }

    public static SetLocationFragment newInstance(Location location) {
        Bundle arguments = new Bundle();
        arguments.putSerializable(EXTRA_LOCATION, location);
        SetLocationFragment fragment = new SetLocationFragment();
        fragment.setArguments(arguments);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            location = (Location) getArguments().getSerializable(EXTRA_LOCATION);
            if (location == null) {
                location = new Location();
            } else {
                fillAddressFields(location.getAddress());
            }
        }
        bindingManager = BindingManager.newInstance().setDataUpdatedCallback(this);
        presenter = new LocationViewPresenterImpl(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_location_fragment, null);
        view.findViewById(R.id.btn_address_save).setOnClickListener(this);
        view.findViewById(R.id.btn_address_save).setEnabled(false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindingManager.newBinder("Address",
                new ObjectDataBinder<>(this).registerView(this)).
                newBinder("location", new ObjectDataBinder<>(location).registerView(this)
                ).bindUI();
    }

    private void fillAddressFields(String address) {
        try {
            if (!TextUtils.isEmpty(address)) {
                String[] fields = address.split("\\,");
                city = fields[0];
                street = fields[1];
                house = fields[2];
                block = fields[3];
                block_number = fields[4];
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    private void combineAddress() {
        String address = "";
        if (!TextUtils.isEmpty(city)) {
            address += (city);
        }
        if (!TextUtils.isEmpty(street)) {
            address += (", " + street);
        }

        if (!TextUtils.isEmpty(house)) {
            address += (", " + house);
        }

        if (!TextUtils.isEmpty(block)) {
            address += (", " + block);
        }

        if (!TextUtils.isEmpty(block_number)) {
            address += (", " + block_number);
        }
        requestCoordinates(address);
    }

    @Override
    public void requestCoordinates(String address) {
        presenter.loadLocation(address);
    }

    @Override
    public void setLatLng(String address, double latitude, double longitude) {
        location.setAddress(address);
        Intent intent = new Intent(getContext(), SetLocationOnMapActivity.class);
        intent.putExtra(EXTRA_LATITUDE, latitude);
        intent.putExtra(EXTRA_LONGITUDE, longitude);
        startActivityForResult(intent, MAP_REQUEST_CODE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_address_save:
                combineAddress();
                break;
        }
    }

    private boolean isValid() {
        boolean isValid = true;
        isValid &= !TextUtils.isEmpty(city);
        isValid &= !TextUtils.isEmpty(street);
        isValid &= !TextUtils.isEmpty(house);
        isValid &= !TextUtils.isEmpty(location.getContactName());
        isValid &= !TextUtils.isEmpty(location.getContactPhone());
        return isValid;
    }

    @Override
    public void onDataUpdated(UIBinder uiBinder, Object object, String propertyName, Object value) {
        if(getView()!=null){
            getView().findViewById(R.id.btn_address_save).setEnabled(isValid());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        handleMapOnActivityResult(requestCode, resultCode, data);
    }

    private void handleMapOnActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MAP_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            location.setLatitude(data.getExtras().getDouble(EXTRA_LATITUDE));
            location.setLongitude(data.getExtras().getDouble(EXTRA_LONGITUDE));
            Intent intent = new Intent();
            intent.putExtra(EXTRA_LOCATION, location);
            getActivity().setResult(Activity.RESULT_OK, intent);
            getActivity().finish();
        }
    }
}
