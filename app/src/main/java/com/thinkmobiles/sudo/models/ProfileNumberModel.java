package com.thinkmobiles.sudo.models;

/**
 * Created by njakawaii on 05.05.2015.
 */
public class ProfileNumberModel {
    private String number;
    private String countryIso;
    private String expire;
    private String left;


    public String getLeft() {
        return left;
    }

    public void setLeft(String left) {
        this.left = left;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCountryIso() {
        return countryIso;
    }

    public void setCountryIso(String countryIso) {
        this.countryIso = countryIso;
    }

    public String getExpire() {
        return expire;
    }

    public void setExpire(String expire) {
        this.expire = expire;
    }

}
