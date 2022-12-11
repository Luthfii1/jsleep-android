package com.LuthfiMisbachulMunirJSleepFN.jsleep_android.model;

import com.LuthfiMisbachulMunirJSleepFN.jsleep_android.model.Serializable;

/**
 * The `Renter` class represents a person who rents a product or service.
 *
 * @author Luthfi Misbachul Munir
 * @version 11 Desember 2022
 */
public class Renter extends Serializable
{
    /**
     * A regular expression that matches the username of a `Renter` object.
     */
    public static final String REGEX_NAME ="^[A-Z][A-Za-z0-9_]{4,20}$";

    /**
     * A regular expression that matches the phone number of a `Renter` object.
     */
    public static final String REGEX_PHONE = "^[0-9]{9,12}$";

    /**
     * The username of the `Renter` object.
     */
    public String username;

    /**
     * The address of the `Renter` object.
     */
    public String address;

    /**
     * The phone number of the `Renter` object.
     */
    public String phoneNumber;

    /**
     * Constructs a `Renter` object with the specified username, phone number, and address.
     *
     * @param username the username of the `Renter` object
     * @param phoneNumber the phone number of the `Renter` object
     * @param address the address of the `Renter` object
     */
    public Renter(String username, String phoneNumber, String address){
        super();
        this.username = username;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    /**
     * Validates the username and phone number of the `Renter` object.
     *
     * @return `true` if the username and phone number are valid, `false` otherwise
     */
    public boolean validate(){
        if(this.username.matches(REGEX_NAME) && this.phoneNumber.matches(REGEX_PHONE)){
            return true;}
        return false;
    }
}