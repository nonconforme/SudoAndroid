package com.thinkmobiles.sudo.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import com.thinkmobiles.sudo.R;
import com.thinkmobiles.sudo.core.rest.RetrofitAdapter;
import com.thinkmobiles.sudo.models.DefaultResponseModel;
import com.thinkmobiles.sudo.models.addressbook.UserModel;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by omar on 23.04.15.
 */
public class ProfileAddActivity extends BaseProfileEditActivity {


    private Callback<DefaultResponseModel> mAddContactCB;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_edit_profile;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUserModel = new UserModel();
        initAddContactCB();
        getToolbar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));


    }

    @Override
    protected void returnEditedProfile() {
        if (checkNewName() && checkNewPhone()) {
            addProfile(mUserModel);
            onBackPressed();

        }
    }

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, ProfileAddActivity.class);
        activity.startActivityForResult(intent, START_EDIT_PROFILE_ACTIVITY_CODE);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    private void addProfile(final UserModel _userModel) {
        RetrofitAdapter.getInterface().addContact(_userModel, mAddContactCB);
    }

    private void initAddContactCB() {
        mAddContactCB = new Callback<DefaultResponseModel>() {
            @Override
            public void success(DefaultResponseModel defaultResponseModel, Response response) {
            }

            @Override
            public void failure(RetrofitError error) {
            }
        };
    }

}

