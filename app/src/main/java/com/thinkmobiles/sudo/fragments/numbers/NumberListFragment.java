package com.thinkmobiles.sudo.fragments.numbers;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thinkmobiles.sudo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NumberListFragment extends Fragment {


    public NumberListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_number_list, container, false);
        initGetNumbersCB();

        return view;
    }

    private void initGetNumbersCB() {

    }


}
