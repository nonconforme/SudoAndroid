package com.thinkmobiles.sudo.location;

import android.app.PendingIntent;
import android.content.Context;
import android.location.LocationManager;


/**
 * Created by omar on 03.06.15.
 */
public class LocationUtility {
    private Context context;

    private LocationManager locationManager;

    private PendingIntent locationPendingIntent;

    private StringBuilder sbGPS = new StringBuilder();
    private StringBuilder sbNet = new StringBuilder();

    public LocationUtility(Context context) {

        this.context = context;
        locationManager = (android.location.LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        setupPendingIntent();
    }


    public void getLocation() {
        locationManager.requestSingleUpdate(android.location.LocationManager.GPS_PROVIDER, locationPendingIntent);
    }


    private void setupPendingIntent() {
/*
        locationPendingIntent = new PendingIntent()
*/


    }


}
