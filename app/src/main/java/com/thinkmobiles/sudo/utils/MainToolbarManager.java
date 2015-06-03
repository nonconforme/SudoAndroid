package com.thinkmobiles.sudo.utils;


import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import com.thinkmobiles.sudo.Main_Activity;
import com.thinkmobiles.sudo.R;
import com.thinkmobiles.sudo.global.App;


/**
 * Created by omar on 30.04.15.
 */
public class MainToolbarManager extends ToolbarManager {

    private static Main_Activity mMain_Activity;
    private static MainToolbarManager sMainToolbarManager;
    private boolean showSearchView = false;
    private boolean showTrachView = false;
    private boolean showListDrawer = false;
    private boolean showDrawer = true;

    public boolean isShowDrawer() {
        return showDrawer;
    }

    public void setShowDrawer(boolean showDrawer) {
        this.showDrawer = showDrawer;
    }


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


    public boolean isShowSearchView() {
        return showSearchView;
    }

    public boolean isShowTrachView() {
        return showTrachView;
    }

    public boolean isShowListDrawer() {
        return showListDrawer;
    }

    public void setShowListDrawer(boolean showListDrawer) {
        this.showListDrawer = showListDrawer;
    }

    public void enableDrawer(boolean show) {
        mMain_Activity.enableDrawer(show);
    }

    public void enableSearchView(boolean show) {
        showSearchView = show;
    }

    public void enableTrashView(boolean show) {
        showTrachView = show;
    }

    public void setProgressBarVisible(boolean _visible) {
        mMain_Activity.setProgressBarVisible(_visible);
    }

    public static void initMainToolbar() {
        if (sMainToolbarManager != null) {
            mToolbar = (Toolbar) mMain_Activity.findViewById(R.id.tool_bar);
            mToolbar.setTitle(R.string.app_name);
            mMain_Activity.setSupportActionBar(mToolbar);
            mMain_Activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            mMain_Activity.getSupportActionBar().setHomeButtonEnabled(true);
            mMain_Activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    public void reloadOptionsMenu() {
        mMain_Activity.invalidateOptionsMenu();
        setToolbarIcon(App.getCurrentISO());
    }
}
