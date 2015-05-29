package com.thinkmobiles.sudo.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.thinkmobiles.sudo.Main_Activity;
import com.thinkmobiles.sudo.R;
import com.thinkmobiles.sudo.global.App;

/**
 * Created by omar on 12.05.15.
 */
public class AvailableCreditsFragment extends Fragment {

    Main_Activity main_activity;
    TextView tvCredits;
    View mView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_available_credits, container, false);
        tvCredits = (TextView) mView.findViewById(R.id.tvCredits_CAF);
        setCredits();
        return mView;
    }

    public  void setCredits(){
        tvCredits.setText(App.getCurrentCredits() + getActivity().getString(R.string.CREDITS));
        main_activity.refreshDrawerMenu();
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        main_activity = (Main_Activity) activity;
    }
}
