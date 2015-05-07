package com.thinkmobiles.sudo.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.thinkmobiles.sudo.R;
import com.thinkmobiles.sudo.ToolbarManager;
import com.thinkmobiles.sudo.core.rest.RetrofitAdapter;
import com.thinkmobiles.sudo.models.DefaultResponseModel;
import com.thinkmobiles.sudo.models.UpdateProfileModel;
import com.thinkmobiles.sudo.models.addressbook.UserModel;
import com.thinkmobiles.sudo.utils.JsonHelper;

import java.io.UnsupportedEncodingException;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by omar on 23.04.15.
 */
public class ProfileEditActivity extends BaseProfileEditActivity {


    private Callback<UpdateProfileModel> mUpdateContactCB;
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
        setDefaultColor();
        setContent();
        initUpdateContactCB();
        this.overridePendingTransition(R.anim.anim_edit_profile_slide_in, R.anim.anim_view_profile_slide_out);
        ToolbarManager.getInstance(this).changeToolbarTitle(oldName);

    }

    @Override
    protected void loadUserModel() {
        super.loadUserModel();
        oldName = mUserModel.getCompanion();
    }



    @Override
    protected void returnEditedProfile() {
        if (checkNewName() && checkNewPhone()) {

            try {
                updateProfile(oldName, mUserModel);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }


        }
    }

    public static void launch(Activity activity, UserModel userModel) {

        Intent intent = new Intent(activity, ProfileEditActivity.class);
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

    private void initUpdateContactCB() {
        mUpdateContactCB = new Callback<UpdateProfileModel>() {
            @Override
            public void success(UpdateProfileModel updateProfileModel, Response response) {
                mUserModel.setAvatar(updateProfileModel.getAvatar());
                Intent intent = new Intent();
                Bundle b = new Bundle();
                b.putSerializable(BaseProfileActivity.USER_MODEL, mUserModel);
                intent.putExtra(BaseProfileActivity.USER_MODEL, b);
                setResult(RESULT_OK, intent);
                onBackPressed();
            }

            @Override
            public void failure(RetrofitError error) {
            }
        };
    }

    private void updateProfile(final String _oldName, final UserModel _userModel) throws UnsupportedEncodingException {
        RetrofitAdapter.getInterface().updateContact(JsonHelper.makeJson(_userModel), _oldName, mUpdateContactCB);

    }
}

