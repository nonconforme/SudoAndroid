package com.thinkmobiles.sudo.fragments.numbers;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.thinkmobiles.sudo.Main_Activity;
import com.thinkmobiles.sudo.R;
import com.thinkmobiles.sudo.MainToolbarManager;
import com.thinkmobiles.sudo.fragments.AvailableCreditsFragment;
import com.thinkmobiles.sudo.models.counties.CountryModel;

/**
 * Created by njakawaii on 29.04.2015.
 */
public abstract class BaseNumbersFragment extends Fragment {
    protected Main_Activity mActivity;
    protected static FragmentManager mFragmentManager;
    protected static CountryModel mCountryModel;
    private MainToolbarManager mToolbarManager;
    private AvailableCreditsFragment mCreditsAvailableFragment;

    @Override
    public void onAttach(Activity _activity) {
        super.onAttach(_activity);
        mActivity = (Main_Activity) _activity;
        mToolbarManager = MainToolbarManager.getCustomInstance(mActivity);
    }


    public void setMainFragment(final NumberMainFragment _mainFragment) {
        mFragmentManager = _mainFragment.getChildFragmentManager();
    }

    protected void openCountryFragment() {
        Fragment newFragment = new CountriesFragment();
        mFragmentManager.beginTransaction().add(R.id.flContainer_FMN, newFragment).commit();
    }

    protected void openNumbersFragment(final String _countryIso) {
        mToolbarManager.enableDrawer(false);
        changeFragment(NumberListFragment.newInstance(_countryIso));
    }

    protected MainToolbarManager getToolbarManager() {
        return mToolbarManager;
    }

    protected void openBuyNumberFragment(final String _number, final String _countryIso) {
        changeFragment(BuyNumberFragment.newInstance(_number, _countryIso));
    }

    private void changeFragment(final Fragment _fragment) {
        mFragmentManager.beginTransaction().replace(R.id.flContainer_FMN, _fragment).addToBackStack(null).commit();
    }

    public static void goBack() {
        if (mFragmentManager.getBackStackEntryCount() > 0) {
            mFragmentManager.popBackStack();
        }
    }

    protected CountryModel getCountryModel() {
        return mCountryModel;
    }

    protected void setmCountryModel(CountryModel mCountryModel) {
        this.mCountryModel = mCountryModel;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);

    }

    protected void changeToolbarTitleAndImage(int title) {
        MainToolbarManager.getCustomInstance(mActivity).changeToolbarTitleAndIcon(title, 0);
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
}
