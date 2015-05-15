package com.thinkmobiles.sudo.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.thinkmobiles.sudo.R;

/**
 * Created by omar on 15.05.15.
 */
public class BuyCreditsDialog extends DialogFragment {
    View view;

    private Activity activity;
    private Button btnOK;
    private TextView tvCredits;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().setTitle(activity.getString(R.string.buy_credits_dialog_title));

        view = activity.getLayoutInflater().inflate(R.layout.dialog_buy_creadits, container, false);
        findUI();
        return view;
    }

    private void findUI() {
        btnOK = (Button)view.findViewById(R.id.btnOK_DBC);
        tvCredits = (TextView)view.findViewById(R.id.tvCreadits_DBC);
    }

    public static void getInstance(String resource) {
        BuyCreditsDialog dialog = new BuyCreditsDialog();
        Bundle arguments = new Bundle();

        dialog.setArguments(arguments);
    }

}
