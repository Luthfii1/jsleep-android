package com.LuthfiMisbachulMunirJSleepFN.jsleep_android.model;

public class Price
{
    public double rebate;
    public double price;
    public double discount;

    public Price(double price, double discount)
    {
        this.price = price;
        this.discount = discount;
        this.rebate = 0;
    }

    public Price(double price)
    {
        this.price = price;
        this.discount = 0;
        this.rebate = 0;
    }

    public String toString(){
        return this.price + "";
    }
}