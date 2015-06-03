package com.thinkmobiles.sudo.utils;

import android.content.Context;
import android.content.SharedPreferences;
import com.thinkmobiles.sudo.Main_Activity;
import com.thinkmobiles.sudo.global.Constants;


/**
 * Created by Sasha on 12.02.2015.
 */
public class StoreNotification {
    public static void storeNotificationsatus(final Context _context, final boolean show) {
        final SharedPreferences prefs = _context.getSharedPreferences(Main_Activity.class.getSimpleName(), Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(Constants.NOTIFICATION, show);

        editor.commit();
    }

    public static boolean loadNotificationStatus(final Context _context) {
        final SharedPreferences prefs = _context.getSharedPreferences(Main_Activity.class.getSimpleName(), Context.MODE_PRIVATE);

        return prefs.getBoolean(Constants.NOTIFICATION, false);
    }

}

