package com.thinkmobiles.sudo.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.transition.Explode;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thinkmobiles.sudo.R;
import com.thinkmobiles.sudo.ToolbarManager;
import com.thinkmobiles.sudo.core.rest.RetrofitAdapter;
import com.thinkmobiles.sudo.models.DefaultResponseModel;
import com.thinkmobiles.sudo.models.addressbook.UserModel;
import com.thinkmobiles.sudo.utils.JsonHelper;
import com.thinkmobiles.sudo.utils.TypedJsonString;

import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;
import io.codetail.widget.RevealFrameLayout;
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
        getWindow().setEnterTransition( new Explode() );
        getWindow().setExitTransition( new Explode() );;
        mUserModel = new UserModel();
        initAddContactCB();
        getToolbar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
        ToolbarManager.getInstance(this).changeToolbarTitleAndIcon(R.string.add_profile, 0);
        ToolbarManager.getInstance(this).getToolbar().setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        addNewNumber();
//        RevealFrameLayout myView = (RevealFrameLayout) findViewById(R.id.awesome_card);
//
//        // get the center for the clipping circle
//        int cx = (myView.getLeft() + myView.getRight()) / 2;
//        int cy = (myView.getTop() + myView.getBottom()) / 2;
//
//        // get the final radius for the clipping circle
//        int finalRadius = Math.max(myView.getWidth(), myView.getHeight());
//
//        SupportAnimator animator =
//                ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0, finalRadius);
//        animator.setInterpolator(new AccelerateDecelerateInterpolator());
//        animator.setDuration(1500);
//        animator.start();

    }

    @Override
    protected void returnEditedProfile() {
            addProfile(mUserModel);
            onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    private void addProfile(final UserModel _userModel) {

        RetrofitAdapter.getInterface().addContact(JsonHelper.makeJson(_userModel), mAddContactCB);
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

