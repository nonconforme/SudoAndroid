package com.thinkmobiles.sudo.models;

/**
 * Created by njakawaii on 07.05.2015.
 */
public class UpdateProfileModel  extends  DefaultResponseModel{
    private String avatar;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
