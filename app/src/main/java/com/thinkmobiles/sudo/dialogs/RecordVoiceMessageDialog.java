package com.thinkmobiles.sudo.dialogs;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.thinkmobiles.sudo.global.Constants;

/**
 * Created by omar on 03.06.15.
 */
public class RecordVoiceMessageDialog extends DialogFragment {

    private String filePath;


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
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }
}
