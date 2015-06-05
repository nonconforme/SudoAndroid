package com.thinkmobiles.sudo.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import com.thinkmobiles.sudo.Main_Activity;
import com.thinkmobiles.sudo.global.Constants;

/**
 * Created by Sasha on 17.10.2014.
 */
public abstract class SharedPreferencesManager {

    private static final String TAG = "GCMDemoShared";

    public static void storeRegistrationId(final Context _context, final String _regId) {
        final SharedPreferences prefs = _context.getSharedPreferences(
                Main_Activity.class.getSimpleName(), Context.MODE_PRIVATE);
        int appVersion = getAppVersion(_context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Constants.PARAM_REG_ID, _regId);
        editor.putInt(Constants.PROPERTY_APP_VERSION, appVersion);
        editor.commit();
    }

    public static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }


}
