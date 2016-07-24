package ru.moleculus.moveme.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.noisyz.patternededittext.PatternedEditText;

import java.util.concurrent.TimeUnit;

import ru.moleculus.moveme.R;
import com.noisyz.customeelements.utils.SimpleAnimationUtils;
import ru.moleculus.moveme.data.SharedManager;
import ru.moleculus.moveme.presenters.LoginViewPresenter;
import ru.moleculus.moveme.presenters.impl.LoginViewPresenterImpl;
import ru.moleculus.moveme.view.LoginView;

/**
 * Created by Oleg on 25.02.2016.
 */
public class SendSmsCodeActivity extends AbsLoginActivity implements View.OnClickListener {

    private LoginViewPresenter presenter;

    private boolean isSendNumberButtonShowed, isSubmitButtonShowed;

    private int waitingSeconds = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_sms_code_number_layout);
        initUI();
    }

    private void initUI() {
        SimpleAnimationUtils.hide(findViewById(R.id.btn_send_phone));
        SimpleAnimationUtils.hide(findViewById(R.id.btn_submit));

        presenter = new LoginViewPresenterImpl((LoginView) this);

        final PatternedEditText edSmsCode = (PatternedEditText) findViewById(R.id.ed_sms_code);
        edSmsCode.setTextValidationListener(new PatternedEditText.TextValidationListener() {
            @Override
            public void onTextChanged(boolean isValid) {
                if (!isValid) {
                    showSendNumberButton();
                    hideSubmitButton();
                } else {
                    hideSendNumberButton();
                    showSubmitButton();
                }
                finishTimer();
            }
        });

        findViewById(R.id.btn_send_phone).setOnClickListener(this);
        findViewById(R.id.btn_submit).setOnClickListener(this);

        startTimer();
    }

    private void showSendNumberButton() {
        if (!isSendNumberButtonShowed) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    SimpleAnimationUtils.fadeIn(findViewById(R.id.btn_send_phone), ANIMATION_DURATION);
                    findViewById(R.id.btn_send_phone).bringToFront();
                    findViewById(R.id.btn_send_phone).requestLayout();
                }
            });
            isSendNumberButtonShowed = !isSendNumberButtonShowed;
        }
    }

    private void hideSendNumberButton() {
        if (isSendNumberButtonShowed) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    SimpleAnimationUtils.fadeOut(findViewById(R.id.btn_send_phone), ANIMATION_DURATION);
                }
            });
            isSendNumberButtonShowed = !isSendNumberButtonShowed;
        }
    }

    private void showSubmitButton() {
        if (!isSubmitButtonShowed) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    SimpleAnimationUtils.fadeIn(findViewById(R.id.btn_submit), ANIMATION_DURATION);
                    findViewById(R.id.btn_submit).bringToFront();
                    findViewById(R.id.btn_submit).requestLayout();
                }
            });
            isSubmitButtonShowed = !isSubmitButtonShowed;
        }
    }

    private void hideSubmitButton() {
        if (isSubmitButtonShowed) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    SimpleAnimationUtils.fadeOut(findViewById(R.id.btn_submit), ANIMATION_DURATION);
                }
            });
            isSubmitButtonShowed = !isSubmitButtonShowed;
        }
    }

    private void showWaitingMessage() {
        hideSendNumberButton();
        hideSubmitButton();
        startTimer();
    }

    private void startTimer() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (waitingSeconds < BASE_SMS_WAITING_TIME) {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    waitingSeconds++;
                    updateWaitingText();
                }
                waitingSeconds = 0;
                showSendNumberButton();
            }
        });
        thread.start();
    }

    private void finishTimer() {
        waitingSeconds = BASE_SMS_WAITING_TIME;
    }

    private void updateWaitingText() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String text = String.format(getString(R.string.sms_waiting), (BASE_SMS_WAITING_TIME - waitingSeconds));
                ((TextView) findViewById(R.id.tv_waiting)).setText(text);
            }
        });
    }


    @Override
    public void onClick(View view) {
        showProgressView();
        switch (view.getId()) {
            case R.id.btn_send_phone:
                presenter.sendNumber();
                hideSendNumberButton();
                break;
            case R.id.btn_submit:
                presenter.requestToken(((EditText)
                        findViewById(R.id.ed_sms_code)).getText().toString());
                break;
        }
    }

    @Override
    public void onNumberSended() {
        hideProgressView();
        showWaitingMessage();
    }

    @Override
    public void onAuthSuccessfully(String token, boolean isRegistered) {
        hideProgressView();
        SharedManager.saveToken(getContext(), token);
        SharedManager.setRegistered(getContext(), isRegistered);
        Intent intent = null;
        if (isRegistered) {
            intent = new Intent(getContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        } else {
            intent = new Intent(getContext(), RegisterUserActivity.class);
        }
        startActivity(intent);
        super.onAuthSuccessfully(token, isRegistered);
    }
}
