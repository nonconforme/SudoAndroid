package com.thinkmobiles.sudo.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.thinkmobiles.sudo.activities.Main_Activity;
import com.thinkmobiles.sudo.global.Constants;


/**
 * Created by Sasha on 12.02.2015.
 */
public class SPAutoLogin {
    public static void storeLoginParams(final Context _context, final String _email, final String _pas) {
        final SharedPreferences prefs = _context.getSharedPreferences(
                Main_Activity.class.getSimpleName(), Context.MODE_PRIVATE);
        Log.i("Login param saved", "Saving mobileId on app version ");
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Constants.EMAIL, _email);
        editor.putString(Constants.PASSWORD, _pas);
        editor.commit();
    }
    public static String loadMail(final Context _context) {
        final SharedPreferences prefs = _context.getSharedPreferences(Main_Activity.class.getSimpleName(), Context.MODE_PRIVATE);
        String email = prefs.getString(Constants.EMAIL, "");
        return email;
    }
    public static String loadPassword(final Context _context) {
        final SharedPreferences prefs = _context.getSharedPreferences(Main_Activity.class.getSimpleName(), Context.MODE_PRIVATE);
        String pass = prefs.getString(Constants.PASSWORD, "");
        return pass;
    }

}

