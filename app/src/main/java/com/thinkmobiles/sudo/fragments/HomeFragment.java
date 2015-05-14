package com.thinkmobiles.sudo.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thinkmobiles.sudo.R;
import com.thinkmobiles.sudo.utils.MainToolbarManager;
import com.thinkmobiles.sudo.adapters.ViewPagerAdapter;
import com.thinkmobiles.sudo.custom_views.SlidingTabLayout;
import com.thinkmobiles.sudo.global.Constants;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements SlidingTabLayout.TabColorizer  {

    private ViewPager pager;
    private ViewPagerAdapter adapter;
    private SlidingTabLayout tabs;
    private CharSequence Titles[] = {Constants.TITLE_CONTACTS, Constants.TITLE_CHATS};
    private final int mTabsCount = 2;
    private final int currentTab = 0;
    private ActionBarActivity mActivity;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (ActionBarActivity) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        findUI(view);
        initAdapter();
        initPagerListener();
        initTabs();
        pager.setCurrentItem(0, true);

        return view;
    }

    private void findUI(View _view) {
        pager = (ViewPager) _view.findViewById(R.id.vpHome_HF);
        tabs = (SlidingTabLayout) _view.findViewById(R.id.tabs);

    }

    private void initAdapter() {
        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter = new ViewPagerAdapter(mActivity.getSupportFragmentManager(), Titles, mTabsCount);

        // Assigning ViewPager View and setting the adapter
        pager.setAdapter(adapter);

    }


    private void initTabs() {
        // Assiging the Sliding Tab Layout View
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(this);

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);

    }

    public void initPagerListener() {
    }

    @Override
    public int getIndicatorColor(int position) {
        return getResources().getColor(R.color.tabsScrollColor);
    }


    public int getCurrentTab() {
        return currentTab;
    }

    public ViewPagerAdapter getAdapter() {
        return adapter;
    }

    @Override
    public void onResume() {

        MainToolbarManager.getCustomInstance(mActivity).enableSearchView(true);
        super.onResume();


    }


    @Override
    public void onPause() {

        MainToolbarManager.getCustomInstance(mActivity).enableSearchView(false);

        super.onPause();


    }


}
