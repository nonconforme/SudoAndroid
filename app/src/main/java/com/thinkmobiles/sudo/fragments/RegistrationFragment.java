package com.thinkmobiles.sudo.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.thinkmobiles.sudo.R;
import com.thinkmobiles.sudo.activities.LoginActivity;
import com.thinkmobiles.sudo.core.rest.RetrofitAdapter;
import com.thinkmobiles.sudo.models.LoginResponse;
import com.thinkmobiles.sudo.utils.ColorHelper;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Pavilion on 09.04.2015.
 */
public class RegistrationFragment extends Fragment implements View.OnClickListener {
    private Activity mActivity;

    private View mView;
    private EditText mETEmail, mETPassword, mETConfirmPass;
    private Button mBTNSignUp;

    private Callback<LoginResponse> mSignUpCB;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_sign_up, container, false);
        initComponent();
        initSignUpCB();
        setListeners();
        setColors();
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

    private void initComponent() {
        mETEmail = (EditText) mView.findViewById(R.id.etEmail_FSU);
        mETPassword = (EditText) mView.findViewById(R.id.etPassword_FSU);
        mETConfirmPass = (EditText) mView.findViewById(R.id.etConfirmPassword_FSU);
        mBTNSignUp = (Button) mView.findViewById(R.id.btnSignUp_FSU);
    }


    private boolean isValidPassword(EditText _et1, EditText _et2) {
        String p1 = _et1.getText().toString();
        String p2 = _et2.getText().toString();
        if (p1.isEmpty() || p2.isEmpty()) {
            Toast.makeText(mActivity, mActivity.getString(R.string.password_cannot_be_empty), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!p1.equals(p2)) {
            Toast.makeText(mActivity, mActivity.getString(R.string.passwords_do_not_match), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (p1.length() < 6) {
            Toast.makeText(mActivity, mActivity.getString(R.string.password_must_be_six_simbols_long), Toast.LENGTH_SHORT).show();
            return false;
        }
       if(! p2.matches("\\w+")){
           Toast.makeText(mActivity, mActivity.getString(R.string.password_should_have_only_az_09), Toast.LENGTH_SHORT)
                   .show();
           return false;

       }


        return true;
    }

    private boolean isValidEmail(EditText et){
        String target = et.getText().toString();
        if (TextUtils.isEmpty(target) || !android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches()) {
            Toast.makeText(mActivity, mActivity.getString(R.string.not_an_email), Toast.LENGTH_SHORT).show();
            return false;

        } else {
            return true;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSignUp_FSU:
                if (isValidEmail(mETEmail )&& isValidPassword(mETPassword, mETConfirmPass)) signUpRequest();
                break;

        }
    }

    private void signUpRequest() {
        RetrofitAdapter.getInterface().signUp(mETEmail.getText().toString(), mETPassword.getText().toString(), mSignUpCB);
    }

    private void initSignUpCB() {
        mSignUpCB = new Callback<LoginResponse>() {
            @Override
            public void success(LoginResponse loginResponse, Response response) {
                Log.d("signUp", loginResponse.getSuccess());
                Toast.makeText(mActivity, loginResponse.getSuccess(), Toast.LENGTH_SHORT).show();
                ((LoginActivity) mActivity).goBack();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("signUp", error.getMessage());
                Toast.makeText(mActivity, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        };
    }

    private void setColors() {
        ColorHelper.changeEditTextUnderlineColor(mETEmail);
        ColorHelper.changeEditTextUnderlineColor(mETPassword);
        ColorHelper.changeEditTextUnderlineColor(mETConfirmPass);
    }
}
