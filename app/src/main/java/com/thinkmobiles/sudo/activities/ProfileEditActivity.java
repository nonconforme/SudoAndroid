package com.thinkmobiles.sudo.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.thinkmobiles.sudo.R;
import com.thinkmobiles.sudo.ToolbarManager;
import com.thinkmobiles.sudo.core.rest.RetrofitAdapter;
import com.thinkmobiles.sudo.global.ProgressDialogWorker;
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
        try {
            if (isProfileChangesValid()){
                updateProfile(oldName, mUserModel);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_view_profile_slide_in, R.anim.anim_edit_profile_slide_out);
        finish();
    }

    private void initUpdateContactCB() {
        mUpdateContactCB = new Callback<UpdateProfileModel>() {
            @Override
            public void success(UpdateProfileModel updateProfileModel, Response response) {
                mUserModel.setAvatar(updateProfileModel.getAvatar());
                sendResult(mUserModel);
            }

            @Override
            public void failure(RetrofitError error) {
                ProgressDialogWorker.dismissDialog();

            }
        };
    }

    private void sendResult(final UserModel profileModel){
        Intent intent = new Intent();
        Bundle b = new Bundle();
        if (profileModel != null) {
            b.putSerializable(BaseProfileActivity.USER_MODEL, profileModel);
            intent.putExtra(BaseProfileActivity.USER_MODEL, b);
        }
        setResult(RESULT_OK, intent);
        finish();
        overridePendingTransition(R.anim.anim_view_profile_slide_in, R.anim.anim_edit_profile_slide_out);
        ProgressDialogWorker.dismissDialog();
    }

    private void updateProfile(final String _oldName, final UserModel _userModel) throws UnsupportedEncodingException {
        ProgressDialogWorker.createDialog(this);
        RetrofitAdapter.getInterface().updateContact(JsonHelper.makeJson(_userModel), _oldName, mUpdateContactCB);

    }
}

