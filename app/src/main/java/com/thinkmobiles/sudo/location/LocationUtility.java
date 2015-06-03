package com.thinkmobiles.sudo.location;

import android.app.PendingIntent;
import android.content.Context;
import android.location.*;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import com.thinkmobiles.sudo.global.App;

import java.io.IOException;
import java.util.Locale;


/**
 * Created by omar on 03.06.15.
 */
public class LocationUtility {
    private Context context;

    private LocationManager locationManager;

    private PendingIntent locationPendingIntent;

    private LocationListener locationListener;

    private StringBuilder sbGPS = new StringBuilder();
    private StringBuilder sbNet = new StringBuilder();

    private boolean gpsEnabled;
    private boolean networkEnabled;

    public LocationUtility(Context context) {

        this.context = context;
        locationManager = (android.location.LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        setupLocationListener();
    }


    public void getLocation() {
        gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        networkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (!networkEnabled) return;
        locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, locationListener, Looper.getMainLooper());
    }


    private void setupLocationListener() {

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                Address address = new Address(Locale.getDefault());
                try {
                    address = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1).get(0);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Log.d("Current location iso = ", address.getCountryCode());
                App.setCurrentLocationISO(address.getCountryCode());
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };


    }


}
