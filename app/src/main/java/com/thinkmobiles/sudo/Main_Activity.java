package com.thinkmobiles.sudo;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;

import android.util.Log;
import android.view.*;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;


import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.squareup.picasso.Picasso;
import com.thinkmobiles.sudo.activities.LoginActivity;
import com.thinkmobiles.sudo.callbacks.ContactsFragmentCallback;
import com.thinkmobiles.sudo.core.rest.RetrofitAdapter;
import com.thinkmobiles.sudo.fragments.HomeFragment;
import com.thinkmobiles.sudo.fragments.numbers.BaseNumbersFragment;
import com.thinkmobiles.sudo.fragments.numbers.NumberMainFragment;
import com.thinkmobiles.sudo.fragments.RechargeCreditsFragment;
import com.thinkmobiles.sudo.fragments.SettingsFragment;
import com.thinkmobiles.sudo.global.App;
import com.thinkmobiles.sudo.global.CircleTransform;
import com.thinkmobiles.sudo.global.FragmentReplacer;
import com.thinkmobiles.sudo.models.DefaultResponseModel;
import com.thinkmobiles.sudo.models.addressbook.UserModel;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.thinkmobiles.sudo.global.DrawerConstants.CREDITS_FRAGMENT;
import static com.thinkmobiles.sudo.global.DrawerConstants.GET_NUMBER_FRAGMENT;
import static com.thinkmobiles.sudo.global.DrawerConstants.HOME_FRAGMENT;
import static com.thinkmobiles.sudo.global.DrawerConstants.SETTINGS_FRAGMENT;
import static com.thinkmobiles.sudo.global.DrawerConstants.SIGN_OUT_ACTION;


public class Main_Activity extends ActionBarActivity implements Drawer.OnDrawerItemSelectedListener, Drawer.OnDrawerListener, Drawer.OnDrawerItemClickListener, ContactsFragmentCallback {

    // Declaring Your View and Variables


    private String mTitle;
    private Drawer.Result mDrawer = null;
    private Callback<DefaultResponseModel> mSignOutCB;
    private MainToolbarManager sToolbarManager;
    private HomeFragment homeFragment;

    private SearchManager searchManager;
    private SearchView searchView;

    private ImageView ivAvatarDrawer;
    private TextView tvProfileNameDrawer, tvProfilePhoneDrawer;


    private UserModel selectedContact;
    private boolean showDrawer;
    private boolean showSearchView;

    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        enableSearchView(false);
        setContentView(R.layout.activity_main);
        setBaseTitle();
        initToolbar();
        initProgressBar();
        openHomeFragment();
        initDrawer();
        initDrawerHeader();
        setDrawerHeaderContent();
        initSignOutCB();

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
                mDrawer.openDrawer();
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
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l, IDrawerItem iDrawerItem) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onDrawerOpened(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) Main_Activity.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(Main_Activity.this.getCurrentFocus().getWindowToken(), 0);
    }

    @Override
    public void onDrawerClosed(View view) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l, IDrawerItem iDrawerItem) {
        implementClick(pos);

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
        if (App.getCurrentMobile() == null) {
            mTitle = App.getGetUserName();
        } else {
            mTitle = App.getCurrentMobile();
        }
    }

    private void initDrawer() {
        mDrawer = new Drawer().withActivity(this).withToolbar(sToolbarManager.getToolbar()).withActionBarDrawerToggle(true).withHeader(R.layout.drawer_header).withOnDrawerListener(this).withOnDrawerItemClickListener(this).addDrawerItems(new PrimaryDrawerItem().withName(R.string.drawer_item_home).withIcon(getResources().getDrawable(R.drawable.ic_contacts_chats)).withBadge("99").withIdentifier(1), new PrimaryDrawerItem().withName(R.string.drawer_item_get_number).withIcon(getResources().getDrawable(R.drawable.ic_get_number)), new PrimaryDrawerItem().withName(R.string.drawer_item_recharge_credits).withIcon(getResources().getDrawable(R.drawable.ic_recharge_credits)).withBadge("6").withIdentifier(2), new DividerDrawerItem(), new SecondaryDrawerItem().withName(R.string.drawer_item_settings).withIcon(getResources().getDrawable(R.drawable.ic_settings)), new SecondaryDrawerItem().withName(R.string.drawer_item_sign_out).withIcon(getResources().getDrawable(R.drawable.ic_sign_out))).build();

    }

    private void setAvatarContent(UserModel userModel) {
        tvProfileNameDrawer.setText(userModel.getCompanion());


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
                Log.d("searchView", "closed");
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



    private void initDrawerHeader(){
        ivAvatarDrawer = (ImageView) findViewById(R.id.ivAvatar_Drawer);
        tvProfileNameDrawer = (TextView) findViewById(R.id.tvProfileName_Drawer);
        tvProfilePhoneDrawer = (TextView) findViewById(R.id.tvProfilePhone_Drawer);
    }
    private void setDrawerHeaderContent(){
        Picasso.with(this).load(App.getAvatar()).transform(new CircleTransform()).into(ivAvatarDrawer, new com.squareup.picasso.Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {
                tvProfileNameDrawer.setBackground(new ColorDrawable( getResources().getColor(android.R.color
                        .transparent)));
            }
        });

        tvProfileNameDrawer.setText(App.getGetUserName());
        tvProfilePhoneDrawer.setText(App.getCurrentMobile());
    }
}