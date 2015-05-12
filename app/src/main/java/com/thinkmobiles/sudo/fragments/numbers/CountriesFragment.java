package com.thinkmobiles.sudo.fragments.numbers;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.thinkmobiles.sudo.Main_Activity;
import com.thinkmobiles.sudo.R;
import com.thinkmobiles.sudo.MainToolbarManager;
import com.thinkmobiles.sudo.adapters.CountriesAdapter;
import com.thinkmobiles.sudo.core.rest.RetrofitAdapter;
import com.thinkmobiles.sudo.fragments.CreditsAvailableFragment;
import com.thinkmobiles.sudo.models.counties.CountryModel;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Pavilion on 14.04.2015.
 */
public class CountriesFragment extends BaseNumbersFragment implements AdapterView.OnItemClickListener {

    private View mView;
    private Main_Activity mActivity;
    private CountriesAdapter mAdapter;

    private ListView mListView;
    private Callback<List<CountryModel>> mContries;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_number, container, false);
        initComponent();
        initCountiesCB();
        getCountries();
        setListeners();

        changeToolbarTitleAndImage(R.string.countries);
        return mView;
    }



    private void setListeners() {
        mListView.setOnItemClickListener(this);
    }

    private void initCountiesCB() {
        mContries = new Callback<List<CountryModel>>() {
            @Override
            public void success(List<CountryModel> _countryModels, Response _response) {
                getToolbarManager().setProgressBarVisible(false);
                mAdapter.reloadList(_countryModels);
            }

            @Override
            public void failure(RetrofitError error) {
                getToolbarManager().setProgressBarVisible(false);
                Toast.makeText(mActivity, "Error! Pleasy try again.", Toast.LENGTH_LONG).show();

            }
        };
    }

    private void initComponent() {
        mListView = (ListView) mView.findViewById(R.id.lvNumbers_FN);
        mAdapter = new CountriesAdapter(mActivity);
        mListView.setAdapter(mAdapter);


    }

    @Override
    public void onResume() {
        super.onResume();
        MainToolbarManager.getCustomInstance(mActivity).enableDrawer(true);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (Main_Activity) activity;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        openNumbersFragment(mAdapter.getItem(position).getCountryIso());
        setmCountryModel(mAdapter.getItem(position));
    }

    private void getCountries() {
        getToolbarManager().setProgressBarVisible(true);
        RetrofitAdapter.getInterface().getCountries(mContries);
    }
}
