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

import java.util.concurrent.TimeUnit;

/**
 * Created by Pavilion on 09.04.2015.
 */
public class RegistrationFragment extends Fragment implements View.OnClickListener {
    private Activity mActivity;
    private Toast mToast;

    private View mView;
    private EditText mETEmail, mETPassword, mETConfirmPass;
    private Button mBTNSignUp;
    private static boolean showToast = true;

    private Callback<LoginResponse> mSignUpCB;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_sign_up, container, false);
        initComponent();
        initSignUpCB();
        setListeners();
        setColors();
        initToast();
        return mView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;

    }

    private void initToast() {
        mToast = new Toast(mActivity);
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
            showToast(mActivity.getString(R.string.password_cannot_be_empty));
            return false;
        }
        if (!p1.equals(p2)) {
            showToast(mActivity.getString(R.string.passwords_do_not_match));
            return false;
        }
        if (p1.length() < 6) {
            showToast(mActivity.getString(R.string.password_must_be_six_simbols_long));

            return false;
        }
        if (!p2.matches("\\w+")) {
              showToast(mActivity.getString(R.string.password_should_have_only_az_09));
            return false;

        }


        return true;
    }

    private boolean isValidEmail(EditText et) {
        String target = et.getText().toString();
        if (TextUtils.isEmpty(target) || !android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches()) {
            showToast(mActivity.getString(R.string.not_an_email));
             return false;

        } else {
            return true;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSignUp_FSU:


                if (isValidEmail(mETEmail) && isValidPassword(mETPassword, mETConfirmPass)) signUpRequest();
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
                showToast(mActivity.getString(R.string.user_created));

                ((LoginActivity) mActivity).goBack();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("signUp error", error.getMessage());

                showToast(mActivity.getString(R.string.user_already_ecists));


            }
        };
    }

    private void setColors() {
        ColorHelper.changeEditTextUnderlineColor(mETEmail);
        ColorHelper.changeEditTextUnderlineColor(mETPassword);
        ColorHelper.changeEditTextUnderlineColor(mETConfirmPass);
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
}
