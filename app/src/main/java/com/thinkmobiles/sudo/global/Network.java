package com.thinkmobiles.sudo.global;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.thinkmobiles.sudo.R;

public abstract class Network {
    /**
     * check existing internet connection
     *
     * @param _context
     * @return
     */
    public static final boolean isInternetConnectionAvailable(final Context _context) {
        final ConnectivityManager connectivityManager = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
            noInternetConnectionDialog(_context);
            return false;
        }

        return true;
    }

    public static final boolean isInternetConnectionAvailableNoDialog(final Context _context) {
        final ConnectivityManager connectivityManager = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {

            return false;
        }

        return true;
    }

    private static void noInternetConnectionDialog(final Context context) {

        new AlertDialog.Builder(context)

                .setTitle(context.getString(R.string.network_problem)).setMessage(context.getString(R.string.no_internet_connection)).setPositiveButton(context.getString(R.string.retry), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                isInternetConnectionAvailable(context);
            }

        }).show();

    }


}
