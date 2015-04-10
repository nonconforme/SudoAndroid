package com.thinkmobiles.sudo.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.thinkmobiles.sudo.R;

/**
 * Created by Pavilion on 09.04.2015.
 */
public class RegistrationFragment extends Fragment implements View.OnClickListener{
    private View mView;
    private Activity mActivity;
    private EditText mETEmail, mETPassword, mETConfirmPass;
    private ImageView mImage;
    private Button mBTNSignUp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_sign_up, container, false);
        initComponent();
        return mView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    public void initComponent(){
        mETEmail = (EditText) mView.findViewById(R.id.etEmail_FSU);
        mETPassword = (EditText) mView.findViewById(R.id.etPassword_FSU);
        mETConfirmPass = (EditText) mView.findViewById(R.id.etConfirmPassword_FSU);
        mImage = (ImageView) mView.findViewById(R.id.ivImage_FSU);
        mBTNSignUp = (Button) mView.findViewById(R.id.btnSignUp_FSU);

        mBTNSignUp.setOnClickListener(this);
    }

    public void checkIsEmpty(EditText _et){
        String check = _et.getText().toString();
        if (check.isEmpty()) {
            Toast.makeText(mActivity, "You did not enter a text", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnSignUp_FSU){
            checkIsEmpty(mETEmail);
            checkIsEmpty(mETPassword);
            checkIsEmpty(mETConfirmPass);
        }
    }
}
