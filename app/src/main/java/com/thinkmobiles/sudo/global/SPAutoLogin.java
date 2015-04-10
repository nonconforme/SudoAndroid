package com.thinkmobiles.sudo.global;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.thinkmobiles.sudo.MainActivity;


/**
 * Created by Sasha on 12.02.2015.
 */
public class SPAutoLogin {
    public static void storeLoginParams(final Context _context, final String _email, final String _pas) {
        final SharedPreferences prefs = _context.getSharedPreferences(
                MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
        Log.i("Login param saved", "Saving mobileId on app version ");
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Constants.EMAIL, _email);
        editor.putString(Constants.PASSWORD, _pas);
        editor.commit();
    }
    public static String loadMail(final Context _context) {
        final SharedPreferences prefs = _context.getSharedPreferences(MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
        String email = prefs.getString(Constants.EMAIL, "");
        return email;
    }
    public static String loadPassword(final Context _context) {
        final SharedPreferences prefs = _context.getSharedPreferences(MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
        String pass = prefs.getString(Constants.PASSWORD, "");
        return pass;
    }
    public static String loadUserId(final Context _context) {
        final SharedPreferences prefs = _context.getSharedPreferences(MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
        String id = prefs.getString(Constants.USER_ID, "");
        return id;
    }
    public static void storeUserId(final Context _context, final String _id) {
        final SharedPreferences prefs = _context.getSharedPreferences(
                MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Constants.USER_ID, _id);
        editor.commit();
    }
}

