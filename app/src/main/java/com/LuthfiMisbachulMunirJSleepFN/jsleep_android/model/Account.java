package com.LuthfiMisbachulMunirJSleepFN.jsleep_android.model;
import com.LuthfiMisbachulMunirJSleepFN.jsleep_android.model.Serializable;

public class Account extends Serializable
{
    public double balance;
    public Renter renter;
    public String name;
    public String email;
    public String password;
    public  static final String REGEX_EMAIL = "^[a-zA-Z0-9 ][a-zA-Z0-9]+@[a-zA-Z.]+?\\.[a-zA-Z]+?$";
    public  static final String REGEX_PASSWORD = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$";

    public Account(String name,String email,String password){
        super();
        this.name = name;
        this.email = email;
        this.password = password;
        this.balance = 0;
    }

    public String toString(){
        return "Account{" +
                "balance= " + balance +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", renter=" + renter + '}';
    }

    public boolean validate(){
        return this.email.matches(REGEX_EMAIL) && this.password.matches(REGEX_PASSWORD);
    }
}