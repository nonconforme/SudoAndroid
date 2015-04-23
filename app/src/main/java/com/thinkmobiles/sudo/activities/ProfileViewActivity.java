package com.thinkmobiles.sudo.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.thinkmobiles.sudo.R;

/**
 * Created by omar on 22.04.15.
 */
public class ProfileViewActivity extends ActionBarActivity {
    private Toolbar toolbar;
    private ImageButton btnHome, btnAccept;
    private View.OnClickListener onToolbarClickListener;

    public static final int PROFILE_VIEW_ACTIVITY_ID = 1;
    public static final String USER_MODEL = "user_model";
    public static final String TAG = "profile view activity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "started");
        setContentView(R.layout.activity_view_profile);

        initToolbar();
        initComponent();
        initListeners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.profileViewToolBar);

        setSupportActionBar(toolbar);

        btnHome = (ImageButton) findViewById(R.id.ibHome_AVC);
        btnAccept = (ImageButton) findViewById(R.id.ibEdit_AVC);

    }

    private void initComponent() {
    }

    private void initListeners() {
        initToolbarOnCLickListener();


    }

    private void initToolbarOnCLickListener() {
        onToolbarClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.ibHome_AVC:
                        Toast.makeText(ProfileViewActivity.this, "Home clicked", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                        break;
                    case    R.id.ibEdit_AVC:
                        Toast.makeText(ProfileViewActivity.this, "Home EditClicked", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
        btnHome.setOnClickListener(onToolbarClickListener);
        btnAccept.setOnClickListener(onToolbarClickListener);
    }
}
