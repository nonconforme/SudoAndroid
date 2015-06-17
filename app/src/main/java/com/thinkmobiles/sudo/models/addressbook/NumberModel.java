package com.thinkmobiles.sudo.models.addressbook;

import java.io.Serializable;

/**
 * Created by njakawaii on 17.04.2015.
 */
public class NumberModel implements Serializable {
    private String number;
    private boolean isBlocked;
    private String countryIso;
    private String expire;
    private String left;

    public NumberModel() {
        setNumber("");
        setCountryIso("");
        setLeft("");
    }

    public String getLeft() {
        return left;
    }

    public void setLeft(String left) {
        this.left = left;
    }

    public String getExpire() {
        return expire;
    }

    public void setExpire(String expire) {
        this.expire = expire;
    }

    public String getCountryIso() {
        return countryIso;
    }

    public void setCountryIso(String countryIso) {
        this.countryIso = countryIso;
    }

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
