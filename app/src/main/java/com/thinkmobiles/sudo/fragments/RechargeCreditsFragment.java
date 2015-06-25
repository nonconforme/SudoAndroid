package com.thinkmobiles.sudo.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.thinkmobiles.sudo.R;
import com.thinkmobiles.sudo.adapters.RechargeCreditsAdapter;
import com.thinkmobiles.sudo.billing.BillingHelper;
import com.thinkmobiles.sudo.billing.BillingHelperCallback;
import com.thinkmobiles.sudo.core.rest.RetrofitAdapter;
import com.thinkmobiles.sudo.global.App;
import com.thinkmobiles.sudo.global.Constants;
import com.thinkmobiles.sudo.models.Credits;
import com.thinkmobiles.sudo.models.numbers.BuyNumberResponce;
import com.thinkmobiles.sudo.utils.MainToolbarManager;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pavilion on 16.04.2015.
 */
public class RechargeCreditsFragment extends Fragment implements AdapterView.OnItemClickListener, BillingHelperCallback {

    private AvailableCreditsFragment mCreditsAvailableFragment;
    private ActionBarActivity mActivity;


    private View mView;
    private ListView mListView;

    private RechargeCreditsAdapter mAdapter;
    private Callback<BuyNumberResponce> mBuyCreditsCB;

    private List<Credits> mListCredit;

    private BillingHelper billingHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_recharge_credits, container, false);
        initComponent();
        setListener();
        initBuyCreditsCB();
        MainToolbarManager.getCustomInstance(mActivity).changeToolbarTitleAndIcon(R.string.credits, 0);
        return mView;
    }

    private void initBuyCreditsCB() {
        mBuyCreditsCB = new Callback<BuyNumberResponce>() {
            @Override
            public void success(BuyNumberResponce buyNumberResponce, Response response) {
                App.setCurrentCredits(buyNumberResponce.getCredits());
                refreshFragmentAvailableCredistFragment();
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(mActivity,mActivity.getString(R.string.failure_recharging_credits), Toast.LENGTH_SHORT).show();

            }
        };
    }

    private void makeBuyCreditsRequest(){
        RetrofitAdapter.getInterface().buyCredits("1000", Constants.PROVIDER_GOOGLE, mBuyCreditsCB);
    }

    private void initComponent(){
        mListView = (ListView) mView.findViewById(R.id.lvCredits_FRC);

        mListCredit = new ArrayList<>();
        mListCredit.add(new Credits("10credits = 10$", "BUY"));
        mListCredit.add(new Credits("20credits = 20$", "BUY"));
        mListCredit.add(new Credits("30credits = 30$", "BUY"));
        mListCredit.add(new Credits("40credits = 40$", "BUY"));

        mAdapter = new RechargeCreditsAdapter(mActivity, mListCredit);
        mListView.setAdapter(mAdapter);
    }

    private void setListener(){
        mListView.setOnItemClickListener(this);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (ActionBarActivity)activity;
        billingHelper = BillingHelper.getInstance(mActivity, this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(position == 0)
            billingHelper.doPurchase10();
        else if (position == 1)
            billingHelper.doPurchase20();
        else if (position == 2)
            billingHelper.doPurchase30();
        else if (position == 3)
            billingHelper.doPurchase40();

    }

    @Override
    public void onResume() {
        super.onResume();
        initAvailableCredistFragment();
    }

    @Override
    public void onPause() {
        removeAvailableCredistFragment();
        super.onPause();

    }

    private void initAvailableCredistFragment() {
        mCreditsAvailableFragment = new AvailableCreditsFragment();
        mActivity.getSupportFragmentManager().beginTransaction().add(R.id.container_credits, mCreditsAvailableFragment).commit();
    }
    public void refreshFragmentAvailableCredistFragment() {
        mCreditsAvailableFragment.setCredits();
    }


    private void removeAvailableCredistFragment() {
        mActivity.getSupportFragmentManager().beginTransaction().remove(mCreditsAvailableFragment

        ).commit();
    }

    @Override
    public void updateCredits(String string) {
        makeBuyCreditsRequest();
    }

    @Override
    public void onStop() {
        super.onStop();
        billingHelper.onDestroyCheck();
    }
}
