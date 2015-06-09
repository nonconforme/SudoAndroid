package com.thinkmobiles.sudo.fragments;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.thinkmobiles.sudo.R;
import com.thinkmobiles.sudo.activities.LoginActivity;
import com.thinkmobiles.sudo.activities.Main_Activity;
import com.thinkmobiles.sudo.adapters.ViewPagerAdapter;
import com.thinkmobiles.sudo.custom_views.SlidingTabLayout;
import com.thinkmobiles.sudo.global.App;
import com.thinkmobiles.sudo.global.Constants;
import com.thinkmobiles.sudo.utils.MainToolbarManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements SlidingTabLayout.TabColorizer  {

    private Main_Activity mActivity;
    private ViewPager pager;
    private ViewPagerAdapter adapter;
    private SlidingTabLayout tabs;
    private CharSequence Titles[] = {Constants.TITLE_CONTACTS, Constants.TITLE_CHATS};
    private final int mTabsCount = 2;
    private final int currentTab = 0;
    private MainToolbarManager mainToolbarManager;



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (App.getCurrentUser() == null) {
            getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
        }
        mActivity = (Main_Activity) activity;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        findUI(view);
        initAdapter();
        initTabs();
        pager.setCurrentItem(0, true);
        mainToolbarManager = MainToolbarManager.getCustomInstance(mActivity);
        return view;
    }

    private void findUI(View _view) {
        pager = (ViewPager) _view.findViewById(R.id.vpHome_HF);
        tabs = (SlidingTabLayout) _view.findViewById(R.id.tabs);

    }

    private void initAdapter() {
        adapter = new ViewPagerAdapter(mActivity.getSupportFragmentManager(), Titles, mTabsCount);
        pager.setAdapter(adapter);

    }


    private void initTabs() {
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width
        tabs.setCustomTabColorizer(this);
        tabs.setViewPager(pager);

    }


    @Override
    public int getIndicatorColor(int position) {
        return getResources().getColor(R.color.tabsScrollColor);
    }

    public int getCurrentTab() {
        return currentTab;
    }

    @Override
    public void onResume() {

        mainToolbarManager.enableSearchView(true);
         
        super.onResume();

    }


    @Override
    public void onPause() {

        MainToolbarManager.getCustomInstance(mActivity).enableSearchView(false);
        super.onPause();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
