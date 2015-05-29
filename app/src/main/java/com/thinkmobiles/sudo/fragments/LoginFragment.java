package com.thinkmobiles.sudo.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.thinkmobiles.sudo.R;
import com.thinkmobiles.sudo.activities.LoginActivity;
import com.thinkmobiles.sudo.core.rest.RetrofitAdapter;
import com.thinkmobiles.sudo.global.App;
import com.thinkmobiles.sudo.global.SPAutoLogin;
import com.thinkmobiles.sudo.models.LoginResponse;
import com.thinkmobiles.sudo.utils.ColorHelper;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.util.concurrent.TimeUnit;

/**
 * Created by Pavilion on 09.04.2015.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {
    private Activity mActivity;

    private View mView;
    private EditText mETEmail, mETPassword;
    private Button mBTNSignIn, mBTNRegister;

    private Callback<LoginResponse> mSignInCB;
    private Toast mToast;

    private static boolean showToast = true;

    private void initToast() {
        mToast = new Toast(mActivity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_sign_in, container, false);
        initComponent();
        setListeners();
        initSignInCB();
        setColors();
        initToast();
        loadLogAndPass();
        return mView;
    }

    private void setColors() {
        ColorHelper.changeEditTextUnderlineColor(mETEmail);
        ColorHelper.changeEditTextUnderlineColor(mETPassword);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    private void initComponent() {
        mETEmail = (EditText) mView.findViewById(R.id.etEmail_FSI);
        mETPassword = (EditText) mView.findViewById(R.id.etPassword_FSI);
        mBTNSignIn = (Button) mView.findViewById(R.id.btnSignIn_FSI);
        mBTNRegister = (Button) mView.findViewById(R.id.btnRegister_FSI);

    }

    private boolean isValidateParam(EditText _et) {
        String check = _et.getText().toString();
        return !check.isEmpty();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnSignIn_FSI:
                if (isValidateParam(mETEmail) && isValidateParam(mETPassword)) loginRequest();
                break;
            case R.id.btnRegister_FSI:
                ((LoginActivity) mActivity).openRegisterFragment();
                break;
        }
    }

    private void loginRequest() {
        removeEmailSpaces();
        RetrofitAdapter.getInterface().signIn(mETEmail.getText().toString(), mETPassword.getText().toString(), mSignInCB);
    }


    private void initSignInCB() {
        mSignInCB = new Callback<LoginResponse>() {
            @Override
            public void success(LoginResponse loginResponse, Response response) {

                showToast(loginResponse.getSuccess());

                App.setuId(loginResponse.getuId());
                saveLogAndPass();
                ((LoginActivity) mActivity).getUserRequest();
            }

            @Override
            public void failure(RetrofitError error) {

                showToast(error.getMessage());

            }
        };
    }

    private void setListeners() {
        mBTNSignIn.setOnClickListener(this);
        mBTNRegister.setOnClickListener(this);
    }

    private void showToast(String text) {
        if (showToast) {
            mToast.makeText(mActivity, text, Toast.LENGTH_SHORT).show();
            showToast = false;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showToast = true;
                        }
                    });
                }
            }).start();
        }
    }


    private void saveLogAndPass() {

        String email = mETEmail.getText().toString();
        String pass = mETPassword.getText().toString();
        SPAutoLogin.storeLoginParams(mActivity, email, pass);

    }

    private void loadLogAndPass() {
        mETEmail.setText(SPAutoLogin.loadMail(mActivity));
        mETPassword.setText(SPAutoLogin.loadPassword(mActivity));
    }

    private void removeEmailSpaces() {

        String email = mETEmail.getText().toString();
        email = email.replaceAll("\\s+", "");
        mETEmail.setText(email);
    }

}
