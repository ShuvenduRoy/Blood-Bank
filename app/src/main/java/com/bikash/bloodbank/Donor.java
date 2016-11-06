package com.bikash.bloodbank;

/**
 * Created by root on 11/6/16.
 */

public class Donor {
    String name;
    String contuctNumber;
    String city;
    String address;

    public Donor(String name, String contuctNumber, String address, String city) {
        this.name = name;
        this.contuctNumber = contuctNumber;
        this.address = address;
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContuctNumber() {
        return contuctNumber;
    }

    public void setContuctNumber(String contuctNumber) {
        this.contuctNumber = contuctNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
