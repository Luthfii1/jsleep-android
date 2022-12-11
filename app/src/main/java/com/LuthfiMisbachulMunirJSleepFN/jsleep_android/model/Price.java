package com.LuthfiMisbachulMunirJSleepFN.jsleep_android.model;

/**
 * The `Price` class represents the price of a product.
 *
 * @author Luthfi Misbachul Munir
 * @version 11 Desember 2022
 */
public class Price
{
    /**
     * The amount of the rebate.
     */
    public double rebate;

    /**
     * The price of the product.
     */
    public double price;

    /**
     * The discount applied to the product.
     */
    public double discount;

    /**
     * Constructs a `Price` object with the specified price and discount.
     *
     * @param price the price of the product
     * @param discount the discount applied to the product
     */
    public Price(double price, double discount)
    {
        this.price = price;
        this.discount = discount;
        this.rebate = 0;
    }

    /**
     * Constructs a `Price` object with the specified price.
     *
     * @param price the price of the product
     */
    public Price(double price)
    {
        this.price = price;
        this.discount = 0;
        this.rebate = 0;
    }

    /**
     * Returns the price of the product as a string.
     *
     * @return the price of the product as a string
     */
    public String toString(){
        return this.price + "";
    }
}