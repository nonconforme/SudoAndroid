package com.thinkmobiles.sudo.models;

/**
 * Created by Pavilion on 16.04.2015.
 */
public class Credits {

    private String mCredits;
    private String mBuy;

    public Credits(String _credits, String _buy){
        mCredits = _credits;
        mBuy = _buy;
    }

    public String getCredits() {
        return mCredits;
    }

    public String getBuy() {
        return mBuy;
    }
}
