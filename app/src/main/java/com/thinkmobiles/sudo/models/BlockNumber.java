package com.thinkmobiles.sudo.models;

/**
 * Created by omar on 19.05.15.
 */
public class BlockNumber {

    public BlockNumber(){}
    public BlockNumber(String number, boolean isBlocked){
        this.number = number;
        this.isBlocked = isBlocked;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setIsBlocked(boolean isBlocked) {
        this.isBlocked = isBlocked;
    }

    String number;
    boolean isBlocked;
}
