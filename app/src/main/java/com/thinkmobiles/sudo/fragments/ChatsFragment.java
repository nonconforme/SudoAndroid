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
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.thinkmobiles.sudo.Main_Activity;
import com.thinkmobiles.sudo.R;
import com.thinkmobiles.sudo.activities.ChatActivity;
import com.thinkmobiles.sudo.global.Constants;
import com.thinkmobiles.sudo.utils.MainToolbarManager;
import com.thinkmobiles.sudo.adapters.ChatsListAdapter;
import com.thinkmobiles.sudo.core.rest.RetrofitAdapter;
import com.thinkmobiles.sudo.global.App;
import com.thinkmobiles.sudo.models.chat.LastChatsModel;

import java.util.*;
import java.util.concurrent.TimeUnit;

import com.thinkmobiles.sudo.utils.Utils;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by hp1 on 21-01-2015.
 */
public class ChatsFragment extends Fragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, AbsListView.OnScrollListener {


    private Main_Activity mActivity;
    private MainToolbarManager mainToolbarManager;
    private ListView mListView;
    private Callback<List<LastChatsModel>> mLastChatsCB;
    private ChatsListAdapter mChatAdapter;
    private List<LastChatsModel> mChatsList;
    private TextView tvNoChats;
     private   View footerView;


    private boolean selectionMode = false;
    private boolean[] selectionArray;
    private int mPageCount = 1;
    private int mLength = 10;

    private boolean mEndOfList = false;

    private HashSet<Integer> mLoadWatcher = new HashSet<>();

    private BroadcastReceiver mTrashReciever = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String flag = intent.getStringExtra(Constants.FLAG);
            if (flag.equalsIgnoreCase(Constants.ACCEPT)) {
                deleteChats();
            }
            stopSelectionMode();

        }


    };

    private BroadcastReceiver mChatStartedReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            reloadList();
        }
    };

    @Override
    public void onPause() {
        selectionMode = false;
        unregisterSelectionReceiver();

        super.onPause();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_chats, container, false);
        findUI(v);

        initComponent();
        iniGetChatsCB();
        getLastChats();
        return v;
    }

    private void findUI(View _view) {
        mListView = (ListView) _view.findViewById(R.id.lvChats_CF);
        tvNoChats = (TextView) _view.findViewById(R.id.tvNoChats);
        tvNoChats.setVisibility(View.INVISIBLE);

    }


    @Override
    public void onResume() {
        super.onResume();

        MainToolbarManager.getCustomInstance(mActivity).changeToolbarTitleAndIcon(App.getCurrentMobile(), App.getCurrentISO());
        registerSelectionReceiver();
        registerNewChatReceiver();

    }

    private void iniGetChatsCB() {
        mLastChatsCB = new Callback<List<LastChatsModel>>() {
            @Override
            public void success(List<LastChatsModel> lastChatsModel, Response response) {
                hideProgressBar();
                if (lastChatsModel.isEmpty()) mEndOfList = true;
                else {
                    mPageCount++;
                    mChatsList.addAll(lastChatsModel);
                    Toast.makeText(mActivity, String.valueOf(mChatsList.size()), Toast.LENGTH_SHORT);
                    updateList(mChatsList);
                    if (selectionMode) reloadSeletcion(lastChatsModel.size());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                mEndOfList = true;

                Log.d("Retrofit chat Error", error.getMessage());

            }
        };
    }

    public void reloadSeletcion(int grow) {
        boolean[] tempSelectionArray = new boolean[grow];
        selectionArray = concat(selectionArray, tempSelectionArray);

    }

    public boolean[] concat(boolean[] a, boolean[] b) {
        int aLen = a.length;
        int bLen = b.length;
        boolean[] c = new boolean[aLen + bLen];
        System.arraycopy(a, 0, c, 0, aLen);
        System.arraycopy(b, 0, c, aLen, bLen);
        return c;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (Main_Activity) activity;

    }


    public void searchChatList(String querry) {

        List<LastChatsModel> tempContactsArrayList = new ArrayList<>();
        if (mChatsList != null) {
            for (LastChatsModel tempModel : mChatsList) {
                if (Utils.stringContains(tempModel.getLastmessage().getCompanion().getNumber(), querry))
                    tempContactsArrayList.add(tempModel);
                else if (Utils.stringContains(tempModel.getLastmessage().getOwner().getNumber(), querry))
                    tempContactsArrayList.add(tempModel);

            }
            updateList(tempContactsArrayList);
        }


    }

    public void updateList(List<LastChatsModel> chatsModelList) {
        mChatAdapter.reloadList(chatsModelList);
        if (chatsModelList.size() > 0) tvNoChats.setVisibility(View.INVISIBLE);
        else tvNoChats.setVisibility(View.VISIBLE);
    }


    private void initComponent() {
        mChatAdapter = new ChatsListAdapter(mActivity);
        mChatsList = new ArrayList<>();
        footerView  =
                mActivity.getLayoutInflater().inflate(R.layout.loading_footer, null);
        mListView.addFooterView(footerView);
        mListView.setAdapter(mChatAdapter);
        mListView.setOnItemClickListener(this);
        mListView.setOnItemLongClickListener(this);
        mListView.setOnScrollListener(this);
        footerView.setVisibility(View.GONE);


    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

    }


    private void getLastChats() {
        if (!mLoadWatcher.contains(mPageCount)) {
            showProgressBar();
            RetrofitAdapter.getInterface().getLastChatsPages(mPageCount, mLength, mLastChatsCB);
            mLoadWatcher.add(mPageCount);
        }
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

        Log.d("TAG", "clicked");
        if (!selectionMode) {
            startChatActivity(mChatsList.get(position), view);

        } else {
            controlSelection(position);
            mChatAdapter.setSelection(selectionMode, selectionArray);
            checkSelectionNotEmpty();
        }


    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        Log.d("TAG", "long - clicked");
        startSelectionMode();
        controlSelection(i);
        mChatAdapter.setSelection(selectionMode, selectionArray);

        return true;
    }

    private void startChatActivity(LastChatsModel chatModel, final View view) {
        int[] startingLocation = new int[2];
        view.getLocationOnScreen(startingLocation);
        ChatActivity.launch(mActivity, chatModel.getLastmessage().getOwner().getNumber(), chatModel.getLastmessage().getCompanion().getNumber(), startingLocation);
    }

    private void startSelectionMode() {
        selectionArray = new boolean[mChatsList.size()];
        selectionMode = true;
        registerSelectionReceiver();
        mainToolbarManager = MainToolbarManager.getCustomInstance(mActivity);
        mainToolbarManager.enableSearchView(false);
        mainToolbarManager.enableTrashView(true);
        mainToolbarManager.reloadOptionsMenu();
    }


    private void stopSelectionMode() {
        unregisterSelectionReceiver();
        selectionMode = false;
        mainToolbarManager.enableSearchView(true);
        mainToolbarManager.enableTrashView(false);
        mainToolbarManager.reloadOptionsMenu();
        mChatAdapter.setSelection(selectionMode, selectionArray);
        mChatAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        unRegisterNewChatReceiver();
        super.onDestroy();

    }

    private void registerSelectionReceiver() {
        if (selectionMode) mActivity.registerReceiver(mTrashReciever, new IntentFilter(Constants.TRASH_INTENT));
    }

    private void unregisterSelectionReceiver() {
        if (selectionMode) try {
            mActivity.unregisterReceiver(mTrashReciever);
        } catch (Exception e) {
        }
    }

    private void checkSelectionNotEmpty() {
        boolean containsSelection = false;
        if (selectionArray != null) {
            for (int i = 0; i < selectionArray.length - 1; i++) {
                if (selectionArray[i]) {
                    containsSelection = true;
                }
            }
        }
        if (!containsSelection) {
            stopSelectionMode();
        }
    }

    private void controlSelection(int position) {
        selectionArray[position] = !selectionArray[position];

    }

    private void deleteChats() {
        if (selectionArray != null || selectionArray.length == mChatsList.size()) {
            List<LastChatsModel> tempChatList = new ArrayList<>();
            for (int i = 0; i < selectionArray.length - 1; i++) {
                if (!selectionArray[i]) {

                    tempChatList.add(mChatsList.get(i));
                }
            }
            mChatsList = tempChatList;
            selectionArray = new boolean[mChatsList.size()];
            mChatAdapter.reloadList(mChatsList);
            Toast.makeText(mActivity, "detele chats", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {
    }

    public void onScroll(AbsListView view, int firstVisible, int visibleCount, int totalCount) {

        boolean loadMore = /* maybe add a padding */
                firstVisible + visibleCount >= totalCount;

        if (loadMore && mPageCount > 1 && !mEndOfList) {
            getLastChats();
        }


    }

    private void reloadList() {
        mPageCount = 1;
        mEndOfList = false;
        mLoadWatcher = new HashSet<>();
        if (selectionMode) {
            stopSelectionMode();
        }
        getLastChats();
    }

    private void registerNewChatReceiver() {
        mActivity.registerReceiver(mChatStartedReceiver, new IntentFilter(Constants.UPDATE_CHAT_LIST));
    }

    private void unRegisterNewChatReceiver() {
        try {
            mActivity.unregisterReceiver(mChatStartedReceiver);
        } catch (Exception e) {
        }

    }

    private void showProgressBar(){
        footerView.setVisibility(View.VISIBLE);




        }

    private void hideProgressBar(){  /* new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    footerView.setVisibility(View.GONE);


                }
            });

        }
    }).start();
*/
        footerView.setVisibility(View.GONE);


    }


}