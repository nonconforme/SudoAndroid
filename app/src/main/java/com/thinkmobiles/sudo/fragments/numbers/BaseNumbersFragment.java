package com.thinkmobiles.sudo.fragments.numbers;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.thinkmobiles.sudo.R;
import com.thinkmobiles.sudo.ToolbarManager;
import com.thinkmobiles.sudo.models.counties.CountryModel;

/**
 * Created by njakawaii on 29.04.2015.
 */
public abstract class BaseNumbersFragment extends Fragment {
    protected Activity mActivity;
    protected static FragmentManager mFragmentManager;
    private CountryModel mCountryModel;


    @Override
    public void onAttach(Activity _activity) {
        super.onAttach(_activity);
        mActivity = _activity;
    }


    public void setMainFragment(final NumberMainFragment _mainFragment) {
        mFragmentManager = _mainFragment.getChildFragmentManager();
    }

    protected void openCountryFragment() {
        Fragment newFragment = new CountriesFragment();
        mFragmentManager.beginTransaction().add(R.id.flContainer_FMN, newFragment).commit();
    }

    protected void openNumbersFragment() {
        ToolbarManager.getInstance(mActivity).enableDrawer(false);
        changeFragment(new NumberListFragment());
    }

    protected void openBuyNumberFragment() {
        changeFragment(new BuyNumberFragment());
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


}
