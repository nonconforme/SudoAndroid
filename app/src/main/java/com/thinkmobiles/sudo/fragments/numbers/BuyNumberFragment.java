package com.thinkmobiles.sudo.fragments.numbers;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.thinkmobiles.sudo.R;
import com.thinkmobiles.sudo.core.rest.RetrofitAdapter;
import com.thinkmobiles.sudo.global.Constants;
import com.thinkmobiles.sudo.models.DefaultResponseModel;
import com.thinkmobiles.sudo.models.counties.NumberPackages;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class BuyNumberFragment extends BaseNumbersFragment {

    private String mNumber;
    private String mCountryIso;
    private Activity mActivity;
    private Callback<DefaultResponseModel> mBuyNumberCB;
    private List<NumberPackages> mList;

    public BuyNumberFragment() {
        // Required empty public constructor
    }

    public static BuyNumberFragment newInstance( final String _number,final String _countryIso){
        BuyNumberFragment numberFragment = new BuyNumberFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.PATH_PARAM_COUNTRY_ISO, _countryIso);
        bundle.putString(Constants.PARAM_NUMBER, _number);
        numberFragment.setArguments(bundle);
        return numberFragment;
    }

    @Override
    public void onAttach(Activity _activity) {
        super.onAttach(_activity);
        mActivity = _activity;
        if (!getArguments().isEmpty()){
            mNumber         = getArguments().getString(Constants.PARAM_NUMBER);
            mCountryIso     = getArguments().getString(Constants.COUNTRY_CODE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_buy_number, container, false);
        mList = getCountryModel().getBuyNumberPackages();
        initBuyNumberCB();
        buyNumber(0);
        return view;
    }

    private void initBuyNumberCB() {
        mBuyNumberCB = new Callback<DefaultResponseModel>() {
            @Override
            public void success(DefaultResponseModel defaultResponseModel, Response response) {

            }

            @Override
            public void failure(RetrofitError error) {

            }
        };
    }

    private void buyNumber(final int _pos){
        RetrofitAdapter.getInterface().buyNumber(mNumber, mCountryIso.toUpperCase(), getCountryModel().getBuyNumberPackages().get(_pos).getPackageName(), mBuyNumberCB);
    }

}
