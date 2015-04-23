package com.thinkmobiles.sudo.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import com.thinkmobiles.sudo.models.addressbook.UserModel;

/**
 * Created by omar on 22.04.15.
 */
public class ProfileActivity extends ActionBarActivity {


    private UserModel thisUserModel;


    public static final String USER_MODEL = "user_model";
    public static final String TAG = "profile view activity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "started");
        loadUserModel();

    }

    private void loadUserModel() {
        thisUserModel = (UserModel) getIntent().getExtras().getBundle(ProfileActivity.USER_MODEL).getSerializable(ProfileActivity.USER_MODEL);
    }



}
