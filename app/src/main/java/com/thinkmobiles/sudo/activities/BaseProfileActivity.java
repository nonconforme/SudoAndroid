package com.thinkmobiles.sudo.activities;

/**
 * Created by njakawaii on 23.04.2015.
 */

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import com.thinkmobiles.sudo.R;
import com.thinkmobiles.sudo.global.Network;
import com.thinkmobiles.sudo.utils.Utils;


public abstract class BaseProfileActivity extends ActionBarActivity {

    private Toolbar toolbar;
    public static final String USER_MODEL       = "user_model";
    public static final String TAG              = "profile view activity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
        toolbar = (Toolbar) findViewById(R.id.toolbar);

            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    protected abstract int getLayoutResource();


    protected ActionBar getToolbarAB(){
        return getSupportActionBar();
    }
    protected Toolbar getToolbar(){
        return toolbar;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    protected void setStatusBarColor(final int _statusBarColor){
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        getWindow().setStatusBarColor(_statusBarColor);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Network.isInternetConnectionAvailable(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

    }
}