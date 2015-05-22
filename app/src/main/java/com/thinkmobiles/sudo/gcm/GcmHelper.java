package com.thinkmobiles.sudo.gcm;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.thinkmobiles.sudo.global.Constants;
import com.thinkmobiles.sudo.global.SharedPreferencesManager;

import java.io.IOException;

/**
 * Created by Sasha on 17.10.2014.
 */
public class GcmHelper {
    private GoogleCloudMessaging mGcm;
    private Context mContext;
    private Activity mActivity;
    private String regId;
    private String TAG = "GcmHelper";

    public GcmHelper(final Activity _activity) {
        this.mActivity = _activity;
        this.mContext = _activity.getApplicationContext();
    }

    public void registerDevice() {
        if (checkPlayServices()) {
            mGcm    = GoogleCloudMessaging.getInstance(mContext);
            regId   = DeviceManager.loadRegistrationId(mContext);

            if (regId.isEmpty()) {
                registerInBackground();
            }
        } else{
            Log.i(TAG, "No valid Google Play Services APK found.");
        }

    }


    public boolean checkPlayServices() {
        int resultCode  = GooglePlayServicesUtil.isGooglePlayServicesAvailable(mContext);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, mActivity,
                        Constants.PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i(TAG, "This device is not supported.");
                mActivity.finish();
            }
            return false;
        }
        return true;
    }


    private void registerInBackground() {

        new AsyncTask<Void, Void, String>() {
            protected void onPostExecute(String msg) {
                Log.d(TAG, msg);
                SharedPreferencesManager.storeRegistrationId(mContext, regId);
            }
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    if (mGcm == null) {
                        mGcm = GoogleCloudMessaging.getInstance(mContext);
                    }
                    regId = mGcm.register(Constants.SENDER_ID);
                    msg = "Device registered, registration ID=" + regId;
                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                }
                return msg;
            }

        }.execute(null, null, null);

    }

}
