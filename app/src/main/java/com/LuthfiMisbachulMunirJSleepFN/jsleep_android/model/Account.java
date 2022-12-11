package com.LuthfiMisbachulMunirJSleepFN.jsleep_android.model;
import com.LuthfiMisbachulMunirJSleepFN.jsleep_android.model.Serializable;

/**
 * This class is used to store the information of a user account.
 * @author Luthfi Misbachul Munir
 * @see Serializable
 */
public class Account extends Serializable
{
    public double balance;
    public Renter renter;
    public String name;
    public String email;
    public String password;
    public  static final String REGEX_EMAIL = "^[a-zA-Z0-9 ][a-zA-Z0-9]+@[a-zA-Z.]+?\\.[a-zA-Z]+?$";
    public  static final String REGEX_PASSWORD = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$";

    /**
     * This is the constructor for the Account class.
     * @author Luthfi Misbachul Munir
     * @param name The name of the account.
     * @param email The email of the account.
     * @param password The password of the account.
     *                 The password must contain at least 8 characters, 1 uppercase, 1 lowercase, and 1 number
     */

    public Account(String name,String email,String password){
        super();
        this.name = name;
        this.email = email;
        this.password = password;
        this.balance = 0;
    }

    public String toString(){
        return "Account\nId: " + super.id + "\nName: " + name + "\nEmail: " + email + "\nPassword: " + password;
    }

    /**
     * This method is used to validate the email and password of the account.
     * @author Luthfi Misbachul Munir
     * @return Returns true if the email and password are valid.
     */
    public boolean validate(){
        return this.email.matches(REGEX_EMAIL) && this.password.matches(REGEX_PASSWORD);
    }
}