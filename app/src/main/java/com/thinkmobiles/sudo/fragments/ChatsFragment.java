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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.thinkmobiles.sudo.Main_Activity;
import com.thinkmobiles.sudo.R;
import com.thinkmobiles.sudo.activities.ChatActivity;
import com.thinkmobiles.sudo.adapters.ChatsListAdapter;
import com.thinkmobiles.sudo.core.rest.RetrofitAdapter;
import com.thinkmobiles.sudo.global.App;
import com.thinkmobiles.sudo.global.Constants;
import com.thinkmobiles.sudo.models.DefaultResponseModel;
import com.thinkmobiles.sudo.models.addressbook.NumberModel;
import com.thinkmobiles.sudo.models.chat.LastChatsModel;
import com.thinkmobiles.sudo.utils.MainToolbarManager;
import com.thinkmobiles.sudo.utils.ChatsSelectionHelper;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by hp1 on 21-01-2015.
 */
public class ChatsFragment extends Fragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, AbsListView.OnScrollListener {


    private Main_Activity mActivity;
    private MainToolbarManager mainToolbarManager;

    private Callback<List<LastChatsModel>> mLastChatsCB;
    private Callback<DefaultResponseModel> mDeleteChatCB;

    private ChatsListAdapter mChatAdapter;
    private ListView mListView;
    private TextView tvNoChats;
    private View footerView;

    private BroadcastReceiver mTrashReciever;
    private BroadcastReceiver mChatStartedReceiver;

    private List<LastChatsModel> mChatsList;


    private HashSet<Integer> mLoadWatcher = new HashSet<>();


    private int mPageCount = 1;
    private int mLength = 10;
    private boolean mEndOfList = false;

    private ChatsSelectionHelper mSelectionHelper;


    private void ininSelectionHelper() {
        mSelectionHelper = new ChatsSelectionHelper() {
            @Override
            public void stopParentSelectionMode() {
                stopSelectionMode();
            }
        };
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ininSelectionHelper();
        initTrashBroadcastReceiver();
        initRefreshListBroadcastReceiver();
    }

    private void initTrashBroadcastReceiver() {
        mTrashReciever = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                String flag = intent.getStringExtra(Constants.FLAG);
                if (flag.equalsIgnoreCase(Constants.ACCEPT)) {
                    deleteChats();
                }
                stopSelectionMode();
            }

        };
    }

    private void initRefreshListBroadcastReceiver() {
        mChatStartedReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                reloadList();
            }
        };

    }


    @Override
    public void onPause() {
        mSelectionHelper.stopSelectionMode();
        unregisterSelectionReceiver();
        super.onPause();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_chats, container, false);
        findUI(v);
        initComponent();
        initListeners();
        iniGetChatsCB();
        initDeleteChatCB();
        reloadList();
        return v;
    }

    private void initDeleteChatCB() {
        mDeleteChatCB = new Callback<DefaultResponseModel>() {
            @Override
            public void success(DefaultResponseModel defaultResponseModel, Response response) {

                if (mSelectionHelper.removeDeletedItem()) {
                    deleteSingleChat();
                } else {
                    hideProgressBar();
                    reloadList();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                hideProgressBar();
                Toast.makeText(mActivity, mActivity.getString(R.string.failed), Toast.LENGTH_SHORT).show();
                mSelectionHelper.stopSelectionMode();
            }
        };
    }

    private void findUI(View _view) {
        footerView = _view.findViewById(R.id.progressLayout);
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
                    if (mSelectionHelper.isSelectionMode()) mSelectionHelper.growSelecction(lastChatsModel.size());
                    mPageCount++;
                    mChatsList.addAll(lastChatsModel);
                    updateList(mChatsList);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                mEndOfList = true;
            }
        };
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (Main_Activity) activity;
    }



    public void updateList(List<LastChatsModel> chatsModelList) {
        mChatAdapter.setSelection(mSelectionHelper.isSelectionMode(), mSelectionHelper.getSelection());
        mChatAdapter.reloadList(chatsModelList);

        if (chatsModelList.size() > 0) tvNoChats.setVisibility(View.INVISIBLE);
        else tvNoChats.setVisibility(View.VISIBLE);
    }


    private void initComponent() {
        mChatAdapter = new ChatsListAdapter(mActivity);
        mChatsList = new ArrayList<>();
        mListView.setAdapter(mChatAdapter);
        footerView.setVisibility(View.GONE);
    }


    private void initListeners() {
        mListView.setOnItemClickListener(this);
        mListView.setOnItemLongClickListener(this);
        mListView.setOnScrollListener(this);
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

        if (!mSelectionHelper.isSelectionMode()) {
            String avatarUrl = mChatAdapter.getCompanionAvatar(position);
            startChatActivity(mChatsList.get(position), view, avatarUrl);

        } else {
            mSelectionHelper.addToSelection(position);
            mChatAdapter.setSelection(mSelectionHelper.isSelectionMode(), mSelectionHelper.getSelection());
            mSelectionHelper.checkSelectionNotEmpty();
        }


    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        startSelectionMode();
        mSelectionHelper.addToSelection(i);
        mChatAdapter.setSelection(mSelectionHelper.isSelectionMode(), mSelectionHelper.getSelection());
        return true;
    }

    private void startChatActivity(LastChatsModel chatModel, final View view, String avatarUrl) {
        int[] startingLocation = new int[2];
        view.getLocationOnScreen(startingLocation);
        ChatActivity.launch(mActivity, chatModel.getLastmessage().getOwner().getNumber(), chatModel.getLastmessage().getCompanion().getNumber(), avatarUrl, startingLocation);
    }

    private void startSelectionMode() {

        mSelectionHelper.startSelectionMode(mChatsList);

        registerSelectionReceiver();
        mainToolbarManager = MainToolbarManager.getCustomInstance(mActivity);
        mainToolbarManager.enableSearchView(false);
        mainToolbarManager.enableTrashView(true);
        mainToolbarManager.reloadOptionsMenu();
    }


    private void stopSelectionMode() {
        unregisterSelectionReceiver();

        mainToolbarManager.enableSearchView(true);
        mainToolbarManager.enableTrashView(false);
        mainToolbarManager.reloadOptionsMenu();

        mSelectionHelper.stopSelectionMode();
        mChatAdapter.setSelection(mSelectionHelper.isSelectionMode(), mSelectionHelper.getSelection());
        mChatAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        unRegisterNewChatReceiver();
        super.onDestroy();
    }

    private void registerSelectionReceiver() {
        if (mSelectionHelper.isSelectionMode())
            mActivity.registerReceiver(mTrashReciever, new IntentFilter(Constants.TRASH_INTENT));
    }

    private void unregisterSelectionReceiver() {
        if (mSelectionHelper.isSelectionMode()) try {
            mActivity.unregisterReceiver(mTrashReciever);
        } catch (Exception e) {
        }
    }


    private void deleteChats() {
        if (mSelectionHelper.isSelectionMode()) {
            mChatAdapter.setSelection(mSelectionHelper.isSelectionMode(), mSelectionHelper.getSelection());

            mSelectionHelper.splitList();

            if (mSelectionHelper.getDeleteList().size() > 0) {
                showProgressBar();
                deleteSingleChat();
            }

        }

    }


    private void deleteSingleChat() {

            LastChatsModel chatModel = mSelectionHelper.getLastDeleteItem();
            String[] numbers = getUserNumber(chatModel);

            if (numbers[0] != null && numbers[1] != null) {
                RetrofitAdapter.getInterface().deleteChat(numbers[0], numbers[1], mDeleteChatCB);
            } else {
                hideProgressBar();
            }
    }


    private String[] getUserNumber(LastChatsModel chatModel) {
        String[] numbers = new String[2];
        for (NumberModel numberModel : App.getCurrentUser().getNumbers()) {
            if (numberModel.getNumber().equalsIgnoreCase(chatModel.getLastmessage().getCompanion().getNumber())) {
                numbers[0] = chatModel.getLastmessage().getCompanion().getNumber();
                numbers[1] = chatModel.getLastmessage().getOwner().getNumber();
                return numbers;
            }
        }
        for (NumberModel numberModel : App.getCurrentUser().getNumbers()) {
            if (numberModel.getNumber().equalsIgnoreCase(chatModel.getLastmessage().getOwner().getNumber())) {
                numbers[0] = chatModel.getLastmessage().getOwner().getNumber();
                numbers[1] = chatModel.getLastmessage().getCompanion().getNumber();
                return numbers;
            }
        }
        numbers[0] = App.getCurrentMobile();
        if (!App.getCurrentMobile().equalsIgnoreCase(chatModel.getLastmessage().getCompanion().getNumber())) {
            numbers[1] = chatModel.getLastmessage().getCompanion().getNumber();
        } else {
            numbers[1] = chatModel.getLastmessage().getOwner().getNumber();
        }
        return numbers;

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

        mChatsList = new ArrayList<>();
        mPageCount = 1;
        mEndOfList = false;
        mLoadWatcher = new HashSet<>();
        mChatAdapter = new ChatsListAdapter(mActivity);
        mListView.setAdapter(mChatAdapter);
        if (mSelectionHelper.isSelectionMode()) {
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

    private void showProgressBar() {
        footerView.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        footerView.setVisibility(View.GONE);
    }


}