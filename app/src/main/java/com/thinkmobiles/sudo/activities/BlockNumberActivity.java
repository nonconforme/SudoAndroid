package com.thinkmobiles.sudo.activities;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
 import com.thinkmobiles.sudo.R;
import com.thinkmobiles.sudo.adapters.*;
import com.thinkmobiles.sudo.core.rest.RetrofitAdapter;
import com.thinkmobiles.sudo.global.Network;
import com.thinkmobiles.sudo.models.addressbook.UserModel;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.util.List;

/**
 * Created by omar on 13.05.15.
 */
public class BlockNumberActivity extends ActionBarActivity {
    public List<UserModel> mContactsList;

    private Callback<List<UserModel>> mContactsCB;
    private BlockNumberExpandableAdapter adapter;
    private ExpandableListView listView;
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
        overridePendingTransition(0, 0);
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



    private void initList() {
        listView.setDivider(null);
        listView.setDividerHeight(0);


        adapter = new BlockNumberExpandableAdapter(this);
        listView.setAdapter(adapter);
        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                BlockNumberExpandableAdapter.ParentViewHolder  holder  =  ( BlockNumberExpandableAdapter
                        .ParentViewHolder ) v.getTag();
                Log.d("TAG", v.getTag().toString());
                holder.viewSeparator.setVisibility(View.VISIBLE);
                return false;
            }
        });


    }



    private void findUI() {
        listView = (ExpandableListView) findViewById(R.id.slContacts_ABN);




    }

    public void reloadList(List<UserModel> contacts) {

        adapter.reloadList(contacts);
    }

    private void makeGetUserRequest() {
        RetrofitAdapter.getInterface().getContacts(mContactsCB);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Network.isInternetConnectionAvailable(this);
    }
}
