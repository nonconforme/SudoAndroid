package com.thinkmobiles.sudo;


import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;


/**
 * Created by omar on 30.04.15.
 */
public class ToolbarManager {

    private static ActionBarActivity mActivity;
    public static Toolbar mToolbar;
    private static ToolbarManager sToolbarManager;

    public static void setmActivity(ActionBarActivity _mActivity) {
        mActivity = _mActivity;

    }

    public static ToolbarManager getInstance(Activity _actionBarActivity) {
        if (sToolbarManager == null) {
            sToolbarManager = new ToolbarManager();
            mActivity = (ActionBarActivity) _actionBarActivity;
            initToolbar();
        } else {
            mActivity = (ActionBarActivity) _actionBarActivity;
            initToolbar();
        }
        return sToolbarManager;
    }

    public static void initToolbar() {
        if (sToolbarManager != null) {
            mToolbar = (Toolbar) mActivity.findViewById(R.id.toolbar);
            mActivity.setSupportActionBar(mToolbar);
            mActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            mActivity.getSupportActionBar().setHomeButtonEnabled(true);
            mActivity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    public Toolbar getToolbar() {
        return mToolbar;
    }


    public void changeToolbarTitleAndImage(int title, int image) {
        mToolbar.setTitle(title);
        if (image == 0) {
            mActivity.getSupportActionBar().setIcon(new ColorDrawable(mActivity.getResources().getColor(android.R.color.transparent)));
        }
    }

    public void changeToolbarTitleAndImage(int title, String image) {
        mToolbar.setTitle(title);
        if (image == null) {
            mActivity.getSupportActionBar().setIcon(new ColorDrawable(mActivity.getResources().getColor(android.R.color.transparent)));
        }
    }

    public void changeToolbarTitleAndImage(String title, int image) {
        mToolbar.setTitle(title);
        if (image == 0) {
            mActivity.getSupportActionBar().setIcon(new ColorDrawable(mActivity.getResources().getColor(android.R.color.transparent)));
        }
    }

    public void changeToolbarTitle(int title) {
        mToolbar.setTitle(title);
    }

    public void changeToolbarTitle(String title) {
        mToolbar.setTitle(title);

    }


}
