package ru.moleculus.moveme.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import ru.moleculus.moveme.R;
import com.noisyz.customeelements.utils.SimpleAnimationUtils;
import com.noisyz.customeelements.utils.SimpleTextUtils;

import ru.moleculus.moveme.data.SharedManager;
import ru.moleculus.moveme.presenters.LoginViewPresenter;
import ru.moleculus.moveme.presenters.impl.LoginViewPresenterImpl;
import ru.moleculus.moveme.view.LoginView;

/**
 * Created by Oleg on 25.02.2016.
 */
public class RegisterUserActivity extends AbsLoginActivity implements TextWatcher, View.OnClickListener {

    private LoginViewPresenter presenter;
    private EditText edFirstName, edLastName, edMiddleName, edMail;

    private MenuItem item;

    private boolean isSubmitButtonShowed = false;

    private int typeOfAccount = TYPE_ACCOUNT_CLIENT;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_activity_layout);
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_register, menu);
        item = menu.findItem(R.id.action_save);
        item.setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                presenter.registerUser(typeOfAccount, edFirstName.getText().toString(),
                        edLastName.getText().toString(), edMiddleName.getText().toString(), edMail.getText().toString());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.settings);
        presenter = new LoginViewPresenterImpl((LoginView) this);
        edFirstName = (EditText) findViewById(R.id.ed_first_name);
        edFirstName.addTextChangedListener(this);
        edLastName = (EditText) findViewById(R.id.ed_second_name);
        edLastName.addTextChangedListener(this);
        edMiddleName = (EditText) findViewById(R.id.ed_middle_name);
        edMiddleName.addTextChangedListener(this);
        edMail = (EditText) findViewById(R.id.ed_mail);
        edMail.addTextChangedListener(this);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.type_acc_client).setIcon(R.drawable.icon_client));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.type_acc_worker).setIcon(R.drawable.icon_driver));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                typeOfAccount = tab.getPosition();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        findViewById(R.id.btn_submit).setOnClickListener(this);
        SimpleAnimationUtils.hide(findViewById(R.id.btn_submit));
    }

    @Override
    public void onRegistrationFinish() {
        hideProgressView();
        SharedManager.setRegistered(this, true);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean isDataValid() {
        boolean validType = typeOfAccount != TYPE_ACCOUNT_INVALID;
        boolean validFirstName = !edFirstName.getText().toString().isEmpty();
        boolean validLastName = !edLastName.getText().toString().isEmpty();
        boolean validMiddleName = !edMiddleName.getText().toString().isEmpty();
        boolean validMail = SimpleTextUtils.isValidEmail(edMail.getText().toString());
        return validType && validFirstName && validLastName && validMiddleName && validMail;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        item.setVisible(isDataValid());
        if (isDataValid()) {
            showSubmitButton();
        } else {
            hideSubmitButton();
        }
    }

    private void showSubmitButton() {
        if (!isSubmitButtonShowed) {
            SimpleAnimationUtils.fadeIn(findViewById(R.id.btn_submit), ANIMATION_DURATION);
            isSubmitButtonShowed = !isSubmitButtonShowed;
        }
    }

    private void hideSubmitButton() {
        if (isSubmitButtonShowed) {
            SimpleAnimationUtils.fadeOut(findViewById(R.id.btn_submit), ANIMATION_DURATION);
            isSubmitButtonShowed = !isSubmitButtonShowed;
        }
    }

    @Override
    public void onClick(View v) {
        presenter.registerUser(typeOfAccount, edFirstName.getText().toString(),
                edLastName.getText().toString(), edMiddleName.getText().toString(), edMail.getText().toString());
        showProgressView();
    }

}
