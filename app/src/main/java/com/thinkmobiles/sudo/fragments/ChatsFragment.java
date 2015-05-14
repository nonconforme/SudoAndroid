package com.thinkmobiles.sudo.fragments;

/**
 * Created by njakawaii on 14.04.2015.
 */

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.thinkmobiles.sudo.R;
import com.thinkmobiles.sudo.activities.ChatActivity;
import com.thinkmobiles.sudo.utils.MainToolbarManager;
import com.thinkmobiles.sudo.adapters.ChatsListAdapter;
import com.thinkmobiles.sudo.core.rest.RetrofitAdapter;
import com.thinkmobiles.sudo.global.App;
import com.thinkmobiles.sudo.models.chat.LastChatsModel;

import java.util.ArrayList;
import java.util.List;

import com.thinkmobiles.sudo.utils.Utils;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by hp1 on 21-01-2015.
 */
public class ChatsFragment extends Fragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {


    private Activity mActivity;
    private ListView lvChats;
    private Callback<List<LastChatsModel>> mLastChatsCB;
    private ChatsListAdapter mChatAdapter;
    private List<LastChatsModel> mLastChatsModel;

    private AdapterView.OnItemSelectedListener selectItemsListener;
    private boolean selectionMode = false;
    private boolean[] selectionArray;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_chats, container, false);
        findUI(v);

        initComponent();
        iniGetChatsCB();

        return v;
    }

    private void findUI(View _view) {
        lvChats = (ListView) _view.findViewById(R.id.lvChats_CF);

    }


    @Override
    public void onResume() {
        super.onResume();
        getLastChats();
        MainToolbarManager.getCustomInstance(mActivity).changeToolbarTitleAndIcon(App.getCurrentMobile(), App.getCurrentUser().getAvatar());

    }

    private void iniGetChatsCB() {
        mLastChatsCB = new Callback<List<LastChatsModel>>() {
            @Override
            public void success(List<LastChatsModel> lastChatsModel, Response response) {
                mLastChatsModel = lastChatsModel;
                reloadList(mLastChatsModel);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("Retrofit chat Error", error.getMessage());

            }
        };
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;

    }


    public void searchChatList(String querry) {

        List<LastChatsModel> tempContactsArrayList = new ArrayList<>();
        if (mLastChatsModel != null) {
            for (LastChatsModel tempModel : mLastChatsModel) {
                if (Utils.stringContains(tempModel.getLastmessage().getCompanion().getNumber(), querry))
                    tempContactsArrayList.add(tempModel);
                else if (Utils.stringContains(tempModel.getLastmessage().getOwner().getNumber(), querry))
                    tempContactsArrayList.add(tempModel);

            }
            reloadList(tempContactsArrayList);
        }


    }
    public void reloadList(List<LastChatsModel> chatsModelList) {
        mChatAdapter.reloadList(chatsModelList);
    }


    private void initComponent() {
        mChatAdapter = new ChatsListAdapter(mActivity);
        lvChats.setDivider(getResources().getDrawable(R.color.colorLine));
        lvChats.setDividerHeight(1);
        lvChats.setAdapter(mChatAdapter);
        lvChats.setOnItemClickListener(this);
        lvChats.setOnItemLongClickListener(this);


    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

    }


    private void getLastChats() {
        RetrofitAdapter.getInterface().getLastChats(mLastChatsCB);
    }

    public void reloadCurrentList() {
        getLastChats();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        mChatAdapter.setSelection(selectionMode, selectionArray);
        if (!selectionMode) {
            startChatActivity(mLastChatsModel.get(position));
            Log.d("TAG", "]clicked");
        } else {
            controlSelection(position, view);
        }


    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        Log.d("TAG", "long - clicked");
        startSelectionMode();
        controlSelection(i, (View) view.getParent());
        mChatAdapter.setSelection(selectionMode,selectionArray);

        return false;
    }

    private void startChatActivity(LastChatsModel chatModel) {
//        ChatActivity.launch(mActivity, chatModel.getLastmessage().getOwner().getNumber(), chatModel.getLastmessage().getCompanion().getNumber());
    }

    private void startSelectionMode() {
        selectionArray = new boolean[mLastChatsModel.size()];
        selectionMode = true;
    }

    private void stopSelectionMode() {
        selectionMode = false;
    }
    private void checkSelectionNotEmpty(){
        boolean containsSelection = false;
        for(int i = 0; i<selectionArray.length; i++){
            if(selectionArray[i]){
                containsSelection = true;
            }
        }
        if(!containsSelection){
            stopSelectionMode();
        }
    }

    private void controlSelection(int position, View view) {
        selectionArray[position] = !selectionArray[position];
        if (selectionArray[position]) {
            view.setBackgroundResource(R.drawable.bg_chats_item_long_pressed);
        } else {
            view.setBackgroundResource(R.drawable.bg_chats_item_default);

        }
    }

    private void deleteChats() {
        Toast.makeText(mActivity, "detele chats", Toast.LENGTH_SHORT).show();
    }

}