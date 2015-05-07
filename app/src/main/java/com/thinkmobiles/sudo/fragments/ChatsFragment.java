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
    private List<ChatModel> chatsModelList;
    private Callback<List<LastChatsModel>> mLastChatsCB;
    private ChatsListAdapter chatListAdapter;

    private UserModel thisUser;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_chats, container, false);
        initComponent(v);
        initTestingData();
        iniGetChatsCB();
        getLastChats();
        reloadList();
        MainToolbarManager.getCustomInstance(mActivity).changeToolbarTitleAndIcon("name", R.drawable.ic_launcher);
        return v;
    }

    private void iniGetChatsCB() {
        mLastChatsCB = new Callback<List<LastChatsModel>>() {
            @Override
            public void success(List<LastChatsModel> lastChatsModel, Response response) {

            }

            @Override
            public void failure(RetrofitError error) {

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

    public void reloadList() {
        chatListAdapter.reloadList(chatsModelList);
    }

    public void reloadCurrentList() {
        if (chatsModelList != null) reloadList();
    }

    private void initComponent(View view) {
        chatsList = (ListView) view.findViewById(R.id.lvChats_CF);
        chatsList.setDivider(null);
        chatsList.setDividerHeight(0);
        chatListAdapter = new ChatsListAdapter(mActivity);
        chatsList.setAdapter(chatListAdapter);
    }

    private void initTestingData() {

        thisUser = new UserModel();
        UserModel sender = new UserModel();
        thisUser.setCompanion("this user");
        sender.setCompanion("sender");


        ChatModel testChatModel = new ChatModel(sender, thisUser, new NumberModel(), new NumberModel());

        MessageModelOld message1 = new MessageModelOld();
        message1.setSender(thisUser);
        message1.setMessageText("privet lalala 1");
        message1.setTimeStamp(System.currentTimeMillis());

        MessageModelOld message2 = new MessageModelOld();
        message2.setSender(sender);
        message2.setMessageText("privet lalala 2");
        message2.setTimeStamp(System.currentTimeMillis());

        MessageModelOld message3 = new MessageModelOld();
        message3.setSender(thisUser);
        message3.setMessageText("privet lalala 3");
        message3.setTimeStamp(System.currentTimeMillis());

        MessageModelOld message4 = new MessageModelOld();
        message4.setSender(sender);
        message4.setMessageText("privet lalala 4");
        message4.setTimeStamp(System.currentTimeMillis());


        testChatModel.addMessage(message1);
        testChatModel.addMessage(message2);
        testChatModel.addMessage(message3);
        testChatModel.addMessage(message4);


        chatsModelList = new ArrayList<>();
        chatsModelList.add(testChatModel);
    }


    private void getLastChats(){
        RetrofitAdapter.getInterface().getLastChats(mLastChatsCB);
    }

}