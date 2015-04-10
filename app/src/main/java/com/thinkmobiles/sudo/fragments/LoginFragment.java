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
public class LoginFragment extends Fragment implements View.OnClickListener{
    private Activity    mActivity;
    private View        mView;
    private ImageView   mImage;
    private EditText    mETEmail, mETPassword;
    private Button      mBTNSignIn, mBTNGoRegistration;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_sign_in, container, false);
        initComponent();
        return mView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    public void initComponent(){
        mImage              = (ImageView) mView.findViewById(R.id.ivImage_FSI);
        mETEmail            = (EditText) mView.findViewById(R.id.etEmail_FSI);
        mETPassword         = (EditText) mView.findViewById(R.id.etPassword_FSI);
        mBTNSignIn          = (Button) mView.findViewById(R.id.btnSignIn_FSI);
        mBTNGoRegistration  = (Button) mView.findViewById(R.id.btnGoRegistation_FSI);

        mBTNSignIn.setOnClickListener(this);
        mBTNGoRegistration.setOnClickListener(this);
    }

    public void checkIsEmpty(EditText _et){
        String check = _et.getText().toString();
        if (check.isEmpty()) {
            Toast.makeText(mActivity, "You did not enter a text", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
       if (v.getId() == R.id.btnSignIn_FSI){
           checkIsEmpty(mETEmail);
           checkIsEmpty(mETPassword);
       } else if (v.getId() == R.id.btnGoRegistation_FSI){
           //TODO go to Registration fragment
       }
    }
}
