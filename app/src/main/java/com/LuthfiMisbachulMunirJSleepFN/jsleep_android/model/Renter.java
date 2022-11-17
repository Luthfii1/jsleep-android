package com.LuthfiMisbachulMunirJSleepFN.jsleep_android.model;

import com.LuthfiMisbachulMunirJSleepFN.jsleep_android.model.Serializable;

public class Renter extends Serializable
{
    public static final String REGEX_NAME ="^[A-Z][A-Za-z0-9_]{4,20}$";
    public static final String REGEX_PHONE = "^[0-9]{9,12}$";
    public String username;
    public String address;
    public String phoneNumber;

    public Renter(String username, String phoneNumber, String address){
        super();
        this.username = username;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }
    public boolean validate(){
        if(this.username.matches(REGEX_NAME) && this.phoneNumber.matches(REGEX_PHONE)){
            return true;}
        return false;
    }
}