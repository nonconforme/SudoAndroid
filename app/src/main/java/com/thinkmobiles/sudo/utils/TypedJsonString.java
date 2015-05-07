package com.thinkmobiles.sudo.utils;

import retrofit.mime.TypedString;

/**
 * Created by njakawaii on 07.05.2015.
 */
public class TypedJsonString extends TypedString {
    public TypedJsonString(String body) {
        super(body);
    }

    @Override
    public String mimeType() {
        return "application/json";
    }
}