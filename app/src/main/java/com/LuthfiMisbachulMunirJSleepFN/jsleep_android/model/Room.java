package com.LuthfiMisbachulMunirJSleepFN.jsleep_android.model;

import com.LuthfiMisbachulMunirJSleepFN.jsleep_android.model.Serializable;

import java.util.*;

/**
 * @author Luthfi Misbachul Munir
 * @version 29 September 2022
 */

import java.util.ArrayList;
import java.util.Date;

/**
 * The `Room` class represents a room in a hotel or other lodging facility.
 *
 * @author Luthfi Misbachul Munir
 * @version 11 Desember 2022
 */
public class Room extends Serializable {
    /**
     * The size of the room.
     */
    public int size;

    /**
     * The name of the room.
     */
    public String name;

    /**
     * The facilities available in the room.
     */
    public ArrayList<Facility> facility = new ArrayList<>();

    /**
     * The price of the room.
     */
    public Price price;

    /**
     * The address of the room.
     */
    public String address;

    /**
     * The type of bed in the room.
     */
    public BedType bedType;

    /**
     * The city in which the room is located.
     */
    public City city;

    /**
     * The dates on which the room is booked.
     */
    public ArrayList<Date> booked;

    /**
     * The ID of the user who owns the room.
     */
    public int accountId;

    /**
     * Constructs a `Room` object with the specified owner ID, name, size, price, facilities, city, address, and bed type.
     *
     * @param accountId the ID of the user who owns the room
     * @param name the name of the room
     * @param size the size of the room
     * @param price the price of the room
     * @param facility the facilities available in the room
     * @param city the city in which the room is located
     * @param address the address of the room
     * @param bedType the type of bed in the room
     */
    public Room(int accountId, String name, int size, Price price,
                ArrayList<Facility> facility, City city, String address, BedType bedType) {
        this.accountId = accountId;
        this.city = city;
        this.address = address;
        this.name = name;
        this.size = size;
        this.price = price;
        this.facility = facility;
        this.booked = new ArrayList<Date>();
        this.bedType = bedType;
    }

    /**
     * Returns a string representation of the `Room` object.
     *
     * @return a string representation of the `Room` object
     */
    public String toString(){
        return  "\nName : " + name +
                "\nBed Type : " + bedType +
                "\nSize : " + size +
                "\n" + price +
                "\nFacility : " + facility +
                "\nCity : " + city +
                "\nAddress : " + address +
                "\nId : " + id + "\n\n";
    }
}