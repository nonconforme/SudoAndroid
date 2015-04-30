package com.thinkmobiles.sudo;


import android.support.v7.widget.Toolbar;


/**
 * Created by omar on 30.04.15.
 */
public class ToolbarManager {

    private static Main_Activity mMain_Activity;
    private static Toolbar mToolbar;
    private static ToolbarManager sToolbarManager;

    private ToolbarManager() {

    }

    public static ToolbarManager getInstance(Main_Activity _actionBarActivity) {
        if (sToolbarManager == null) {
            sToolbarManager = new ToolbarManager();
            mMain_Activity = _actionBarActivity;
            initToolbar();
        } else {
            mMain_Activity = _actionBarActivity;
            initToolbar();
        }
        return sToolbarManager;
    }

    private static void initToolbar() {
        if (sToolbarManager != null) {
            mToolbar = (Toolbar) mMain_Activity.findViewById(R.id.tool_bar);
            mMain_Activity.setSupportActionBar(mToolbar);
            mMain_Activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            mMain_Activity.getSupportActionBar().setHomeButtonEnabled(true);
            mMain_Activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
            mMain_Activity.getSupportActionBar().setIcon(mMain_Activity.getResources().getDrawable(R.drawable.ic_launcher));

        }
    }

    public Toolbar getToolbar() {
        return mToolbar;
    }

    public void setTitlie(String title) {
        mToolbar.setTitle(title);
    }

    public void enableDrawer() {
        mMain_Activity.enableDrawer(true);


    }

    public void disableDrawer() {
        mMain_Activity.enableDrawer(false);


    }
}
