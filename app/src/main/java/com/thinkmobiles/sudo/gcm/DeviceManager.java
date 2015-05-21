package com.thinkmobiles.sudo.gcm;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.thinkmobiles.sudo.Main_Activity;
import com.thinkmobiles.sudo.global.Constants;
import com.thinkmobiles.sudo.global.SharedPreferencesManager;

/**
 * Created by Sasha on 17.10.2014.
 */
public abstract class DeviceManager {
    private static final String TAG = "DeviceManager";

    /**
     * Gets the current registration ID for application on GCM service.
     * <p/>
     * If result is empty, the app needs to register.
     *
     * @return registration ID, or empty string if there is no existing
     * registration ID.
     */

    public static String loadRegistrationId(final Context _context) {
        final SharedPreferences prefs = _context.getSharedPreferences(Main_Activity.class.getSimpleName(), Context.MODE_PRIVATE);
        String registrationId = prefs.getString(Constants.PARAM_REG_ID, "");
        if (registrationId.isEmpty()) {
            Log.i(TAG, "Registration not found.");
            return "";

        }
        int registeredVersion   = prefs.getInt(Constants.PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion      = SharedPreferencesManager.getAppVersion(_context);
        if (registeredVersion  != currentVersion) {
            Log.i(TAG, "App version changed.");
            return "";
        }
        return registrationId;
    }

    public static String loadIds(final Context _context){
        String push_id  = loadRegistrationId(_context);
        if ( push_id.isEmpty()){
            Log.d("RegId", "Error loading data");
        }
          return push_id;
    }
}
