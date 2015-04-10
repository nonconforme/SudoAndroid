package com.thinkmobiles.sudo.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;

import com.thinkmobiles.sudo.R;
import com.thinkmobiles.sudo.fragments.LoginFragment;
import com.thinkmobiles.sudo.fragments.RegistrationFragment;

/**
 * Created by njakawaii on 09.04.2015.
 */
public class LoginActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        openLoginFragment();

    }

    private void openLoginFragment() {
        getFragmentManager().beginTransaction().add(R.id.flLoginContainer_AL, new LoginFragment()).commit();
    }
    public void openRegisterFragment() {
        getFragmentManager().beginTransaction().replace(R.id.flLoginContainer_AL, new RegistrationFragment()).addToBackStack(null).commit();
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
    private void goBack(){
        if(getFragmentManager().getBackStackEntryCount() == 0){
            finish();
        } else {
            getFragmentManager().popBackStack();
        }
    }
}
