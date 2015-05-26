package com.thinkmobiles.sudo.fragments;

/**
 * Created by njakawaii on 14.04.2015.
 */

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.thinkmobiles.sudo.R;
import com.thinkmobiles.sudo.activities.ProfileAddActivity;
import com.thinkmobiles.sudo.activities.ProfileViewActivity;
import com.thinkmobiles.sudo.adapters.ContactsListAdapter;
import com.thinkmobiles.sudo.core.rest.RetrofitAdapter;
import com.thinkmobiles.sudo.global.App;
import com.thinkmobiles.sudo.global.Constants;
import com.thinkmobiles.sudo.models.DefaultResponseModel;
import com.thinkmobiles.sudo.models.addressbook.UserModel;
import com.thinkmobiles.sudo.utils.MainToolbarManager;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

import java.util.List;

/**
 * Created by hp1 on 21-01-2015.
 */
public class ContactsFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    private Activity mActivity;
    private ContactsListAdapter mStickyListAdapter;

    private FloatingActionButton mBTNAddFriend;
    private View mView;
    private TextView tvNoContacts;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private StickyListHeadersListView mStickyList;


    public List<UserModel> mContactsList;
    private Callback<List<UserModel>> mContactsCB;
    private Callback<DefaultResponseModel> mDeleteCB;


    private BroadcastReceiver mSearchBroadcastReceiver, mDeleteContactBroadcastReceiver;
    private SwipeRefreshLayout.OnRefreshListener mSwipeRefreshListener;
    private IntentFilter mSearchFilter, mDeleteContactFilter;


    private void createSwipeRefreshListener() {

        mSwipeRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                makeGetUserRequest();
            }
        };
    }


    private void createSearchViewBroadcastReseiver() {
        mSearchFilter = new IntentFilter(Constants.QUERRY);
        mSearchBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                searchContacts(intent.getStringExtra(Constants.QUERRY));
            }
        };
    }

    private void createDeleteContactBroadcastReseiver() {
        mDeleteContactFilter = new IntentFilter(Constants.DELETE_CONTACT);
        mDeleteContactBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                deleteContact(intent.getIntExtra(Constants.POSITION, 0));
            }
        } ;
    }

    private void deleteContact(int position) {

        RetrofitAdapter.getInterface().deleteContact(mContactsList.get(position).getCompanion(), mDeleteCB);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_contacts, container, false);
        createSwipeRefreshListener();
        createSearchViewBroadcastReseiver();
        createDeleteContactBroadcastReseiver();
        findUI();
        initList();
        setListener();
        initGetContactsCB();
        initDeleteContactCB();
        setSwipeRefrechLayoutColor();
        MainToolbarManager.getCustomInstance(mActivity).changeToolbarTitleAndIcon(App.getCurrentUser().getEmail(), App.getCurrentISO());
        makeGetUserRequest();
        return mView;
    }


    private void findUI() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.swipeRefreshLayout_AC);
        tvNoContacts = (TextView) mView.findViewById(R.id.tvNoContacts_CF);
        tvNoContacts.setVisibility(View.INVISIBLE);
        mBTNAddFriend = (FloatingActionButton) mView.findViewById(R.id.btnAddFriend_CF);
        mStickyList = (StickyListHeadersListView) mView.findViewById(R.id.lwContactsList);

    }

    private void setSwipeRefrechLayoutColor() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                makeGetUserRequest();
            }
        });
    }

    private void makeGetUserRequest() {
        RetrofitAdapter.getInterface().getContacts(mContactsCB);
    }

    @Override
    public void onResume() {
        super.onResume();
        mActivity.registerReceiver(mSearchBroadcastReceiver, mSearchFilter);
        mActivity.registerReceiver(mDeleteContactBroadcastReceiver,mDeleteContactFilter);
    }

    @Override
    public void onDestroy() {
        mActivity.unregisterReceiver(mSearchBroadcastReceiver);
        mActivity.unregisterReceiver(mDeleteContactBroadcastReceiver);
        super.onDestroy();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    private void initList() {
        mStickyList.setDivider(null);
        mStickyList.setDividerHeight(0);
        mStickyListAdapter = new ContactsListAdapter(mActivity);
        mStickyList.setAdapter(mStickyListAdapter);
        mStickyList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                if (canScrollUp(mStickyList)) {
                    mSwipeRefreshLayout.setEnabled(false);

                } else {
                    mSwipeRefreshLayout.setEnabled(true);
                }

            }
        });
    }

    public boolean canScrollUp(View view) {
        return ViewCompat.canScrollVertically(view, -1);
    }

    public void reloadList(List<UserModel> contacts) {
        mStickyListAdapter.reloadList(contacts);
        if (contacts != null && contacts.size() > 0) {
            tvNoContacts.setVisibility(View.INVISIBLE);
        } else {
            tvNoContacts.setVisibility(View.VISIBLE);
        }
    }

    private void setListener() {
        mBTNAddFriend.setOnClickListener(this);
        mStickyList.setOnItemClickListener(this);
    }

    private void initGetContactsCB() {
        mContactsCB = new Callback<List<UserModel>>() {
            @Override
            public void success(List<UserModel> userModels, Response response) {
                mContactsList = userModels;
                App.setCurrentContacts(String.valueOf(mContactsList.size()));
                App.setContactsList(userModels);
                reloadList(userModels);
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void failure(RetrofitError error) {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        };
    }

    private void initDeleteContactCB() {
        mDeleteCB = new Callback<DefaultResponseModel>() {
            @Override
            public void success(DefaultResponseModel defaultResponseModel, Response response) {
                makeGetUserRequest();
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(mActivity, mActivity.getString(R.string.failed), Toast.LENGTH_SHORT).show();
            }
        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddFriend_CF:
                addFriendActivity();
                break;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    private void addFriendActivity() {
        int[] startingLocation = new int[2];
        mBTNAddFriend.getLocationOnScreen(startingLocation);
        startingLocation[0] += mBTNAddFriend.getWidth() / 2;
        ProfileAddActivity.startCameraFromLocation(startingLocation, mActivity);
        mActivity.overridePendingTransition(0, 0);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        UserModel tempModel = mStickyListAdapter.getItem(i);
        ImageView transitionView = ((ContactsListAdapter.ViewHolder) view.getTag()).getAvatarIV();
        startProfileViewActivity(tempModel, transitionView);
    }


    private void startProfileViewActivity(UserModel userModel, View view) {
        ProfileViewActivity.launch(mActivity, view, userModel);
    }

    private void searchContacts(String query) {
        RetrofitAdapter.getInterface().searchContacts(query, mContactsCB);
    }

}
