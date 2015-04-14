package com.thinkmobiles.sudo.models;

/**
 * Created by Pavilion on 14.04.2015.
 */
public class Numbers {

    private String mCountry;
    private String mCredits;

    public Numbers(String _country, String _credits){
        mCountry = _country;
        mCredits = _credits;
    }

    public String getmCredits() {
        return mCredits;
    }
    public String getmCountry() {
        return mCountry;
    }
}
