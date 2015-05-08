package com.thinkmobiles.sudo.global;

import android.app.Application;

import com.thinkmobiles.sudo.models.ProfileModel;

/**
 * Created by Sasha on 12.02.2015.
 */
public class App extends Application {
    private static String currentMobile;
    private static String uId;
    private static ProfileModel currentUser;

    public static ProfileModel getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(ProfileModel currentUser) {
        App.currentUser = currentUser;
    }


    public static String getuId() {
        return uId;
    }

    public static void setuId(String uId) {
        App.uId = uId;
    }

    public static String getCurrentMobile() {
        return currentMobile;
    }

    public static void setCurrentMobile(String currentMobile) {
        App.currentMobile = currentMobile;
    }

}
