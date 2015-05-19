package com.thinkmobiles.sudo.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

import com.thinkmobiles.sudo.R;
import com.thinkmobiles.sudo.core.rest.RetrofitAdapter;
import com.thinkmobiles.sudo.custom_views.RevealBackgroundView;
import com.thinkmobiles.sudo.fragments.ContactsFragment;
import com.thinkmobiles.sudo.global.Constants;
import com.thinkmobiles.sudo.models.DefaultResponseModel;
import com.thinkmobiles.sudo.models.addressbook.UserModel;
import com.thinkmobiles.sudo.utils.JsonHelper;
import com.thinkmobiles.sudo.utils.ToolbarManager;
import com.thinkmobiles.sudo.utils.Utils;


import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by omar on 23.04.15.
 */
public class ProfileAddActivity extends BaseProfileEditActivity implements RevealBackgroundView.OnStateChangeListener {
    public static final String ARG_REVEAL_START_LOCATION = "reveal_start_location";

    private RevealBackgroundView vRevealBackground;
    private RelativeLayout rlMain;
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
        findUI();
        addNewNumber();
        setupRevealBackground(savedInstanceState);

    }

    public static void startCameraFromLocation(int[] startingLocation, Activity startingActivity) {
        Intent intent = new Intent(startingActivity, ProfileAddActivity.class);
        intent.putExtra(ARG_REVEAL_START_LOCATION, startingLocation);
        startingActivity.startActivity(intent);
    }

    private void findUI(){
        vRevealBackground = (RevealBackgroundView) findViewById(R.id.rlTest);
        rlMain = (RelativeLayout) findViewById(R.id.rlMain);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void setupRevealBackground(Bundle savedInstanceState) {
        vRevealBackground.setFillPaintColor(getResources().getColor(R.color.colorAddFriend));
        vRevealBackground.setOnStateChangeListener(this);
        if (savedInstanceState == null) {
            final int[] startingLocation = getIntent().getIntArrayExtra(ARG_REVEAL_START_LOCATION);
            vRevealBackground.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    vRevealBackground.getViewTreeObserver().removeOnPreDrawListener(this);
                    vRevealBackground.startFromLocation(startingLocation);
                    return true;
                }

            });
        } else {
            vRevealBackground.setToFinishedFrame();
        }
    }



    @Override
    protected void returnEditedProfile() {
        addProfile(mUserModel);
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        ViewCompat.setElevation(getToolbar(), 0);
        rlMain.animate()
                .translationY(Utils.getScreenHeight(this))
                .setDuration(200)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        finish();
                        overridePendingTransition(0, 0);
                    }
                })
                .start();

    }


    private void addProfile(final UserModel _userModel) {
        RetrofitAdapter.getInterface().addContact(JsonHelper.makeJson(_userModel), mAddContactCB);
    }

    private void initAddContactCB() {
        mAddContactCB = new Callback<DefaultResponseModel>() {
            @Override
            public void success(DefaultResponseModel defaultResponseModel, Response response) {
                reloadContactList();
            }

            @Override
            public void failure(RetrofitError error) {
            }
        };
    }

    @Override
    public void onStateChange(int state) {
        if (RevealBackgroundView.STATE_FINISHED == state) {
            rlMain.setVisibility(View.VISIBLE);
            rlMain.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            initToolBar();
        } else {
            rlMain.setVisibility(View.INVISIBLE);
        }
    }

    private void initToolBar(){
        ToolbarManager.getInstance(this).getToolbar().setBackgroundColor(getResources().getColor(R.color.colorAddFriend));
        setStatusBarColor(getResources().getColor(R.color.colorAddFriendDark));
        setTitle(getResources().getString(R.string.add_profile));
    }

    private void reloadContactList(){
        sendSearchBroadcastQuery("");
    }

    private void sendSearchBroadcastQuery(String query) {
        Intent broadcastIntent = new Intent(Constants.QUERRY);
        broadcastIntent.putExtra(Constants.QUERRY, query);
        sendBroadcast(broadcastIntent);
    }
}

