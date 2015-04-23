package com.thinkmobiles.sudo.fragments;

/**
 * Created by njakawaii on 14.04.2015.
 */

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.thinkmobiles.sudo.R;
import com.thinkmobiles.sudo.adapters.ChatListAdapter;
import com.thinkmobiles.sudo.models.chat.ChatModel;

import java.util.List;

/**
 * Created by hp1 on 21-01-2015.
 */
public class ChatsFragment extends Fragment  {


    private Activity mActivity;
    private ListView chatsList;
    private List<ChatModel> chatModelList;
    private ChatListAdapter chatListAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_chats,container,false);
        initComponent(v);
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


    private void initComponent(View view){
        chatsList = (ListView)view.findViewById(R.id.lvChats_CF);
        chatListAdapter  = new ChatListAdapter(mActivity);
        chatsList.setAdapter(chatListAdapter);
    }


}