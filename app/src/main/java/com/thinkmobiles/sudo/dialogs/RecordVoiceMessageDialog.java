package com.thinkmobiles.sudo.dialogs;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.thinkmobiles.sudo.R;
import com.thinkmobiles.sudo.audio.VoiceRecorder;
import com.thinkmobiles.sudo.global.Constants;

/**
 * Created by omar on 03.06.15.
 */
public class RecordVoiceMessageDialog extends DialogFragment implements View.OnClickListener {


    public interface  RecordVoiceMessageDialogCallback{
        void onSendVoiceMessage();
    }

    private String filePath;
    private Activity activity;
    private VoiceRecorder mVoiceRecorder;
    private boolean recording = false;
    private RecordVoiceMessageDialogCallback cb;


    private ImageView ivDialogRecord_CF, ivDialogSend_CF;


    public static RecordVoiceMessageDialog newInstance(final String filePath) {
        RecordVoiceMessageDialog numberFragment = new RecordVoiceMessageDialog();
        Bundle bundle = new Bundle();

        bundle.putString(Constants.FILEPATH, filePath);
        numberFragment.setArguments(bundle);
        return numberFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        filePath = getArguments().getString(Constants.FILEPATH);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_record_voice, container, false);
        findUI(v);
        setListeners();
        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
        cb = (RecordVoiceMessageDialogCallback)activity;
    }

    private void findUI(View v) {
        ivDialogRecord_CF = (ImageView) v.findViewById(R.id.ivDialogRecord_CF);
        ivDialogSend_CF = (ImageView) v.findViewById(R.id.ivDialogSend_CF);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivDialogRecord_CF:
                if (!recording) {
                    setupVoiceRecorder();
                    mVoiceRecorder.recordStart();
                    ivDialogRecord_CF.setImageResource(android.R.drawable.ic_menu_close_clear_cancel);
                    recording = true;
                }
                else{
                    mVoiceRecorder.recordStop();

                    dismiss();
                }
                break;

            case R.id.ivDialogSend_CF:
                mVoiceRecorder.recordStop();
                cb.onSendVoiceMessage();
                dismiss();
                break;


        }

    }

    private void setListeners() {
        ivDialogRecord_CF.setOnClickListener(this);
        ivDialogSend_CF.setOnClickListener(this);
    }


    private void setupVoiceRecorder() {
        mVoiceRecorder = new VoiceRecorder(filePath);
    }



}

