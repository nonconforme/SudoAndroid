package com.thinkmobiles.sudo.utils;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thinkmobiles.sudo.models.BlockNumber;
import com.thinkmobiles.sudo.models.DeviceID;
import com.thinkmobiles.sudo.models.addressbook.UserModel;
import com.thinkmobiles.sudo.models.chat.ReadModel;

import java.util.List;

/**
 * Created by njakawaii on 07.05.2015.
 */
public abstract class JsonHelper {

    public static TypedJsonString makeJsonUserModel(UserModel _userModel) {
        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
        String temp = gson.toJson(_userModel);
        TypedJsonString request = new TypedJsonString(temp);
        return request;
    }


    public static TypedJsonString makeJsonBlockedNumbers(List<BlockNumber> blockNumberList) {
        Gson gson = new GsonBuilder().create();
        String temp = gson.toJson(blockNumberList);
        TypedJsonString request = new TypedJsonString(temp);
        return request;
    }

    public static TypedJsonString makeJsonDeviceId(DeviceID deviceID) {
        Gson gson = new GsonBuilder().create();
        String temp = gson.toJson(deviceID);
        TypedJsonString request = new TypedJsonString(temp);
        return request;
    }
    public static TypedJsonString makeJsonReadModel(ReadModel readModel) {
        Gson gson = new GsonBuilder().create();
        String temp = gson.toJson(readModel);
        TypedJsonString request = new TypedJsonString(temp);
        return request;
    }

}
