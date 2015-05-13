package com.thinkmobiles.sudo.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import com.thinkmobiles.sudo.R;
import com.thinkmobiles.sudo.ToolbarManager;
import com.thinkmobiles.sudo.adapters.BlockNumberAdapter;
import com.thinkmobiles.sudo.adapters.ContactsListAdapter;
import com.thinkmobiles.sudo.core.rest.RetrofitAdapter;
import com.thinkmobiles.sudo.global.App;
import com.thinkmobiles.sudo.models.addressbook.UserModel;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

import java.util.List;

/**
 * Created by omar on 13.05.15.
 */
public class BlockNumberActivity extends ActionBarActivity {
    public List<UserModel>  mContactsList;
    private BlockNumberAdapter mStickyListAdapter;

    private Callback<List<UserModel>> mContactsCB;
    private StickyListHeadersListView stickyList;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_block_number);
        findUI();
        initList();
        initGetContactsCB();
        makeGetUserRequest();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Block Number");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.overridePendingTransition(0, 0);


    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0,0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_block_number, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:

                onBackPressed();
                break;
            case R.id.action_accept:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initGetContactsCB() {
        mContactsCB = new Callback<List<UserModel>>() {
            @Override
            public void success(List<UserModel> userModels, Response response) {
                mContactsList = userModels;

                reloadList(userModels);
            }

            @Override
            public void failure(RetrofitError error) {

            }
        };
    }

    private void setListener() {

    }

    private void initList() {
        stickyList.setDivider(null);
        stickyList.setDividerHeight(0);


        mStickyListAdapter = new BlockNumberAdapter(this);
        stickyList.setAdapter(mStickyListAdapter);
    }


    private void findUI() {
        stickyList = (StickyListHeadersListView)findViewById(R.id.slContacts_ABN);
    }
    public void reloadList(List<UserModel> contacts) {

        mStickyListAdapter.reloadList(contacts);
    }
    private void makeGetUserRequest() {
        RetrofitAdapter.getInterface().getContacts(mContactsCB);
    }
}
