package com.thinkmobiles.sudo.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.thinkmobiles.sudo.R;

public class SettingsFragment extends Fragment implements View.OnClickListener{

    private Dialog mDialog;
    private View mView;
    private Activity mActivity;
    private TextView mPin, mNotification;
    private CheckBox mCbPin, mCbNotification;
    private Button mBlockNumber, mChangePassword, mBTNCancel, mBTNSubmit;
    private EditText mETNewPass, mETConfirmPass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_settings, container, false);
        initComponent();
        setListeners();
        return mView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    private void initComponent(){
        mPin                = (TextView) mView.findViewById(R.id.tvPin_FS);
        mNotification       = (TextView) mView.findViewById(R.id.tvNotification_FS);
        mCbPin              = (CheckBox) mView.findViewById(R.id.cbPin_FS);
        mCbNotification     = (CheckBox) mView.findViewById(R.id.cbNotification_FS);
        mBlockNumber        = (Button) mView.findViewById(R.id.btnBlockNumber_FS);
        mChangePassword     = (Button) mView.findViewById(R.id.btnChangePassword_FS);
    }

    private void setListeners() {
        mBlockNumber.setOnClickListener(this);
        mChangePassword.setOnClickListener(this);
    }

    private void initDialogComponent(){
        mETNewPass = (EditText) mView.findViewById(R.id.etDialogNewPassFS);
        mETConfirmPass = (EditText) mView.findViewById(R.id.etDialogConfirmPassFS);
        mBTNCancel = (Button) mView.findViewById(R.id.btnDialogCancelFS);
        mBTNSubmit = (Button) mView.findViewById(R.id.btnDialogSubmitFS);
    }

    private void setDialogListeners() {
        mBTNCancel.setOnClickListener(this);
        mBTNSubmit.setOnClickListener(this);
    }

    private void changePasswordDialog(){
        LayoutInflater inflater = mActivity.getLayoutInflater();
        mView = inflater.inflate(R.layout.dialog_change_password, null);
        mDialog = new Dialog(mActivity);
        mDialog.setContentView(mView);
        setDialogListeners();
        initDialogComponent();
        mDialog.setTitle(getResources().getString(R.string.ss_dialog_title));
        mDialog.show();
    }

    private boolean isValidateParam(EditText _et){
        String check = _et.getText().toString();
        return !check.isEmpty();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDialogCancelFS:
                mDialog.dismiss();
                break;
            case R.id.btnDialogSubmitFS:
                if (isValidateParam(mETNewPass) && isValidateParam(mETConfirmPass)) //TODO result OK
                break;
            case R.id.btnBlockNumber_FS:
                break;
            case R.id.btnChangePassword_FS:
                changePasswordDialog();
                break;
        }
    }
}
