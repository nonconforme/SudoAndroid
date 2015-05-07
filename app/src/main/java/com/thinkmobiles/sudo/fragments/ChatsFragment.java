package com.thinkmobiles.sudo.fragments;

/**
 * Created by njakawaii on 14.04.2015.
 */

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import android.widget.Toast;
import com.thinkmobiles.sudo.R;
import com.thinkmobiles.sudo.MainToolbarManager;
import com.thinkmobiles.sudo.adapters.ChatsListAdapter;
import com.thinkmobiles.sudo.core.rest.RetrofitAdapter;
import com.thinkmobiles.sudo.models.addressbook.NumberModel;
import com.thinkmobiles.sudo.models.addressbook.UserModel;
import com.thinkmobiles.sudo.models.chat.ChatModel;
import com.thinkmobiles.sudo.models.chat.LastChatsModel;
import com.thinkmobiles.sudo.models.chat.MessageModelOld;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by hp1 on 21-01-2015.
 */
public class ChatsFragment extends Fragment {


    private Activity mActivity;
    private ListView chatsList;
    private Callback<List<LastChatsModel>> mLastChatsCB;
    private ChatsListAdapter chatListAdapter;
    List<LastChatsModel> mLastChatsModel;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_chats, container, false);
        initComponent(v);
        iniGetChatsCB();
        MainToolbarManager.getCustomInstance(mActivity).changeToolbarTitleAndIcon("name", R.drawable.ic_launcher);
        return v;
    }


    @Override
    public void onResume() {
        super.onResume();
        getLastChats();
    }

    private void iniGetChatsCB() {
        mLastChatsCB = new Callback<List<LastChatsModel>>() {
            @Override
            public void success(List<LastChatsModel> lastChatsModel, Response response) {
                mLastChatsModel = lastChatsModel;
                reloadList(mLastChatsModel);
                Toast.makeText(mActivity,lastChatsModel.size(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(mActivity,"error getting chats",Toast.LENGTH_SHORT).show();


            }
        };
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;

    }

    public void searchChatList(String querry) {
    }

    public void reloadList(List<LastChatsModel> chatsModelList) {
        chatListAdapter.reloadList(chatsModelList);
    }


    private void initComponent(View view) {
        chatsList = (ListView) view.findViewById(R.id.lvChats_CF);
        chatsList.setDivider(null);
        chatsList.setDividerHeight(0);
        chatListAdapter = new ChatsListAdapter(mActivity);
        chatsList.setAdapter(chatListAdapter);
    }




    private void getLastChats(){
        RetrofitAdapter.getInterface().getLastChats(mLastChatsCB);
    }

    public void reloadCurrentList() {
        getLastChats();
    }
}