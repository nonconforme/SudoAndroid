package com.thinkmobiles.sudo.models;

/**
 * Created by njakawaii on 16.04.2015.
 */
public class ProfileResponse extends DefaultResponseModel {

    private ProfileModel user;

    public ProfileModel getUser() {
        return user;
    }

    public void setUser(ProfileModel user) {
        this.user = user;
    }

}
