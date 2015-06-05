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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import com.mikepenz.materialdrawer.Drawer;
import com.thinkmobiles.sudo.activities.LoginActivity;
import com.thinkmobiles.sudo.adapters.DrawerMenuAdapter;
import com.thinkmobiles.sudo.adapters.DrawerPhoneListAdapter;
import com.thinkmobiles.sudo.core.rest.RetrofitAdapter;
import com.thinkmobiles.sudo.fragments.HomeFragment;
import com.thinkmobiles.sudo.fragments.RechargeCreditsFragment;
import com.thinkmobiles.sudo.fragments.SettingsFragment;
import com.thinkmobiles.sudo.fragments.numbers.BaseNumbersFragment;
import com.thinkmobiles.sudo.fragments.numbers.NumberMainFragment;
import com.thinkmobiles.sudo.gcm.DeviceManager;
import com.thinkmobiles.sudo.gcm.GcmHelper;
import com.thinkmobiles.sudo.global.App;
import com.thinkmobiles.sudo.global.Constants;
import com.thinkmobiles.sudo.global.Network;
import com.thinkmobiles.sudo.models.DefaultResponseModel;
import com.thinkmobiles.sudo.models.DeviceID;
import com.thinkmobiles.sudo.models.DrawerMenuItemModel;
import com.thinkmobiles.sudo.utils.*;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.thinkmobiles.sudo.global.DrawerConstants.*;
import static com.thinkmobiles.sudo.utils.CountryHelper.setCountryByIso;


public class Main_Activity extends ActionBarActivity implements Drawer.OnDrawerListener, AdapterView.OnItemClickListener, View.OnClickListener, SearchView.OnQueryTextListener {


    private Drawer.Result mDrawer = null;
    private Callback<DefaultResponseModel> mSignOutCB;
    private MainToolbarManager mToolbarManager;
    private HomeFragment homeFragment;
    private GcmHelper gcmHelper;


    private ImageView ivAvatarDrawer, ivSpinner_Drawer;
    private TextView tvName;
    private ProgressBar progressBar;
    private RelativeLayout rlSpinnerChanger;

    private ListView drawerListView;
    private MenuItem menuItemDelete;

    private DrawerPhoneListAdapter mDrawerPhoneListAdapter;
    private DrawerMenuAdapter mDrawerMenuAdapter;
    private SearchManager searchManager;
    private SearchView searchView;

    private List<DrawerMenuItemModel> mDrawerMenuList;

    private String mTitle;

    private Callback<DefaultResponseModel> mGCMCallback;

    public void refreshDrawerMenu() {
        initDrawerMenuList();
        mDrawerMenuAdapter.reloadList(mDrawerMenuList);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userNullCheck();
        setContentView(R.layout.activity_main);
        initToolbar();
        initProgressBar();
        openHomeFragment();
        initDrawer();
        findHeaderUI();

        initSignOutCB();
        initGCMCallback();
        registerGCM();
        sendDeviceId();
    }

    private void userNullCheck() {
        if (App.getCurrentUser() == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
    private void registerGCM() {
        gcmHelper = new GcmHelper(this);
        gcmHelper.registerDevice();
    }

    public void setHeaderContent() {
        setBaseTitle();
        if (!ContactManager.getNumbers().isEmpty()) {
            if (App.getCurrentISO() == null || App.getCurrentISO().isEmpty()) {
                setDrawerIcon(ContactManager.getNumbers().get(0).getCountryIso());
                App.setCurrentISO(ContactManager.getNumbers().get(0).getCountryIso());
            } else {
                setDrawerIcon(App.getCurrentISO());
                mToolbarManager.setToolbarIcon(App.getCurrentISO());
            }
            mToolbarManager.setToolbarIcon(App.getCurrentISO());
            ivSpinner_Drawer.setVisibility(View.VISIBLE);
        } else {
            ivSpinner_Drawer.setVisibility(View.INVISIBLE);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (Network.isInternetConnectionAvailable(this)) {
            if (gcmHelper != null) gcmHelper.checkPlayServices();
        }
        if (App.getCurrentUser() == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        } else {
            setHeaderContent();
        }
    }

    private void openLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void initToolbar() {
        mToolbarManager = MainToolbarManager.getCustomInstance(this);
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
            onHomePressed();
        }
        if (item.getItemId() == R.id.action_detele) {
            Intent trashIntent = new Intent(Constants.TRASH_INTENT);
            trashIntent.putExtra(Constants.FLAG, Constants.ACCEPT);
            sendBroadcast(trashIntent);
        }
        return super.onOptionsItemSelected(item);
    }


    private void onHomePressed() {
        Utils.hideSoftKeyboard(this);
        if (mToolbarManager.isShowTrachView()) {
            Intent trashIntent = new Intent(Constants.TRASH_INTENT);
            trashIntent.putExtra(Constants.FLAG, Constants.CANCEL);
            sendBroadcast(trashIntent);

        } else {
            if (!mToolbarManager.isShowDrawer()) onBackPressed();
            else {
                if (mDrawer.isDrawerOpen()) {
                    mDrawer.closeDrawer();
                } else {
                    mDrawer.openDrawer();
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (mToolbarManager.isShowDrawer()) {
            if (mDrawer.isDrawerOpen()) {
                finish();
            } else {
                mDrawer.openDrawer();
            }
        } else {
            BaseNumbersFragment.goBack();
        }

    }

    public void enableDrawer(boolean show) {
        mToolbarManager.setShowDrawer(show);
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
        if (Network.isInternetConnectionAvailableNoDialog(this)) {

            RetrofitAdapter.getInterface().sigOut(mSignOutCB);
        } else {
            Toast.makeText(this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        }

    }

    private void setBaseTitle() {

        mTitle = App.getCurrentMobile();
        tvName.setText(mTitle);
    }

    private void initDrawerMenuList() {
        mDrawerMenuList = new ArrayList<>();
        mDrawerMenuList.add(new DrawerMenuItemModel(R.string.drawer_item_home, App.getCurrentChats(), R.drawable.ic_contacts_chats));
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
        mDrawer = new Drawer().withActivity(this).withToolbar(mToolbarManager.getToolbar()).withActionBarDrawerToggle(true).
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

    private void initSearchBar(final Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        menuItemDelete = menu.findItem(R.id.action_detele);
        MenuItem searchItem = menu.findItem(R.id.action_search);

        searchManager = (SearchManager) Main_Activity.this.getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) searchItem.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(Main_Activity.this.getComponentName()));
        searchView.setActivated(true);

        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                sendSearchBroadcastQuery("");
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
    protected void onNewIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            sendSearchBroadcastQuery(query);


        }
    }

    private void sendSearchBroadcastQuery(String query) {
        Intent broadcastIntent = new Intent(Constants.QUERRY);
        broadcastIntent.putExtra(Constants.QUERRY, query);
        sendBroadcast(broadcastIntent);
        searchView.setQuery(query, false);
    }

    @Override
    protected boolean onPrepareOptionsPanel(View view, Menu menu) {
        MenuItem searchItem = menu.findItem(R.id.action_search);
        if (mToolbarManager.isShowSearchView()) searchItem.setVisible(true);
        else searchItem.setVisible(false);
        if (mToolbarManager.isShowTrachView()) {
            menuItemDelete.setVisible(true);
        } else {
            menuItemDelete.setVisible(false);
        }
        return super.onPrepareOptionsPanel(view, menu);
    }


    private void setDrawerIcon(String countryISO) {
        setCountryByIso(this, ivAvatarDrawer, countryISO, 100);

    }

    @Override
    public void onClick(View view) {
        if (App.getCurrentUser().getNumbers() != null && App.getCurrentUser().getNumbers().size() > 0) {
            if (mToolbarManager.isShowListDrawer()) {
                mToolbarManager.setShowListDrawer(false);
                ivSpinner_Drawer.setImageResource(R.drawable.ic_spinner);
                initDefaultDrawer();
            } else {
                mToolbarManager.setShowListDrawer(true);
                ivSpinner_Drawer.setImageResource(R.drawable.ic_spinner_rotate);
                initPhoneListDrawer();
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {

        if (!mToolbarManager.isShowListDrawer()) {
            view.setSelected(true);
            implementClick(pos);
            for (int j = 0; j < adapterView.getChildCount(); j++)
                adapterView.getChildAt(j).setBackgroundColor(Color.TRANSPARENT);

            view.setBackgroundColor(Color.LTGRAY);
            mDrawer.closeDrawer();
        } else {

            drawerSelectNumber(pos);
        }

    }

    private void drawerSelectNumber(int pos) {
        if (pos > 0) {
            App.setCurrentMobile(ContactManager.getNumbers().get(pos - 1).getNumber());
            setDrawerIcon(ContactManager.getNumbers().get(pos - 1).getCountryIso());
            App.setCurrentISO(ContactManager.getNumbers().get(pos - 1).getCountryIso());

        } else {
            App.setCurrentMobile(ContactManager.getNumbers().get(0).getNumber());
            setDrawerIcon(ContactManager.getNumbers().get(0).getCountryIso());
            App.setCurrentISO(ContactManager.getNumbers().get(0).getCountryIso());
        }
        mToolbarManager.changeToolbarTitle(App.getCurrentMobile());
        mToolbarManager.setToolbarIcon(App.getCurrentISO());
        setBaseTitle();
        onClick(new View(this));
    }


    @Override
    public boolean onQueryTextSubmit(final String s) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Main_Activity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        searchView.setIconified(false);
                        searchView.setQuery(s, false);
                        searchView.clearFocus();
                    }
                });
            }
        }).start();

        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {


        return false;
    }


    private void sendDeviceId() {

        DeviceID deviceID = new DeviceID();
        deviceID.setChannelId(DeviceManager.loadIds(this));
        deviceID.setProvider("GOOGLE");
        RetrofitAdapter.getInterface().sendDeviceId(JsonHelper.makeJsonDeviceId(deviceID), mGCMCallback);

    }

    private void initGCMCallback() {
        mGCMCallback = new Callback<DefaultResponseModel>() {
            @Override
            public void success(DefaultResponseModel defaultResponseModel, Response response) {
                Log.d("DeviceID", "Succees");
            }

            @Override
            public void failure(RetrofitError error) {

            }
        };
    }
}