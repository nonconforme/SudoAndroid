package com.thinkmobiles.sudo.activities;

/**
 * Created by njakawaii on 23.04.2015.
 */
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import com.thinkmobiles.sudo.R;


public abstract class BaseProfileActivity extends ActionBarActivity {


    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }


    protected abstract int getLayoutResource();


    protected void setActionBarIcon(int iconRes) {
        toolbar.setNavigationIcon(iconRes);
    }
    protected Toolbar getToolbar(){
        return toolbar;
    }
}