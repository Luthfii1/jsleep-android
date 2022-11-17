package com.LuthfiMisbachulMunirJSleepFN.jsleep_android.model;

import com.LuthfiMisbachulMunirJSleepFN.jsleep_android.model.Serializable;

import java.util.*;

/**
 * @author Luthfi Misbachul Munir
 * @version 29 September 2022
 */

import java.util.ArrayList;
import java.util.Date;

public class Room extends Serializable
{
    public BedType bedType;
    public int accountId;
    public Facility facility;
    public City city;
    public int size;
    public String name;
    public ArrayList<Date> booked;
    public String address;
    public Price price;

    public Room(int accountId, String name, int size, Price price, Facility facility, City city, String address){
        super();
        this.accountId = accountId;
        this.name = name;
        this.size = size;
        this.price = price;
        this.facility = facility;
        this.city = city;
        this.address = address;
        this.bedType = BedType.SINGLE;
        this.booked = new ArrayList<Date>();

    }

    public String toString(){
        return "Room\nId: " + accountId + ", Name: " + name + ", Size: " + size + ", Price: " + price.price + ", Facility: " + facility + ", City: " + city + ", Address: " + address  + ", Bed Type: " + bedType;
    }
}