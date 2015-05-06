package com.thinkmobiles.sudo.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.thinkmobiles.sudo.R;
import com.thinkmobiles.sudo.ToolbarManager;
import com.thinkmobiles.sudo.core.rest.RetrofitAdapter;
import com.thinkmobiles.sudo.models.DefaultResponseModel;
import com.thinkmobiles.sudo.models.addressbook.UserModel;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by omar on 23.04.15.
 */
public class ProfileEditActivity extends BaseProfileEditActivity {


    private Callback<DefaultResponseModel> mUpdateContactCB;
    private String oldName;
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
        ToolbarManager.getInstance(this).changeToolbarTitle(oldName);

    }

    @Override
    protected void loadUserModel() {
        super.loadUserModel();
        oldName = thisUserModel.getCompanion();
    }

    @Override
    protected void returnEditedProfile() {
        if (checkNewName() && checkNewPhone()) {
            updateProfile(oldName, thisUserModel);
            Intent intent = new Intent();
            Bundle b = new Bundle();
            b.putSerializable(BaseProfileActivity.USER_MODEL, thisUserModel);
            intent.putExtra(BaseProfileActivity.USER_MODEL, b);
            setResult(RESULT_OK, intent);
            onBackPressed();

        }
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

    private void updateProfile(final String _oldName, final UserModel _userModel){
        RetrofitAdapter.getInterface().updateContact(_userModel, _oldName, mUpdateContactCB);
    }
}

