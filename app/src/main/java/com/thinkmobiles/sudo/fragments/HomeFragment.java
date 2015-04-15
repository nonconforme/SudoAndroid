package com.thinkmobiles.sudo.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thinkmobiles.sudo.R;
import com.thinkmobiles.sudo.adapters.ViewPagerAdapter;
import com.thinkmobiles.sudo.custom_views.SlidingTabLayout;
import com.thinkmobiles.sudo.global.Constants;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements SlidingTabLayout.TabColorizer {

    private ViewPager pager;
    private ViewPagerAdapter adapter;
    private SlidingTabLayout tabs;
    private CharSequence Titles[]={Constants.TITLE_CONTACTS, Constants.TITLE_CHATS};
    private int mTabsCount = 2;
    private FragmentActivity mActivity;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (FragmentActivity) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        findUI(view);
        initAdapter();
        initTabs();
        return view;
    }

    private void findUI(final View _view) {
        pager           = (ViewPager) _view.findViewById(R.id.vpHome_HF);
        tabs            = (SlidingTabLayout) _view.findViewById(R.id.tabs);

    }

    private void initAdapter(){
        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter =  new ViewPagerAdapter(mActivity.getSupportFragmentManager(),Titles, mTabsCount);

        // Assigning ViewPager View and setting the adapter
        pager.setAdapter(adapter);

    }


    private void initTabs(){
        // Assiging the Sliding Tab Layout View
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(this);

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);


    }

    @Override
    public int getIndicatorColor(int position) {
        return getResources().getColor(R.color.tabsScrollColor);
    }
}
