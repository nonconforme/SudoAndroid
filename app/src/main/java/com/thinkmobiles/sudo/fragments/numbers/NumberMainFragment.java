package com.thinkmobiles.sudo.fragments.numbers;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thinkmobiles.sudo.Main_Activity;
import com.thinkmobiles.sudo.R;
import com.thinkmobiles.sudo.ToolbarManager;
import com.thinkmobiles.sudo.fragments.numbers.BaseNumbersFragment;

/**
 * Created by njakawaii on 29.04.2015.
 */
public class NumberMainFragment  extends BaseNumbersFragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main_numbers, container, false);
        setMainFragment(this);
        openCountryFragment();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}
