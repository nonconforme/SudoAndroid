package com.thinkmobiles.sudo.models;

import com.thinkmobiles.sudo.models.DefaultResponseModel;

import java.util.List;

/**
 * Created by njakawaii on 16.04.2015.
 */
public class ProfileModel extends DefaultResponseModel{
    private String email;
    private String mobile;
    private String __v;
    private List<ProfileNumberModel> numbers;
    private boolean enablepush;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String get__v() {
        return __v;
    }

    public void set__v(String __v) {
        this.__v = __v;
    }

    public List<ProfileNumberModel> getNumbers() {
        return numbers;
    }

    public void setNumbers(List<ProfileNumberModel> numbers) {
        this.numbers = numbers;
    }

    public boolean isEnablepush() {
        return enablepush;
    }

    public void setEnablepush(boolean enablepush) {
        this.enablepush = enablepush;
    }
}
