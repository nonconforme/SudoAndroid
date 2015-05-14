package com.thinkmobiles.sudo.utils;


import android.app.Activity;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import com.thinkmobiles.sudo.Main_Activity;
import com.thinkmobiles.sudo.R;


/**
 * Created by omar on 30.04.15.
 */
public class MainToolbarManager extends ToolbarManager {

    private static Main_Activity mMain_Activity;

    private static MainToolbarManager sMainToolbarManager;


    public static MainToolbarManager getCustomInstance(Activity _main_Activity) {
        if (sMainToolbarManager == null) {
            sMainToolbarManager = new MainToolbarManager();
            mMain_Activity = (Main_Activity) _main_Activity;
            initMainToolbar();
        } else {
            mMain_Activity = (Main_Activity) _main_Activity;
            initMainToolbar();
        }
        setmActivity((ActionBarActivity) _main_Activity);
        return sMainToolbarManager;
    }


    public void enableDrawer(boolean show) {
        mMain_Activity.enableDrawer(show);


    }

    public void enableSearchView(boolean show) {
        mMain_Activity.enableSearchView(show);


    }

    public void enableTrachView(boolean show){
        mMain_Activity.enableTrashView(show);
    }


    public void setProgressBarVisible(boolean _visible) {
        mMain_Activity.setProgressBarVisible(_visible);
    }

    public static void initMainToolbar() {
        if (sMainToolbarManager != null) {
            mToolbar = (Toolbar) mMain_Activity.findViewById(R.id.tool_bar);
            mMain_Activity.setSupportActionBar(mToolbar);
            mMain_Activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            mMain_Activity.getSupportActionBar().setHomeButtonEnabled(true);
            mMain_Activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }
}
