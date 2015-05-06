package com.thinkmobiles.sudo.global;

import android.app.Application;

/**
 * Created by Sasha on 12.02.2015.
 */
public class App extends Application {
    private static String currentMobile;
    private static String uId;
    private static String getUserName;
    private static String avatar;

    public static String getAvatar() {
        return avatar;
    }

    public static void setAvatar(String avatar) {
        App.avatar = avatar;
    }


    public static String getuId() {
        return uId;
    }

    public static void setuId(String uId) {
        App.uId = uId;
    }

    public static String getGetUserName() {
        return getUserName;
    }

    public static void setGetUserName(String getUserName) {
        App.getUserName = getUserName;
    }

    public static String getCurrentMobile() {
        return currentMobile;
    }

    public static void setCurrentMobile(String currentMobile) {
        App.currentMobile = currentMobile;
    }

}
