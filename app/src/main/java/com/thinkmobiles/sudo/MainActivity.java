package com.thinkmobiles.sudo;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Toast;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.thinkmobiles.sudo.activities.LoginActivity;
import com.thinkmobiles.sudo.callbacks.ContactsFragmentCallback;
import com.thinkmobiles.sudo.core.rest.RetrofitAdapter;
import com.thinkmobiles.sudo.fragments.HomeFragment;
import com.thinkmobiles.sudo.fragments.NumbersFragment;
import com.thinkmobiles.sudo.fragments.RechargeCreditsFragment;
import com.thinkmobiles.sudo.fragments.SettingsFragment;
import com.thinkmobiles.sudo.global.App;
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


public class MainActivity extends ActionBarActivity implements Drawer.OnDrawerItemSelectedListener, Drawer.OnDrawerListener, Drawer.OnDrawerItemClickListener, ContactsFragmentCallback {

    // Declaring Your View and Variables

    private Toolbar toolbar;
    private String mTitle;
    private Drawer.Result mDrawer = null;
    private Callback<DefaultResponseModel> mSignOutCB;

    HomeFragment homeFragment;

    private SearchManager searchManager;
    private SearchView searchView;


    private UserModel selectedContact;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setBaseTitle();
        initToolbar();
        openHomeFragment();
        initDrawer();
        initSignOutCB();

    }


    private void openLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle(mTitle);
/*        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (selectedContact != null)
                    Toast.makeText(getApplicationContext(), selectedContact.getCompanion(), Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getApplicationContext(), "toolbar clicked", Toast.LENGTH_LONG).show();

            }
        });*/

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setIcon(getResources().getDrawable(R.drawable.ic_launcher));



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        initSearchBar(menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen()) {
            mDrawer.closeDrawer();
        } else {
            super.onBackPressed();
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
        InputMethodManager inputMethodManager = (InputMethodManager) MainActivity.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(MainActivity.this.getCurrentFocus().getWindowToken(), 0);
    }

    @Override
    public void onDrawerClosed(View view) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l, IDrawerItem iDrawerItem) {
        Toast.makeText(this, "pos " + pos, Toast.LENGTH_SHORT).show();
        implementClick(pos);

    }

    private void implementClick(int pos) {
        switch (pos){
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
        FragmentReplacer.replaceTopNavigationFragment(this, new RechargeCreditsFragment());
    }

    private void openSettingsFragment() {
        FragmentReplacer.replaceTopNavigationFragment(this, new SettingsFragment());
    }
    private void openNumbersFragment() {

        FragmentReplacer.replaceTopNavigationFragment(this, new NumbersFragment());
    }

    private void openHomeFragment() {
        homeFragment = new HomeFragment();
        FragmentReplacer.replaceTopNavigationFragment(this, homeFragment);
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
        mDrawer = new Drawer()
                .withActivity(this)
                .withToolbar(toolbar)
                .withActionBarDrawerToggle(true)
                .withHeader(R.layout.drawer_header)
                .withOnDrawerListener(this)
                .withOnDrawerItemClickListener(this)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_item_home).withIcon(getResources().getDrawable(R.drawable.ic_contacts_chats)).withBadge("99").withIdentifier(1),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_get_number).withIcon(getResources().getDrawable(R.drawable.ic_get_number)),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_recharge_credits).withIcon(getResources().getDrawable(R.drawable.ic_recharge_credits)).withBadge("6").withIdentifier(2),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_settings).withIcon(getResources().getDrawable(R.drawable.ic_settings)),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_sign_out).withIcon(getResources().getDrawable(R.drawable.ic_sign_out)))
                .build();
    }

    private void initSearchBar(final Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

         searchManager = (SearchManager) MainActivity.this.getSystemService(Context.SEARCH_SERVICE);

         searchView = (SearchView) searchItem.getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(MainActivity.this.getComponentName()));
        searchView.setActivated(true);
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                Log.d("searchView", "closed");
                if(getCurrentTab() == 0)
                    homeFragment.getAdapter().getContactsFragment().reloadCurrentList();
                else
                    homeFragment.getAdapter().getChatFragment().reloadCurrentChat();

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
            String querry = intent.getStringExtra(SearchManager.QUERY);


            if (getCurrentTab() == 0) {
                homeFragment.getAdapter().getContactsFragment().searchContactsList(querry);
            } else {
                homeFragment.getAdapter().getChatFragment().searchChatList(querry);
            }


        }
    }

    private int getCurrentTab() {
        return homeFragment.getCurrentTab();
    }

}