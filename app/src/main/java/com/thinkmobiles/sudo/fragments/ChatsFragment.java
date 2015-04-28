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
import com.thinkmobiles.sudo.adapters.ChatsListAdapter;
import com.thinkmobiles.sudo.models.addressbook.UserModel;
import com.thinkmobiles.sudo.models.chat.ChatModel;
import com.thinkmobiles.sudo.models.chat.MessageModel;

import java.util.List;

/**
 * Created by hp1 on 21-01-2015.
 */
public class ChatsFragment extends Fragment {


    private Activity mActivity;
    private ListView chatsList;
    private List<ChatModel> chatsModelList;
    private ChatsListAdapter chatListAdapter;

    private UserModel thisUser;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_chats, container, false);
        initComponent(v);
        initTestingData();
        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;

    }

    public void searchChatList(String querry) {
    }

    public void reloadCurrentChat() {
    }


    private void initComponent(View view) {
        chatsList = (ListView) view.findViewById(R.id.lvChats_CF);
        chatListAdapter = new ChatsListAdapter(mActivity);
        chatsList.setAdapter(chatListAdapter);
    }

    private void initTestingData() {
        ChatModel testChatModel = new ChatModel();
        thisUser = new UserModel();
        UserModel sender = new UserModel();

        thisUser.setCompanion("this user");
        sender.setCompanion("sender");


        MessageModel message1 = new MessageModel();
        message1.setSender(thisUser);
        message1.setMessageText("privet lalala 1");
        message1.setTimeStamp((long) System.currentTimeMillis() - 100);

        MessageModel message2 = new MessageModel();
        message2.setSender(sender);
        message2.setMessageText("privet lalala 2");
        message2.setTimeStamp((long) System.currentTimeMillis() - 200);

        MessageModel message3 = new MessageModel();
        message3.setSender(thisUser);
        message3.setMessageText("privet lalala 3");
        message3.setTimeStamp((long) System.currentTimeMillis() - 300);

        MessageModel message4 = new MessageModel();
        message4.setSender(sender);
        message4.setMessageText("privet lalala 4");
        message4.setTimeStamp((long) System.currentTimeMillis() - 400);


        testChatModel.addMessage(message1);
        testChatModel.addMessage(message2);
        testChatModel.addMessage(message3);
        testChatModel.addMessage(message4);
    }

}