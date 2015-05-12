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
import com.thinkmobiles.sudo.adapters.BuyNumbersAdapter;
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
public class BuyNumberFragment extends BaseNumbersFragment implements AdapterView.OnItemClickListener {

    private String mNumber;
    private String mCountryIso;
    private Callback<DefaultResponseModel> mBuyNumberCB;
    private List<NumberPackages> mList;


    private View mView;
    private BuyNumbersAdapter mBuyNumbersAdapter;
    private ListView mListView;
    private Activity mActivity;



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
        mView = inflater.inflate(R.layout.fragment_buy_number, container, false);
        mList = getCountryModel().getBuyNumberPackages();
        initComponent();
        initListeners();
        initBuyNumberCB();
        setContent();

        changeToolbarTitleAndImage(R.string.buy_a_number);

        return mView;
    }

    private void initBuyNumberCB() {
        mBuyNumberCB = new Callback<DefaultResponseModel>() {
            @Override
            public void success(DefaultResponseModel defaultResponseModel, Response response) {
                getToolbarManager().setProgressBarVisible(false);
                refreshFragmentAvailableCredistFragment();
                Toast.makeText(mActivity, "Success!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void failure(RetrofitError error) {
                getToolbarManager().setProgressBarVisible(false);

                Toast.makeText(mActivity, "Error!", Toast.LENGTH_LONG).show();

            }
        };
    }

    private void buyNumber(final int _pos){
        RetrofitAdapter.getInterface().buyNumber(mNumber, mCountryIso.toUpperCase(), getCountryModel().getBuyNumberPackages().get(_pos).getPackageName(), mBuyNumberCB);
    }

    private void initComponent() {
        mListView = (ListView) mView.findViewById(R.id.lvNumbers_FN);
        mBuyNumbersAdapter = new BuyNumbersAdapter(mActivity);
        mListView.setAdapter(mBuyNumbersAdapter);

    }

    private void initListeners() {
        mListView.setOnItemClickListener(this);
    }

    private void setContent() {
        mBuyNumbersAdapter.reloadList(mList);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        getToolbarManager().setProgressBarVisible(true);

        buyNumber(position);
    }
}
