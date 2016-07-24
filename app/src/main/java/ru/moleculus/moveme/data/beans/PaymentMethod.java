package ru.moleculus.moveme.data.beans;



import com.noisyz.customeelements.utils.SimpleTextUtils;
import com.noisyz.databindinglibrary.annotations.field.SimpleFieldType;
import com.noisyz.databindinglibrary.utils.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.HashMap;

import ru.moleculus.moveme.BaseConstants;

/**
 * Created by Oleg on 05.03.2016.
 */
public class PaymentMethod implements BaseMoveMeObject{

    private long id = -1;

    @SimpleFieldType(com.noisyz.databindinglibrary.annotations.type.TEXT)
    private String type = BaseConstants.TYPE_CARD, number, cvs, expiration_date, card_owner;

    public void setNumber(String number){
        this.number = number;
    }

    public void setOwner(String owner){
        this.card_owner = owner;
    }

    public void setCVS(String code){
        this.cvs = code;
    }

    public void setId(long id){
        this.id = id;
    }

    public void setExpirationDate(String date){
        this.expiration_date = date;
    }

    public long getId(){
        return id;
    }

    public String getType(){
        return type;
    }

    public String getNumber(){
        return number;
    }

    public String getCvs(){
        return cvs;
    }

    public String getExpirationDate(){
        return expiration_date;
    }

    public String getCardOwner(){
        return card_owner;
    }

    @Override
    public boolean isObjectValid() {
        boolean isValid = true;
        for (Field field : getClass().getDeclaredFields()) {
            if (!field.getName().equals("id")) {
                String value = String.valueOf(ReflectionUtils.getVariableValue(field, this));
                isValid&=!SimpleTextUtils.isFieldEmpty(value);
            }
        }
        return isValid;
    }

    @Override
    public HashMap<String, String> getRequestHashMap(int objectIndex) {
        HashMap<String, String> hashMap = new HashMap<>();
        for (Field field : getClass().getDeclaredFields()) {
            String value = String.valueOf(ReflectionUtils.getVariableValue(field, this));
            boolean notEmptyId = !(field.getName().equals("id"));
            if (!SimpleTextUtils.isFieldEmpty(value)&&notEmptyId) {
                hashMap.put(field.getName(), value);
            }
        }
        return hashMap;
    }
}
