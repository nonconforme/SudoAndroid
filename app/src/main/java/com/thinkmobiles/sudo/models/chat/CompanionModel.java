package com.thinkmobiles.sudo.models.chat;

import java.io.Serializable;

/**
 * Created by njakawaii on 07.05.2015.
 */
public class CompanionModel implements Serializable{
    private String number;
    private String avatar;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
