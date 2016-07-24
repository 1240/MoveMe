package ru.moleculus.moveme.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.noisyz.patternededittext.PatternedEditText;

import ru.moleculus.moveme.BaseConstants;
import ru.moleculus.moveme.R;
import com.noisyz.customeelements.utils.SimpleAnimationUtils;

import ru.moleculus.moveme.presenters.LoginViewPresenter;
import ru.moleculus.moveme.presenters.impl.LoginViewPresenterImpl;
import ru.moleculus.moveme.view.LoginView;

/**
 * Created by Oleg on 25.02.2016.
 */
public class SendNumberActivity extends AbsLoginActivity implements LoginView {

    private LoginViewPresenter presenter;

    private boolean isSendButtonShowed;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_phone_number_layout);
        initUI();
    }


    private void initUI() {
        SimpleAnimationUtils.hide(findViewById(R.id.btn_send_phone));
        presenter = new LoginViewPresenterImpl(this);
        final PatternedEditText edNumber = (PatternedEditText) findViewById(R.id.ed_phone_number);
        edNumber.setText(PHONE_CODE);
        edNumber.setSelection(edNumber.getText().length());
        edNumber.setTextValidationListener(new PatternedEditText.TextValidationListener() {
            @Override
            public void onTextChanged(boolean isValid) {
                if (isValid) {
                    showSendPhoneButton();
                } else {
                    hideSendPhoneButton();
                }
            }
        });
        findViewById(R.id.btn_send_phone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.sendNumber(edNumber.getText().toString());
                showProgressView();
            }
        });
    }

    private void showSendPhoneButton() {
        if (!isSendButtonShowed) {
            animatePhoneButton();
        }
    }

    private void animatePhoneButton() {
        if (isSendButtonShowed) {
            SimpleAnimationUtils.fadeOut(findViewById(R.id.btn_send_phone), BaseConstants.ANIMATION_DURATION);
        } else {
            SimpleAnimationUtils.fadeIn(findViewById(R.id.btn_send_phone), BaseConstants.ANIMATION_DURATION);
            findViewById(R.id.btn_send_phone).bringToFront();
            findViewById(R.id.btn_send_phone).requestLayout();
        }
        isSendButtonShowed = !isSendButtonShowed;
    }

    private void hideSendPhoneButton() {
        if (isSendButtonShowed) {
            animatePhoneButton();
        }
    }

    @Override
    public void onNumberSended() {
        hideProgressView();
        Intent intent = new Intent(getContext(), SendSmsCodeActivity.class);
        startActivity(intent);
    }
}
