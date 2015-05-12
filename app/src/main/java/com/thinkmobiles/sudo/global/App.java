package com.thinkmobiles.sudo.global;

import android.app.Application;

import com.thinkmobiles.sudo.models.ProfileModel;
import com.thinkmobiles.sudo.models.addressbook.UserModel;

/**
 * Created by Sasha on 12.02.2015.
 */
public class App extends Application {
    private static String currentMobile;
    private static String uId;
    private static UserModel currentUser;
    public static String currentCredits;
    public static String currentContacts;

    public static UserModel getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(UserModel currentUser) {
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


    public static String getCurrentCredits() {
        return currentCredits;
    }

    public static void setCurrentCredits(String currentCredits) {
        App.currentCredits = currentCredits;
    }

    public static String getCurrentContacts() {
        return currentContacts;
    }

    public static void setCurrentContacts(String currentContacts) {
        App.currentContacts = currentContacts;
    }
}
