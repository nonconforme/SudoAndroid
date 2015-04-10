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
import com.thinkmobiles.sudo.core.rest.RetrofitAdapter;
import com.thinkmobiles.sudo.models.LoginResponse;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Pavilion on 09.04.2015.
 */
public class RegistrationFragment extends Fragment implements View.OnClickListener{
    private View mView;
    private Activity mActivity;
    private EditText mETEmail, mETPassword, mETConfirmPass;
    private ImageView mImage;
    private Button mBTNSignUp;
    private Callback<LoginResponse> mSignUpCB;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_sign_up, container, false);
        initComponent();
        initSignUpCB();
        setListeners();
        return mView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;

    }

    private void setListeners() {
        mBTNSignUp.setOnClickListener(this);
    }

    private void initComponent(){
        mETEmail            = (EditText) mView.findViewById(R.id.etEmail_FSU);
        mETPassword         = (EditText) mView.findViewById(R.id.etPassword_FSU);
        mETConfirmPass      = (EditText) mView.findViewById(R.id.etConfirmPassword_FSU);
        mImage              = (ImageView) mView.findViewById(R.id.ivImage_FSU);
        mBTNSignUp          = (Button) mView.findViewById(R.id.btnSignUp_FSU);

    }

    private boolean isValidateParam(EditText _et){
        String check = _et.getText().toString();
        return !check.isEmpty();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSignUp_FSU:
                if (isValidateParam(mETEmail) && isValidateParam(mETPassword))   signUpRequest();
                break;

        }
    }

    private void signUpRequest() {
        RetrofitAdapter.getInterface().signUp(mETEmail.getText().toString(),
                mETPassword.getText().toString(), mSignUpCB);
    }

    private void openMainActivity(){
        Intent intent = new Intent(mActivity, MainActivity.class);
        mActivity.startActivity(intent);
    }

    private void initSignUpCB() {
        mSignUpCB = new Callback<LoginResponse>() {
            @Override
            public void success(LoginResponse loginResponse, Response response) {
                Log.d("signUp", loginResponse.getSuccess());
                Toast.makeText(mActivity, loginResponse.getSuccess(), Toast.LENGTH_SHORT).show();
                openMainActivity();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("signUp", error.getMessage());
                Toast.makeText(mActivity,  error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        };
    }
}
