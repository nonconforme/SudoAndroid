package com.thinkmobiles.sudo.models;

import com.thinkmobiles.sudo.models.addressbook.UserModel;

/**
 * Created by njakawaii on 16.04.2015.
 */
public class ProfileResponse extends DefaultResponseModel {

    private UserModel user;

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

}
