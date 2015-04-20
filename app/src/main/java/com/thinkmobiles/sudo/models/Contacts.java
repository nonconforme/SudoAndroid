package com.thinkmobiles.sudo.models;

import android.graphics.Bitmap;

/**
 * Created by omar on 19.04.15.
 */
public class Contacts {
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getPhoneNumber1() {
        return phoneNumber1;
    }

    public void setPhoneNumber1(String phoneNumber1) {
        this.phoneNumber1 = phoneNumber1;
    }

    public String getPhoneNumber2() {
        return phoneNumber2;
    }

    public void setPhoneNumber2(String phoneNumber2) {
        this.phoneNumber2 = phoneNumber2;
    }

    public String getPhoneNumber3() {
        return phoneNumber3;
    }

    public void setPhoneNumber3(String phoneNumber3) {
        this.phoneNumber3 = phoneNumber3;
    }

    private String firstName;
    private String lastName;
    private String  avatarUrl;

    private String phoneNumber1;
    private String  phoneNumber2;
    private String  phoneNumber3;





}
