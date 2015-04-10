package com.thinkmobiles.sudo.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.thinkmobiles.sudo.MainActivity;
import com.thinkmobiles.sudo.R;
import com.thinkmobiles.sudo.core.rest.RetrofitAdapter;
import com.thinkmobiles.sudo.models.DefaultResponseModel;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SplashScreen extends Activity {

    private Callback<DefaultResponseModel> mAuthenticatedCB;
    private Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        setNextActivity(LoginActivity.class);
        initAuthenticatedCB();
        authenticatedRequest();
    }

    private void initAuthenticatedCB() {
        mAuthenticatedCB = new Callback<DefaultResponseModel>() {
            @Override
            public void success(DefaultResponseModel defaultResponseModel, Response response) {
                Log.d("authenticated", defaultResponseModel.getSuccess());
                setNextActivity(MainActivity.class);
                openActivity();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("authenticated", error.getMessage());
                if (error.getResponse().getStatus() == 401){
                    setNextActivity(LoginActivity.class);
                    openActivity();
                }

            }
        };
    }

    private void authenticatedRequest() {
        RetrofitAdapter.getInterface().isAuthenticated(mAuthenticatedCB);
    }

    private void showNewActivity(){
        ScheduledExecutorService worker =
                Executors.newSingleThreadScheduledExecutor();
        Runnable task = new Runnable() {
            public void run() {
                openActivity();
            }
        };
        worker.schedule(task, 2, TimeUnit.SECONDS);
    }

    private void openActivity() {
        startActivity(mIntent);
        finish();
    }

    private void setNextActivity(Class _class){
        mIntent = new Intent(this, _class);
    }
}
