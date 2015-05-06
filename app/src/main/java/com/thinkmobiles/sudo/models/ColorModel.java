package com.thinkmobiles.sudo.models;

import java.io.Serializable;

/**
 * Created by njakawaii on 06.05.2015.
 */
public class ColorModel implements Serializable{
    private int mainColor;

    public ColorModel(int mainColor, int darkColor) {
        this.mainColor = mainColor;
        this.darkColor = darkColor;
    }

    public int getDarkColor() {
        return darkColor;
    }

    public void setDarkColor(int darkColor) {
        this.darkColor = darkColor;
    }

    public int getMainColor() {
        return mainColor;
    }

    public void setMainColor(int mainColor) {
        this.mainColor = mainColor;
    }

    private int darkColor;
}
