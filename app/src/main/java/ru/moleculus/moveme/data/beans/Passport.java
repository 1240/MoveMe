package ru.moleculus.moveme.data.beans;

import com.noisyz.customeelements.utils.SimpleTextUtils;
import com.noisyz.databindinglibrary.annotations.converters.Conversion;
import com.noisyz.databindinglibrary.annotations.field.SimpleFieldType;
import com.noisyz.databindinglibrary.annotations.type;
import com.noisyz.databindinglibrary.utils.ReflectionUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * Created by Oleg on 05.03.2016.
 */
public class Passport implements Serializable , BaseMoveMeObject{

    private long id = -1;
    @SimpleFieldType(type.TEXT)
    private String series, number, issued_by, code_of_department, first_name, middle_name,
            last_name, location_of_birth, location_of_registration;


    @SimpleFieldType(value = type.TEXT, twoWayConverter = @Conversion(value = PassportUserFriendlyConverter.class))
    private String date_of_registration, date_of_birth, date_of_issue;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMiddleName() {
        return middle_name;
    }

    public String getFirstName() {
        return first_name;
    }

    public String getCodeDepartment() {
        return code_of_department;
    }

    public String getDateOfIssue() {
        return date_of_issue;
    }

    public String getIssuedBy() {
        return issued_by;
    }

    public String getNumber() {
        return number;
    }

    public String getSeries() {
        return series;
    }

    public String getLastName() {
        return last_name;
    }

    public String getDateOfBirth() {
        return date_of_birth;
    }

    public String getLocationOfBirth() {
        return location_of_birth;
    }

    public String getLocationOfRegistration() {
        return location_of_registration;
    }

    public String getDateOfRegistration() {
        return date_of_registration;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setIssuedBy(String issued_by) {
        this.issued_by = issued_by;
    }

    public void setDateOfIssue(String date_of_issue) {
        this.date_of_issue = date_of_issue;
    }

    public void setCodeOfDepartment(String codeOfDepartment) {
        this.code_of_department = codeOfDepartment;
    }

    public void setFirstName(String firstName) {
        this.first_name = firstName;
    }

    public void setMiddleName(String middleName) {
        this.middle_name = middleName;
    }

    public void setLastName(String lastName) {
        this.last_name = lastName;
    }

    public void setDateOfBirth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public void setLocationOfBirth(String location) {
        this.location_of_birth = location;
    }

    public void setLocationOfRegistration(String locationOfRegistration) {
        this.location_of_registration = locationOfRegistration;
    }

    public void setDateOfRegistration(String date) {
        this.date_of_registration = date;
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
