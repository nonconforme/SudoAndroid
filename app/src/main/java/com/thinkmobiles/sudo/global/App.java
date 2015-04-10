package com.thinkmobiles.sudo.global;

import android.app.Application;

/**
 * Created by Sasha on 12.02.2015.
 */
public class App extends Application {
    private static String userName;
    private static String uId;

    public static String getuId() {
        return uId;
    }

    public static void setuId(String uId) {
        App.uId = uId;
    }

    public static String getNumber() {
        return number;
    }

    public static void setNumber(String number) {
        App.number = number;
    }

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        App.userName = userName;
    }

    private static String number;
}
