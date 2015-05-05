package com.thinkmobiles.sudo.models.numbers;

import java.io.Serializable;

/**
 * Created by njakawaii on 05.05.2015.
 */
public class NumberObject implements Serializable {
    private String country;
    private String number;


    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

}
