package com.thinkmobiles.sudo.models.addressbook;

/**
 * Created by njakawaii on 17.04.2015.
 */
public class NumberModel {
    private String number;
    private boolean  isBlocked;


    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean isBlocked) {
        this.isBlocked = isBlocked;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

}
