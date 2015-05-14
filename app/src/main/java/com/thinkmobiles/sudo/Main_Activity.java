package com.thinkmobiles.sudo;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;

import android.util.Log;
import android.view.*;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;


import com.mikepenz.materialdrawer.Drawer;
import com.thinkmobiles.sudo.activities.LoginActivity;
import com.thinkmobiles.sudo.adapters.DrawerMenuAdapter;
import com.thinkmobiles.sudo.adapters.DrawerPhoneListAdapter;
import com.thinkmobiles.sudo.callbacks.ContactsFragmentCallback;
import com.thinkmobiles.sudo.core.rest.RetrofitAdapter;
import com.thinkmobiles.sudo.fragments.HomeFragment;
import com.thinkmobiles.sudo.fragments.numbers.BaseNumbersFragment;
import com.thinkmobiles.sudo.fragments.numbers.NumberMainFragment;
import com.thinkmobiles.sudo.fragments.RechargeCreditsFragment;
import com.thinkmobiles.sudo.fragments.SettingsFragment;
import com.thinkmobiles.sudo.global.App;
import com.thinkmobiles.sudo.global.FragmentReplacer;
import com.thinkmobiles.sudo.models.DefaultResponseModel;
import com.thinkmobiles.sudo.models.DrawerMenuItemModel;
import com.thinkmobiles.sudo.models.addressbook.UserModel;
import com.thinkmobiles.sudo.utils.ContactManager;

import static com.thinkmobiles.sudo.utils.CountryHelper.setCountryByIso;

import com.thinkmobiles.sudo.utils.MainToolbarManager;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.util.ArrayList;
import java.util.List;

import static com.thinkmobiles.sudo.global.DrawerConstants.CREDITS_FRAGMENT;
import static com.thinkmobiles.sudo.global.DrawerConstants.GET_NUMBER_FRAGMENT;
import static com.thinkmobiles.sudo.global.DrawerConstants.HOME_FRAGMENT;
import static com.thinkmobiles.sudo.global.DrawerConstants.SETTINGS_FRAGMENT;
import static com.thinkmobiles.sudo.global.DrawerConstants.SIGN_OUT_ACTION;


public class Main_Activity extends ActionBarActivity implements Drawer.OnDrawerListener, ContactsFragmentCallback, AdapterView.OnItemClickListener, View.OnClickListener {

    // Declaring Your View and Variables


    private String mTitle;

    private Drawer.Result mDrawer = null;
    private Callback<DefaultResponseModel> mSignOutCB;
    private MainToolbarManager sToolbarManager;
    private HomeFragment homeFragment;

    private SearchManager searchManager;
    private SearchView searchView;

    private ImageView ivAvatarDrawer, ivSpinner_Drawer;
    private TextView tvName;

    private RelativeLayout rlSpinnerChanger;
    private View mHeader;
    private DrawerPhoneListAdapter mDrawerPhoneListAdapter;
    private DrawerMenuAdapter mDrawerMenuAdapter;
    private ListView drawerListView;
    private UserModel selectedContact;
    private boolean showDrawer;
    private boolean showSearchView;
    private boolean showTrachView = false;
    private boolean showListDrawer = false;

    private List<DrawerMenuItemModel> mDrawerMenuList;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        enableSearchView(false);
        setContentView(R.layout.activity_main);

        initToolbar();
        initProgressBar();
        openHomeFragment();
        initDrawer();
        findHeaderUI();
        setHeaderContent();
        initSignOutCB();

    }

    private void setHeaderContent() {
        setBaseTitle();
        setDrawerIcon(ContactManager.getNumbers().get(0).getCountryIso());
        App.setCurrentISO(ContactManager.getNumbers().get(0).getCountryIso());
        sToolbarManager.setToolbarIcon(App.getCurrentISO());
    }

    @Override
    protected void onResume() {
        super.onResume();


    }


    private void openLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void initToolbar() {
        showDrawer = true;
        sToolbarManager = MainToolbarManager.getCustomInstance(this);
        sToolbarManager.changeToolbarTitleAndIcon(mTitle, 0);
    }


    private void initProgressBar() {
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
    }

    public void setProgressBarVisible(boolean visible) {
        if (visible) progressBar.setVisibility(View.VISIBLE);
        else {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        initSearchBar(menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (!showDrawer) onBackPressed();
            else {
                if (mDrawer.isDrawerOpen()) {
                    mDrawer.closeDrawer();
                } else {
                    mDrawer.openDrawer();

                }
            }
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        if (showDrawer) {
            if (mDrawer.isDrawerOpen()) {
                mDrawer.closeDrawer();
            } else {
                finish();
            }
        } else {
            BaseNumbersFragment.goBack();
        }

    }

    public void enableDrawer(boolean show) {

        this.showDrawer = show;

        if (show) {
            mDrawer.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);

        } else {

            mDrawer.getActionBarDrawerToggle().setDrawerIndicatorEnabled(false);
        }

    }


    @Override
    public void onDrawerOpened(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) Main_Activity.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(Main_Activity.this.getCurrentFocus().getWindowToken(), 0);
    }

    @Override
    public void onDrawerClosed(View view) {

    }


    private void implementClick(int pos) {
        switch (pos) {
            case SIGN_OUT_ACTION:
                makeSignOutRequest();
                break;
            case HOME_FRAGMENT:
                openHomeFragment();
                break;
            case CREDITS_FRAGMENT:
                openCreditsFragment();
                break;
            case GET_NUMBER_FRAGMENT:
                openNumbersFragment();
                break;
            case SETTINGS_FRAGMENT:
                openSettingsFragment();
                break;
        }
    }

    private void openCreditsFragment() {
        FragmentReplacer.replaceCurrentFragment(this, new RechargeCreditsFragment());
    }

    private void openSettingsFragment() {
        FragmentReplacer.replaceCurrentFragment(this, new SettingsFragment());
    }

    private void openNumbersFragment() {

        FragmentReplacer.replaceCurrentFragment(this, new NumberMainFragment());
    }

    private void openHomeFragment() {
        homeFragment = new HomeFragment();
        FragmentReplacer.replaceCurrentFragment(this, homeFragment);
    }


    private void makeSignOutRequest() {
        RetrofitAdapter.getInterface().sigOut(mSignOutCB);
    }

    private void setBaseTitle() {

        mTitle = App.getCurrentMobile();
        tvName.setText(mTitle);
    }

    private void initDrawerMenuList() {
        mDrawerMenuList = new ArrayList<>();
        mDrawerMenuList.add(new DrawerMenuItemModel(R.string.drawer_item_home, App.currentContacts, R.drawable.ic_contacts_chats));
        mDrawerMenuList.add(new DrawerMenuItemModel(R.string.drawer_item_get_number, " ", R.drawable.ic_get_number));

        mDrawerMenuList.add(new DrawerMenuItemModel(R.string.drawer_item_recharge_credits, App.currentCredits, R.drawable.ic_recharge_credits));
        mDrawerMenuList.add(new DrawerMenuItemModel(0, "", 0));

        mDrawerMenuList.add(new DrawerMenuItemModel(R.string.drawer_item_settings, "", R.drawable.ic_settings));
        mDrawerMenuList.add(new DrawerMenuItemModel(R.string.drawer_item_sign_out, "", R.drawable.ic_sign_out));

    }


    private void initDrawer() {
        initDrawerMenuList();
        mDrawerPhoneListAdapter = new DrawerPhoneListAdapter(this);
        mDrawerMenuAdapter = new DrawerMenuAdapter(this);
        drawerListView = new ListView(this);
        drawerListView.setDivider(getResources().getDrawable(android.R.color.transparent));
        mDrawer = new Drawer().withActivity(this).withToolbar(sToolbarManager.getToolbar()).withActionBarDrawerToggle(true).
                withHeader(R.layout.drawer_header).withListView(drawerListView).build();


        drawerListView.setAdapter(mDrawerMenuAdapter);

        drawerListView.setOnItemClickListener(this);
        mDrawerMenuAdapter.reloadList(mDrawerMenuList);
    }


    private void initPhoneListDrawer() {

        drawerListView.setAdapter(mDrawerPhoneListAdapter);
        mDrawerPhoneListAdapter.reloadList(App.getCurrentUser().getNumbers());
    }

    private void initDefaultDrawer() {
        drawerListView.setAdapter(mDrawerMenuAdapter);
        mDrawerMenuAdapter.reloadList(mDrawerMenuList);
    }


    private void findHeaderUI() {

        ivAvatarDrawer = (ImageView) findViewById(R.id.ivAvatar_Drawer);
        tvName = (TextView) findViewById(R.id.tvProfileName_Drawer);
        rlSpinnerChanger = (RelativeLayout) findViewById(R.id.rlDrawerSpinerChanger);
        ivSpinner_Drawer = (ImageView) findViewById(R.id.ivSpinner_Drawer);
        rlSpinnerChanger.setOnClickListener(this);
    }

    private void setAvatarContent(UserModel userModel) {
        tvName.setText(userModel.getCompanion());

    }

    private void initSearchBar(final Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

        searchManager = (SearchManager) Main_Activity.this.getSystemService(Context.SEARCH_SERVICE);

        searchView = (SearchView) searchItem.getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(Main_Activity.this.getComponentName()));
        searchView.setActivated(true);
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {

                if (getCurrentTab() == 0) homeFragment.getAdapter().getContactsFragment().reloadCurrentList();
                else homeFragment.getAdapter().getChatFragment().reloadCurrentList();

                return false;
            }
        });


    }

    private void initSignOutCB() {
        mSignOutCB = new Callback<DefaultResponseModel>() {
            @Override
            public void success(DefaultResponseModel defaultResponseModel, Response response) {
                Log.d("sigOut", defaultResponseModel.getSuccess());
                openLoginActivity();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("sigOut", error.getMessage());

            }
        };
    }

    @Override
    public void setCurrentContact(UserModel userModel) {
        selectedContact = userModel;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            // handles a search query
            String query = intent.getStringExtra(SearchManager.QUERY);


            if (getCurrentTab() == 0) {
                homeFragment.getAdapter().getContactsFragment().searchContactsList(query);
            } else {
                homeFragment.getAdapter().getChatFragment().searchChatList(query);
            }


        }
    }

    private int getCurrentTab() {
        return homeFragment.getCurrentTab();
    }

    @Override
    protected boolean onPrepareOptionsPanel(View view, Menu menu) {
        MenuItem searchItem = menu.findItem(R.id.action_search);
        if (showSearchView) searchItem.setVisible(true);
        else searchItem.setVisible(false);

        return super.onPrepareOptionsPanel(view, menu);
    }

    public void enableSearchView(boolean show) {
        showSearchView = show;
    }



    private void setDrawerIcon(String countryISO) {


        setCountryByIso(this, ivAvatarDrawer, countryISO, 100);

    }

    private void setToolbarIcon() {
        sToolbarManager.setToolbarIcon(App.getCurrentISO());
    }

    @Override
    public void onClick(View view) {

        if (showListDrawer) {
            showListDrawer = false;
            ivSpinner_Drawer.setImageResource(R.drawable.ic_spinner);
            initDefaultDrawer();
        } else {
            showListDrawer = true;
            ivSpinner_Drawer.setImageResource(R.drawable.ic_spinner_rotate);
            initPhoneListDrawer();
        }


    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {

        if (!showListDrawer) {
            view.setSelected(true);
            implementClick(pos);
            for (int j = 0; j < adapterView.getChildCount(); j++)
                adapterView.getChildAt(j).setBackgroundColor(Color.TRANSPARENT);

            // change the background color of the selected element
            view.setBackgroundColor(Color.LTGRAY);
            mDrawer.closeDrawer();
        } else {
            App.setCurrentMobile(ContactManager.getNumbers().get(pos - 1).getNumber());
            setDrawerIcon(ContactManager.getNumbers().get(pos - 1).getCountryIso());
            App.setCurrentISO(ContactManager.getNumbers().get(pos - 1).getCountryIso());
            sToolbarManager.changeToolbarTitle(App.getCurrentMobile());
            sToolbarManager.setToolbarIcon(App.getCurrentISO());
            setBaseTitle();
            onClick(new View(this));

        }

    }

    public void enableTrashView(boolean show) {
        showTrachView = show;
    }
}