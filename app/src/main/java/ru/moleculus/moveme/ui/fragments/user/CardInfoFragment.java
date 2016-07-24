package ru.moleculus.moveme.ui.fragments.user;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.noisyz.customeelements.dialog.AlertDialogFactory;
import com.noisyz.databindinglibrary.annotations.field.SimpleFieldType;
import com.noisyz.databindinglibrary.annotations.type;
import com.noisyz.databindinglibrary.bind.base.impl.ObjectDataBinder;
import com.noisyz.databindinglibrary.bind.base.UIBinder;

import java.lang.reflect.Field;

import ru.moleculus.moveme.R;
import ru.moleculus.moveme.data.beans.PaymentMethod;
import ru.moleculus.moveme.data.beans.User;

/**
 * Created by Oleg on 05.03.2016.
 */
public class CardInfoFragment extends AbsUserFragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    @SimpleFieldType(type.TEXT)
    private String summ = "0";
    private PaymentMethod method;

    public CardInfoFragment() {
    }

    public static CardInfoFragment newInstance() {
        return new CardInfoFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View parentView = inflater.inflate(R.layout.set_card_data_fragment, null);
        parentView.findViewById(R.id.btn_save_data).setOnClickListener(this);
        getDataBinder().newBinder("editSumm", new ObjectDataBinder<>(this).
                registerView(parentView));
        parentView.findViewById(R.id.btn_save_data).setEnabled(false);
        return parentView;
    }

    @Override
    public void showUserData(User user) {
        if (user.getPaymentMethods().isEmpty()) {
            method = new PaymentMethod();
        } else {
            method = user.getPaymentMethods().get(0);
        }
        fillData();
        getDataBinder().newBinder("Payment", new ObjectDataBinder<PaymentMethod>(method).registerView(this));
        super.showUserData(user);
    }

    private void fillData() {
        getView().findViewById(R.id.ed_card_expiry).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ed_card_expiry:
                AlertDialogFactory.createDialogWithoutDateField(getActivity(), this).show();
                break;
            case R.id.btn_refill:
                break;
            case R.id.btn_save_data:
                sendCardData();
                break;
        }
    }

    @Override
    public void sendCardData() {
        getPresenter().addCardData(method);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        String dateExpiry = "" + (monthOfYear + 1) + "/" + (year - 2000);
        ((TextView) getView().findViewById(R.id.ed_card_expiry)).setText(dateExpiry);
        method.setExpirationDate(dateExpiry);
    }

    @Override
    public void onDataUpdated(UIBinder uiBinder, Object object, String propertyName, Object value) {
        getView().findViewById(R.id.btn_save_data).setEnabled(true);
    }

}
