package com.thinkmobiles.sudo.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.thinkmobiles.sudo.MainActivity;
import com.thinkmobiles.sudo.R;
import com.thinkmobiles.sudo.activities.LoginActivity;
import com.thinkmobiles.sudo.core.rest.RetrofitAdapter;
import com.thinkmobiles.sudo.models.LoginResponse;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Pavilion on 09.04.2015.
 */
public class LoginFragment extends Fragment implements View.OnClickListener{
    private Activity    mActivity;
    private View        mView;
    private ImageView   mImage;
    private EditText    mETEmail, mETPassword;
    private Button      mBTNSignIn, mBTNGoRegistration;
    private Callback<LoginResponse> mSignInCB;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_sign_in, container, false);
        initComponent();
        setListeners();
        initSignInCB();
        return mView;
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    private void initComponent(){
        mImage              = (ImageView) mView.findViewById(R.id.ivImage_FSI);
        mETEmail            = (EditText) mView.findViewById(R.id.etEmail_FSI);
        mETPassword         = (EditText) mView.findViewById(R.id.etPassword_FSI);
        mBTNSignIn          = (Button) mView.findViewById(R.id.btnSignIn_FSI);
        mBTNGoRegistration  = (Button) mView.findViewById(R.id.btnGoRegistation_FSI);

    }

    private boolean isValidateParam(EditText _et){
        String check = _et.getText().toString();
        return !check.isEmpty();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSignIn_FSI:
                if (isValidateParam(mETEmail) && isValidateParam(mETPassword))   loginRequest();
                break;
            case R.id.btnGoRegistation_FSI:
                ((LoginActivity) mActivity).openRegisterFragment();
                break;
        }
    }

    private void loginRequest() {
        RetrofitAdapter.getInterface().signIn(mETEmail.getText().toString(),
                mETPassword.getText().toString(), mSignInCB);
    }

    private void openMainActivity(){
        Intent intent = new Intent(mActivity, MainActivity.class);
        mActivity.startActivity(intent);
    }

    private void initSignInCB() {
        mSignInCB = new Callback<LoginResponse>() {
            @Override
            public void success(LoginResponse loginResponse, Response response) {
                Log.d("signIn", loginResponse.getSuccess());
                Toast.makeText(mActivity,  loginResponse.getSuccess(), Toast.LENGTH_SHORT).show();
                openMainActivity();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("signIn", error.getMessage());
                Toast.makeText(mActivity,  error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        };
    }

    private void setListeners() {
        mBTNSignIn.setOnClickListener(this);
        mBTNGoRegistration.setOnClickListener(this);
    }
}
