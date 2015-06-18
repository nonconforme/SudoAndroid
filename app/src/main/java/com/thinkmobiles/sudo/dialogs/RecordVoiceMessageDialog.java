package com.thinkmobiles.sudo.dialogs;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.thinkmobiles.sudo.R;
import com.thinkmobiles.sudo.audio.VoiceRecorder;
import com.thinkmobiles.sudo.global.Constants;

import java.util.concurrent.TimeUnit;

/**
 * Created by omar on 03.06.15.
 */
public class RecordVoiceMessageDialog extends DialogFragment implements View.OnClickListener {


    public interface RecordVoiceMessageDialogCallback {
        void onSendVoiceMessage();
    }

    private String filePath;
    private Activity activity;
    private VoiceRecorder mVoiceRecorder;
    private boolean recording = false;
    private RecordVoiceMessageDialogCallback cb;
    private boolean hasRecording = false;
    private TextView tvTitleRD;
    private TextView tvTextRD;
    private ProgressBar pbDR;
    private AsyncTask<Void, Integer, Void> mPreRecordCounter;
    private AsyncTask<Void, Integer, Void> mRecordCounter;


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
        initAsyncTasks();
        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
        cb = (RecordVoiceMessageDialogCallback) activity;
    }

    private void findUI(View v) {
        ivDialogRecord_CF = (ImageView) v.findViewById(R.id.ivDialogRecord_CF);
        ivDialogSend_CF = (ImageView) v.findViewById(R.id.ivDialogSend_CF);
        tvTitleRD = (TextView) v.findViewById(R.id.tvTitle_RD);
        tvTextRD = (TextView) v.findViewById(R.id.tvText_RD);
        pbDR = (ProgressBar) v.findViewById(R.id.pb_DR);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivDialogRecord_CF:
                if (!recording) {
                    setupVoiceRecorder();
                    mPreRecordCounter.execute();
                    ivDialogRecord_CF.setImageResource(android.R.drawable.ic_menu_close_clear_cancel);
                    recording = true;
                    hasRecording = true;
                } else {
                    mVoiceRecorder.recordStop();

                    dismiss();
                }
                break;

            case R.id.ivDialogSend_CF:
                if (hasRecording) {
                    mVoiceRecorder.recordStop();
                    cb.onSendVoiceMessage();
                }
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

    private void initAsyncTasks() {
        mPreRecordCounter = new AsyncTask<Void, Integer, Void>() {
            int counter = 3;


            @Override
            protected Void doInBackground(Void... voids) {
                while (counter > 0 &&  recording) {
                    publishProgress(counter);
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    counter--;

                }
                return null;
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                tvTextRD.setText(activity.getString(R.string.recording_starts) + " " + String.valueOf(values[0]) + " " +
                        activity.getString(R.string.seconds));
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                mRecordCounter.execute();
            }
        };


        mRecordCounter = new AsyncTask<Void, Integer, Void>() {
            int counter = 0;

            @Override
            protected Void doInBackground(Void... voids) {
                mVoiceRecorder.recordStart();
                while (counter < 31 && recording) {
                    publishProgress(counter);
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    counter++;

                }

                return null;
            }

            @Override
            protected void onProgressUpdate(Integer... values) {

                tvTextRD.setText(activity.getString(R.string.recording) + " " + String.valueOf(values[0]) + " " +
                        activity
                        .getString(R.string.seconds));
                pbDR.setProgress(values[0]);
                pbDR.setMax(30);
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                mVoiceRecorder.recordStop();
                tvTextRD.setText(activity.getString(R.string.recorded));
            }
        };
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        recording = false;


        mRecordCounter = null;
        mPreRecordCounter = null;
    }


}

