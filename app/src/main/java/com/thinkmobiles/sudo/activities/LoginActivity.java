package com.thinkmobiles.sudo.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import com.thinkmobiles.sudo.Main_Activity;
import com.thinkmobiles.sudo.R;
import com.thinkmobiles.sudo.core.rest.RetrofitAdapter;
import com.thinkmobiles.sudo.fragments.LoginFragment;
import com.thinkmobiles.sudo.fragments.RegistrationFragment;
import com.thinkmobiles.sudo.global.App;
import com.thinkmobiles.sudo.models.ProfileResponse;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by njakawaii on 09.04.2015.
 */
public class LoginActivity extends Activity {


    private Callback<ProfileResponse> mUserCB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        openLoginFragment();
        overridePendingTransition(R.anim.anim_flade_in, R.anim.anim_flade_out);
        initGetUserCB();
    }



    private void openLoginFragment() {
        getFragmentManager().beginTransaction().setCustomAnimations(android.R.animator.fade_in,android.R.animator.fade_out).add(R.id.flLoginContainer_AL, new LoginFragment()).commit();
    }
    public void openRegisterFragment() {
        getFragmentManager().beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.flLoginContainer_AL, new RegistrationFragment()).addToBackStack(null).commit();
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    public void goBack(){
        if(getFragmentManager().getBackStackEntryCount() == 0){
            finish();
        } else {
            getFragmentManager().popBackStack();
        }
    }

    private void initGetUserCB() {
        mUserCB = new Callback<ProfileResponse>() {
            @Override
            public void success(ProfileResponse profileResponse, Response response) {
                Log.d("user", profileResponse.getSuccess());
                setProfile(profileResponse);
                openMainActivity();

            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("user", error.getMessage());

            }
        };
    }

    private void setProfile(final ProfileResponse _profile) {
        App.setGetUserName(_profile.getUser().getEmail());
        App.setCurrentMobile(_profile.getUser().getMobile());
    }

    public void getUserRequest(){
        RetrofitAdapter.getInterface().getProfile(App.getuId(), mUserCB);
    }
    private void openMainActivity(){
        Intent intent = new Intent(this, Main_Activity.class);
        startActivity(intent);
        finish();
    }
}
