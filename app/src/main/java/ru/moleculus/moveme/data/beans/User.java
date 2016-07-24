package ru.moleculus.moveme.data.beans;

import com.noisyz.databindinglibrary.annotations.converters.ConvertToUI;
import com.noisyz.databindinglibrary.annotations.field.ImageField;
import com.noisyz.databindinglibrary.annotations.field.SimpleFieldType;
import com.noisyz.databindinglibrary.annotations.methods.GetterMethod;
import com.noisyz.databindinglibrary.annotations.type;

import java.io.Serializable;
import java.util.ArrayList;

import ru.moleculus.moveme.BaseConstants;
import ru.moleculus.moveme.customview.MoveMeImageProvider;
import ru.moleculus.moveme.ui.fragments.user.converters.IdConverter;
import ru.moleculus.moveme.ui.fragments.user.converters.TypeOfAccountConverter;

/**
 * Created by Oleg on 11.02.2016.
 */
public class User implements Serializable {

    private static User instance;

    public User() {
    }

    public static User getInstance() {
        return instance;
    }

    public static void init(User user) {
        instance = user;
    }

    @SimpleFieldType(value = type.TEXT, convertToUI = @ConvertToUI(IdConverter.class))
    private long id;

    @SimpleFieldType(type.TEXT)
    private String mail, firstname, lastname, middlename, balance, rating, tariff_out_of_mkad_per_km, tariff_after_2hours, tariff_first_2hours;

    @ImageField(imageProvider = MoveMeImageProvider.class)
    private String user_pic;

    private ArrayList<PaymentMethod> payment_methods;

    private ArrayList<Passport> passports;

    public ArrayList<Passport> getPassports() {
        return passports;
    }

    public ArrayList<PaymentMethod> getPaymentMethods() {
        return payment_methods;
    }

    @SimpleFieldType(value = type.TEXT, convertToUI = @ConvertToUI(TypeOfAccountConverter.class))
    private int type_of_account;

    public long getId() {
        return id;
    }

    public int getTypeOfAccount() {
        return type_of_account;
    }

    public String getMail() {
        return mail;
    }

    public String getFirstName() {
        return firstname;
    }

    public String getLastName() {
        return lastname;
    }

    public String getMiddleName() {
        return middlename;
    }

    public String getTariffFirst2Hours() {
        return tariff_first_2hours;
    }

    public String getTariffAfter2Hours() {
        return tariff_after_2hours;
    }

    public String getTariffOutOfMkad() {
        return tariff_out_of_mkad_per_km;
    }

    @GetterMethod(value = type.VISIBILITY, propertyKey = "isWorker")
    public boolean isWorker() {
        return type_of_account == BaseConstants.TYPE_ACCOUNT_WORKER;
    }

}
