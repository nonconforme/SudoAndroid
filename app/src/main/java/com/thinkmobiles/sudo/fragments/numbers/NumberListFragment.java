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
import com.thinkmobiles.sudo.adapters.NumbersAdapter;
import com.thinkmobiles.sudo.core.rest.RetrofitAdapter;
import com.thinkmobiles.sudo.global.App;
import com.thinkmobiles.sudo.global.Constants;
import com.thinkmobiles.sudo.models.numbers.NumberListResponse;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class NumberListFragment extends BaseNumbersFragment implements AdapterView.OnItemClickListener {


    private NumbersAdapter mAdapter;
    private ListView mListView;
    private View mView;
    private String mCountryIso;


    private Callback<NumberListResponse> mNumbers;


    public NumberListFragment() {
        // Required empty public constructor
    }

    public static NumberListFragment newInstance(String countryIso){
        NumberListFragment numberListFragment = new NumberListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.PATH_PARAM_COUNTRY_ISO, countryIso);
        numberListFragment.setArguments(bundle);
        return numberListFragment;
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
        changeToolbarTitleAndImage(R.string.available_numbers);
        return mView;
    }

    private void initGetNumbersCB() {
        mNumbers = new Callback<NumberListResponse>() {
            @Override
            public void success(NumberListResponse _numberPackages, Response _response) {
                getToolbarManager().setProgressBarVisible(false);
                mAdapter.reloadList(_numberPackages.getObjects(), App.getCurrentUser().getNumbers());

            }

            @Override
            public void failure(RetrofitError error) {
                getToolbarManager().setProgressBarVisible(false);
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

        if (!getArguments().isEmpty()){
            mCountryIso = (String) getArguments().get(Constants.PATH_PARAM_COUNTRY_ISO);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(mActivity,view.getTag().toString(),Toast.LENGTH_SHORT).show();
       openBuyNumberFragment(mAdapter.getItem(position).getNumber(), mCountryIso);
    }

    private void getNumbers() {

        getToolbarManager().setProgressBarVisible(true);
        RetrofitAdapter.getInterface().searchNumbers(mCountryIso.toUpperCase(), mNumbers);
    }


}



