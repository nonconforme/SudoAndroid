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
import com.thinkmobiles.sudo.activities.Main_Activity;
import com.thinkmobiles.sudo.adapters.BuyNumbersAdapter;
import com.thinkmobiles.sudo.core.rest.RetrofitAdapter;
import com.thinkmobiles.sudo.global.App;
import com.thinkmobiles.sudo.global.Constants;
import com.thinkmobiles.sudo.models.ProfileResponse;
import com.thinkmobiles.sudo.models.addressbook.UserModel;
import com.thinkmobiles.sudo.models.counties.NumberPackages;
import com.thinkmobiles.sudo.models.numbers.BuyNumberResponce;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BuyNumberFragment extends BaseNumbersFragment implements AdapterView.OnItemClickListener {

    private View mView;
    private BuyNumbersAdapter mBuyNumbersAdapter;
    private ListView mListView;
    private Main_Activity mActivity;

    private Callback<ProfileResponse> mUserCB;
    private Callback<BuyNumberResponce> mBuyNumberCB;

    private List<NumberPackages> mList;

    private String mNumber;
    private String mCountryIso;


    public BuyNumberFragment() {
        // Required empty public constructor
    }

    public static BuyNumberFragment newInstance(final String _number, final String _countryIso) {
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
        mActivity = (Main_Activity) _activity;
        if (!getArguments().isEmpty()) {
            mNumber = getArguments().getString(Constants.PARAM_NUMBER);
            mCountryIso = getArguments().getString(Constants.COUNTRY_CODE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_buy_number, container, false);
        mList = getCountryModel().getBuyNumberPackages();
        initComponent();
        initListeners();
        initBuyNumberCB();
        initGetUserCB();
        setContent();
        changeToolbarTitleAndImage(R.string.buy_a_number);

        return mView;
    }

    private void initBuyNumberCB() {
        mBuyNumberCB = new Callback<BuyNumberResponce>() {
            @Override
            public void success(BuyNumberResponce buyNumberResponce, Response response) {
                getToolbarManager().setProgressBarVisible(false);


                App.setCurrentCredits(buyNumberResponce.getCredits());
                refreshFragmentAvailableCredistFragment();
                Toast.makeText(mActivity, mActivity.getString(R.string.success), Toast.LENGTH_LONG).show();

                getUserRequest();
            }

            @Override
            public void failure(RetrofitError error) {
                getToolbarManager().setProgressBarVisible(false);


                    Toast.makeText(mActivity, mActivity.getString(R.string.check_credits_balance), Toast.LENGTH_LONG).show();

            }

        };
    }

    private void buyNumber(final int _pos) {
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
        mBuyNumbersAdapter.reloadList(mList, mCountryIso);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        getToolbarManager().setProgressBarVisible(true);
        buyNumber(position);
    }

    private void initGetUserCB() {
        mUserCB = new Callback<ProfileResponse>() {
            @Override
            public void success(ProfileResponse profileResponse, Response response) {

                boolean needToRefreshDrawerAndToolbar = false;
                if (App.getCurrentUser().getNumbers().isEmpty()) {
                    needToRefreshDrawerAndToolbar = true;
                }
                setProfile(profileResponse);
                if (needToRefreshDrawerAndToolbar) {
                    refreshDrawerAndToolbar();
                }

            }

            @Override
            public void failure(RetrofitError error) {

            }
        };
    }

    private void refreshDrawerAndToolbar() {
        mActivity.setHeaderContent();
    }


    private void setProfile(final ProfileResponse _profile) {
        UserModel profileModel = new UserModel();
        profileModel.setEmail(_profile.getUser().getEmail());
        profileModel.setNumbers(_profile.getUser().getNumbers());
        profileModel.setCredits(_profile.getUser().getCredits());
        if (_profile != null) {
            App.setCurrentUser(profileModel);
        }
        if (!_profile.getUser().getNumbers().isEmpty()) {
            App.setCurrentMobile(_profile.getUser().getNumbers().get(0).getNumber());
        } else {

        }
        App.setCurrentCredits(_profile.getUser().getCredits());

    }

    public void getUserRequest() {
        RetrofitAdapter.getInterface().getProfile(App.getuId(), mUserCB);
    }
}
