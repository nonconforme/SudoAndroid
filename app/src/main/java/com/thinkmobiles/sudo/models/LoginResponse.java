package com.thinkmobiles.sudo.models;

/**
 * Created by njakawaii on 09.04.2015.
 */
public class LoginResponse extends DefaultResponseModel {
    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    private String uId;
}
