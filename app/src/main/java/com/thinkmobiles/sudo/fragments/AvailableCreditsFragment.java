package com.thinkmobiles.sudo.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.thinkmobiles.sudo.R;
import com.thinkmobiles.sudo.global.App;

/**
 * Created by omar on 12.05.15.
 */
public class AvailableCreditsFragment extends Fragment {
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
        tvCredits.setText(App.getCurrentCredits() + " CREDITS");
    }

}
