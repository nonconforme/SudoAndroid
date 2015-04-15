package com.thinkmobiles.sudo;

import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Toast;

import com.mikepenz.iconics.typeface.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.thinkmobiles.sudo.adapters.ViewPagerAdapter;
import com.thinkmobiles.sudo.custom_views.SlidingTabLayout;
import com.thinkmobiles.sudo.fragments.HomeFragment;
import com.thinkmobiles.sudo.global.Constants;
import com.thinkmobiles.sudo.global.FragmentReplacer;


public class MainActivity  extends ActionBarActivity implements  Drawer.OnDrawerItemSelectedListener, Drawer.OnDrawerListener, Drawer.OnDrawerItemClickListener {

    // Declaring Your View and Variables

    private Toolbar toolbar;

    private Drawer.Result drawerResult = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();
//        initAdapter();
//        initTabs();
        openHomeFragment();
        initDrawer();


        }

    private void openHomeFragment() {
        FragmentReplacer.replaceTopNavigationFragment(this, new HomeFragment());
    }

    private void initDrawer() {
        drawerResult =  new Drawer()
                .withActivity(this)
                .withToolbar(toolbar)
                .withActionBarDrawerToggle(true)
                .withHeader(R.layout.drawer_header)
                .withOnDrawerListener(this)
                .withOnDrawerItemClickListener(this)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_item_home).withIcon(FontAwesome.Icon.faw_home).withBadge("99").withIdentifier(1),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_get_number).withIcon(FontAwesome.Icon.faw_mobile_phone),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_recharge_credits).withIcon(FontAwesome.Icon.faw_money).withBadge("6").withIdentifier(2),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_sign_out).withIcon(FontAwesome.Icon.faw_sign_out),
//                        new SectionDrawerItem().withName(R.string.drawer_item_settings),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_settings).withIcon(FontAwesome.Icon.faw_calculator)
//                        new DividerDrawerItem(),
//                        new SecondaryDrawerItem().withName(R.string.drawer_item_contact).withIcon(FontAwesome.Icon.faw_github).withBadge("12+").withIdentifier(1)
                )
                .build();

    }


    private void initToolbar(){
        // Creating The Toolbar and setting it as the Toolbar for the activity

        toolbar = (Toolbar) findViewById(R.id.tool_bar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setIcon(getDrawable(R.drawable.ic_launcher));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        // Закрываем Navigation Drawer по нажатию системной кнопки "Назад" если он открыт
        if (drawerResult.isDrawerOpen()) {
            drawerResult.closeDrawer();
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
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l, IDrawerItem iDrawerItem) {
        Toast.makeText(this,"pos " + i, Toast.LENGTH_SHORT).show();

    }
}