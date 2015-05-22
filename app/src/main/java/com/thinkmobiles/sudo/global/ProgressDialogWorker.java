package com.thinkmobiles.sudo.global;

import android.app.ProgressDialog;
import android.content.Context;
import com.thinkmobiles.sudo.R;

/**
 * Created by Виталий on 31/07/2014.
 */
public abstract class ProgressDialogWorker {

    private static ProgressDialog mProgressDialog;

    public static void createDialog(final Context _context) {
        if (mProgressDialog == null) {
            showDialog(_context);
        } else if (!mProgressDialog.isShowing()) {
            showDialog(_context);
        }
    }

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

    public static void dismissDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing())
            mProgressDialog.dismiss();
    }
}
