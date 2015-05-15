package com.thinkmobiles.sudo.global;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import java.util.concurrent.TimeUnit;

public abstract class Network {
    /**
     * check existing internet connection
     * @param _context
     * @return
     */
    public static final void isInternetConnectionAvailable(final Context _context) {
        final ConnectivityManager connectivityManager   = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo activeNetworkInfo             = connectivityManager.getActiveNetworkInfo();

          if(activeNetworkInfo == null || !activeNetworkInfo.isConnected()){
              noInternetConnectionDialog(_context);
        }


    }


    private static void noInternetConnectionDialog( final Context context) {

        new AlertDialog.Builder(context)

                .setTitle("Network Problem").setMessage("No Internet Connection").setPositiveButton("Retry", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                isInternetConnectionAvailable(context);
            }

        }).show();

    }



}
