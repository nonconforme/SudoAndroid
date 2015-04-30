package com.thinkmobiles.sudo.fragments.numbers;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.thinkmobiles.sudo.R;
import com.thinkmobiles.sudo.adapters.NumbersAdapter;
import com.thinkmobiles.sudo.core.rest.RetrofitAdapter;
import com.thinkmobiles.sudo.models.counties.Numbers;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Pavilion on 14.04.2015.
 */
public class CountiesFragment extends BaseNumbersFragment implements AdapterView.OnItemClickListener{

    private View mView;
    private Activity mActivity;
    private NumbersAdapter mAdapter;

    private ListView mListView;
    private Callback<List<Numbers>> mContries;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_number, container, false);
        initComponent();
        initCountiesCB();
        getCountries();
        setListeners();
        return mView;
    }

    private void setListeners() {
        mListView.setOnItemClickListener(this);
    }

    private void initCountiesCB() {
        mContries = new Callback<List<Numbers>>() {
            @Override
            public void success(List<Numbers> _countryModels, Response _response) {
                mAdapter.reloadList(_countryModels);
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(mActivity, "Error! Pleasy try again.",Toast.LENGTH_LONG).show();

            }
        };
    }

    private void initComponent(){
        mListView = (ListView) mView.findViewById(R.id.lvNumbers_FN);
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
        Toast.makeText(mActivity, "Click: pos: " + position + " "
                + mAdapter.getItem(position).getName() , Toast.LENGTH_SHORT).show();
        openNumbersFragment();
    }

    private void getCountries(){
        RetrofitAdapter.getInterface().getCountries(mContries);
    }
}
