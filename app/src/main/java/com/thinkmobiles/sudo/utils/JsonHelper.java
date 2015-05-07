package com.thinkmobiles.sudo.utils;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thinkmobiles.sudo.models.addressbook.UserModel;

/**
 * Created by njakawaii on 07.05.2015.
 */
public abstract class JsonHelper {

    public static TypedJsonString makeJson(UserModel _userModel) {
          Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
        String temp = gson.toJson(_userModel);
        TypedJsonString request = new TypedJsonString(temp);
        return request;
    }
}