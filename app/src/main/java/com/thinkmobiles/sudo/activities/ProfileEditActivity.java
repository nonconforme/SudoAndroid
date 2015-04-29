package com.thinkmobiles.sudo.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;

import com.squareup.picasso.Picasso;
import com.thinkmobiles.sudo.R;
import com.thinkmobiles.sudo.Utils;
import com.thinkmobiles.sudo.adapters.ProfileEditNumbersAdapter;
import com.thinkmobiles.sudo.core.rest.RetrofitAdapter;
import com.thinkmobiles.sudo.custom_views.NonScrollListView;
import com.thinkmobiles.sudo.models.DefaultResponseModel;
import com.thinkmobiles.sudo.models.addressbook.NumberModel;
import com.thinkmobiles.sudo.models.addressbook.UserModel;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by omar on 23.04.15.
 */
public class ProfileEditActivity extends BaseProfileEditActivity {


    private Callback<DefaultResponseModel> mUpdateContactCB;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_edit_profile;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        loadUserModel();
        loadContent();
        setContent();
        initAddContactCB();
        this.overridePendingTransition(R.anim.anim_edit_profile_slide_in, R.anim.anim_view_profile_slide_out);

    }

    public static void launch(Activity activity, UserModel userModel) {


        Intent intent = new Intent(activity, ProfileEditActivity.class);
        intent.putExtra(EXTRA_IMAGE, "https://unseenflirtspoetry.files.wordpress.com/2012/05/homer-excited.png");
        Bundle b = new Bundle();
        if (userModel != null) {
        b.putSerializable(BaseProfileActivity.USER_MODEL, userModel);
            intent.putExtra(BaseProfileActivity.USER_MODEL, b);
        }
        activity.startActivityForResult(intent, START_EDIT_PROFILE_ACTIVITY_CODE);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_view_profile_slide_in, R.anim.anim_edit_profile_slide_out);
    }

    private void initAddContactCB() {
        mUpdateContactCB = new Callback<DefaultResponseModel>() {
            @Override
            public void success(DefaultResponseModel defaultResponseModel, Response response) {
            }

            @Override
            public void failure(RetrofitError error) {
            }
        };
    }

    private void updateProfile(final UserModel _userModel){
        RetrofitAdapter.getInterface().updateContact(_userModel, _userModel.getCompanion(), mUpdateContactCB);
    }
}

