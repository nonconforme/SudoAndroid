package com.thinkmobiles.sudo.models.addressbook;

import java.io.Serializable;

/**
 * Created by njakawaii on 17.04.2015.
 */
public class NumberModel implements Serializable{
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NumberModel)) return false;

        NumberModel that = (NumberModel) o;

        return !(getNumber() != null ? !getNumber().equals(that.getNumber()) : that.getNumber() != null);

    }

    @Override
    public int hashCode() {
        return getNumber() != null ? getNumber().hashCode() : 0;
    }
}
