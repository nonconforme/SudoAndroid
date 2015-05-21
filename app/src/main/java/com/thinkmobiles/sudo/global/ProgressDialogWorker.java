package com.thinkmobiles.sudo.global;

import android.app.ProgressDialog;
import android.content.Context;
import com.thinkmobiles.sudo.R;

/**
 * Created by Виталий on 31/07/2014.
 */
public abstract class ProgressDialogWorker {

    private static ProgressDialog mProgressDialog;

    /**
     * Shows ProgressDialog if it is
     * not showing already
     *
     * @param _context application context
     */
    public static void createDialog(final Context _context) {
        if (mProgressDialog == null) {
            showDialog(_context);
        } else if (!mProgressDialog.isShowing()) {
            showDialog(_context);
        }
    }

    /**
     * Shows ProgressDialog
     *
     * @param _context application context
     */
    private static void showDialog(final Context _context) {
        try {
            mProgressDialog = new ProgressDialog(_context);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setMessage(_context.getString(R.string.loading));
            mProgressDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Dismiss ProgressDialog if it is showing
     */
    public static void dismissDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing())
            mProgressDialog.dismiss();
    }
}
