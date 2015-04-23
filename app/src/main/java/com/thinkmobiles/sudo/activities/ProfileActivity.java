package com.thinkmobiles.sudo.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import com.thinkmobiles.sudo.R;
import com.thinkmobiles.sudo.callbacks.ViewProfileFragmentCallback;
import com.thinkmobiles.sudo.fragments.ViewProfileFragment;
import com.thinkmobiles.sudo.models.addressbook.UserModel;

/**
 * Created by omar on 22.04.15.
 */
public class ProfileActivity extends ActionBarActivity implements ViewProfileFragmentCallback {


    private UserModel thisUserModel;


    public static final String USER_MODEL = "user_model";
    public static final String TAG = "profile view activity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "started");
        setContentView(R.layout.activity_profile);
        loadUserModel();
        setViewProfileFragment(thisUserModel);
    }

    private void loadUserModel() {
        thisUserModel = (UserModel) getIntent().getExtras().getBundle(ProfileActivity.USER_MODEL).getSerializable(ProfileActivity.USER_MODEL);
    }

    private void setViewProfileFragment(UserModel userModel) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, ViewProfileFragment.newInstance(userModel), "").commit();
    }

    private void setEditProdileFragment(UserModel userModel) {
    }

    @Override
    public void startEditProfileFragment() {
        setEditProdileFragment(thisUserModel);

    }
}
