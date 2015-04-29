package com.thinkmobiles.sudo.fragments;

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
import com.thinkmobiles.sudo.models.Numbers;
import com.thinkmobiles.sudo.models.counties.CountryModel;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Pavilion on 14.04.2015.
 */
public class NumbersFragment extends Fragment implements AdapterView.OnItemClickListener{

    private View mView;
    private Activity mActivity;
    private NumbersAdapter mAdapter;
    private List<Numbers> mList;
    private ListView mListView;
    private Callback<List<CountryModel>> mContries;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_number, container, false);
        initComponent();
        initCountiesCB();
        getCountries();
        return mView;
    }

    private void initCountiesCB() {
        mContries = new Callback<List<CountryModel>>() {
            @Override
            public void success(List<CountryModel> countryModels, Response response) {

            }

            @Override
            public void failure(RetrofitError error) {

            }
        };
    }

    private void initComponent(){
        mListView = (ListView) mView.findViewById(R.id.lvNumbers_FN);

        mList = new ArrayList<Numbers>();
        mList.add(new Numbers("Thailand", "12CRED"));
        mList.add(new Numbers("Turkey", "12CRED"));
        mList.add(new Numbers("Ukraine", "12CRED"));

        mAdapter = new NumbersAdapter(mActivity, mList);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(mActivity, "Click: pos: " + position + " "
                + mAdapter.getItem(position).getCountry(), Toast.LENGTH_SHORT).show();
    }

    private void getCountries(){
        RetrofitAdapter.getInterface().getCountries(mContries);
    }
}
