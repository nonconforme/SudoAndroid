package com.thinkmobiles.sudo.fragments.numbers;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.thinkmobiles.sudo.R;
import com.thinkmobiles.sudo.adapters.CountriesAdapter;
import com.thinkmobiles.sudo.adapters.NumbersAdapter;
import com.thinkmobiles.sudo.core.rest.RetrofitAdapter;
import com.thinkmobiles.sudo.models.counties.CountryModel;
import com.thinkmobiles.sudo.models.counties.NumberPackages;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NumberListFragment extends BaseNumbersFragment implements AdapterView.OnItemClickListener {

    private Activity mActivity;
    private NumbersAdapter mAdapter;
    private ListView mListView;
    private View mView;

    private Callback<List<NumberPackages>> mNumbers;


    public NumberListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_number_list, container, false);
        initComponent();
        initGetNumbersCB();
        getNumbers();
        setListeners();

        return mView;
    }

    private void initGetNumbersCB() {
        mNumbers = new Callback<List<NumberPackages>>() {
            @Override
            public void success(List<NumberPackages> _numberPackages, Response _response) {
                mAdapter.reloadList(_numberPackages);
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(mActivity, "Error! Pleasy try again.", Toast.LENGTH_LONG).show();

            }
        };
    }

    private void setListeners() {
        mListView.setOnItemClickListener(this);
    }


    private void initComponent() {
        mListView = (ListView) mView.findViewById(R.id.lvNumbersList_FN);
        mAdapter = new NumbersAdapter(mActivity);
        mListView.setAdapter(mAdapter);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(mActivity, "Click: pos: " + position + " " + mAdapter.getItem(position).getPackageName(), Toast.LENGTH_SHORT).show();
        openNumbersFragment();
    }

    private void getNumbers() {
        /*RetrofitAdapter.getInterface().getCountries(mNumbers);*/
    }
}



