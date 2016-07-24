package ru.moleculus.moveme.ui.fragments.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.noisyz.customeelements.dialog.AlertDialogFactory;
import com.noisyz.customeelements.dialog.SetDateDialog;
import com.noisyz.customeelements.dialog.SetDateDialogListener;
import com.noisyz.customeelements.utils.SimpleAnimationUtils;
import com.noisyz.databindinglibrary.bind.base.impl.ObjectDataBinder;
import com.noisyz.databindinglibrary.bind.base.UIBinder;

import java.lang.reflect.Field;

import ru.moleculus.moveme.R;
import ru.moleculus.moveme.data.beans.Passport;
import ru.moleculus.moveme.data.beans.User;
import ru.moleculus.moveme.net.ApiConst;

/**
 * Created by Oleg on 05.03.2016.
 */
public class PassportFragment extends AbsUserFragment implements View.OnClickListener {

    private SetDateDialog dialog;
    private Passport passport;

    private SetDateDialogListener listener = new SetDateDialogListener() {
        @Override
        public void fillTextField(int fieldId, long time) {
            ((TextView) getView().findViewById(fieldId)).
                    setText(ApiConst.DATE_FORMAT_USER_FRIENDLY.format(time));
        }
    };

    public PassportFragment() {
    }

    public static PassportFragment newInstance() {
        return new PassportFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View parentView = inflater.inflate(R.layout.set_passport_data_fragment, null);
        dialog = AlertDialogFactory.getSetDateDialog(getActivity(), listener);
        parentView.findViewById(R.id.btn_save_data).setOnClickListener(this);
        parentView.findViewById(R.id.btn_save_data).setEnabled(false);
        return parentView;
    }

    @Override
    public void showUserData(User user) {
        hideProgressView();
        if (user.getPassports().isEmpty()) {
            passport = new Passport();
        } else {
            passport = user.getPassports().get(0);
        }
        fillData();
        getDataBinder().newBinder("passport",
                new ObjectDataBinder<Passport>(passport).registerView(this));
        super.showUserData(user);
    }

    private void fillData() {
        getView().findViewById(R.id.ed_pass_given_date).setOnClickListener(this);
        getView().findViewById(R.id.ed_user_birth_date).setOnClickListener(this);
        getView().findViewById(R.id.ed_user_registration_date).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_save_data) {
            SimpleAnimationUtils.fadeOut(getView().findViewById(R.id.btn_save_data), ANIMATION_DURATION);
            showProgressView();
            sendPassportData();
        } else {
            dialog.show(v.getId());
        }
    }

    @Override
    public void sendPassportData() {
        getPresenter().addPassportData(passport);
    }

    @Override
    public void onDataUpdated(UIBinder uiBinder, Object object, String propertyName, Object value) {
        getView().findViewById(R.id.btn_save_data).setEnabled(true);
    }
}
