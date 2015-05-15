package com.thinkmobiles.sudo.global;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public abstract class Network {
    /**
     * check existing internet connection
     * @param _context
     * @return
     */
    public static final boolean isInternetConnectionAvailable(final Context _context) {
        final ConnectivityManager connectivityManager   = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo activeNetworkInfo             = connectivityManager.getActiveNetworkInfo();

          if(activeNetworkInfo == null || !activeNetworkInfo.isConnected()){
              noInternetConnectionDialog(_context);
        }
            return true;

    }


    public static void noInternetConnectionDialog( final Context context) {

        new AlertDialog.Builder(context)

                .setTitle("Network Problem").setMessage("No Internet Connection").setPositiveButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                isInternetConnectionAvailable(context);
            }

        }).show();
    }


}
