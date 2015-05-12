package com.thinkmobiles.sudo.fragments;

/**
 * Created by njakawaii on 14.04.2015.
 */

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.thinkmobiles.sudo.R;
import com.thinkmobiles.sudo.MainToolbarManager;
import com.thinkmobiles.sudo.activities.ChatActivity;
import com.thinkmobiles.sudo.adapters.ChatsListAdapter;
import com.thinkmobiles.sudo.core.rest.RetrofitAdapter;
import com.thinkmobiles.sudo.global.App;
import com.thinkmobiles.sudo.models.addressbook.UserModel;
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
public class ChatsFragment extends Fragment {


    private Activity mActivity;
    private ListView lvChats;
    private Callback<List<LastChatsModel>> mLastChatsCB;
    private ChatsListAdapter mChatAdapter;
    private List<LastChatsModel> mLastChatsModel;



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
                if (Utils.stringContains(tempModel.getLastmessage().getCompanion().getNumber(), querry)) tempContactsArrayList
                        .add(tempModel);
                else if (Utils.stringContains(tempModel.getLastmessage().getOwner().getNumber(), querry))
                tempContactsArrayList
                        .add(tempModel);

            }
            reloadList(tempContactsArrayList);
        }


    }
    public void reloadList(List<LastChatsModel> chatsModelList) {
        mChatAdapter.reloadList(chatsModelList);
    }


    private void initComponent() {
        mChatAdapter = new ChatsListAdapter(mActivity);
        lvChats.setDivider(null);
        lvChats.setDividerHeight(0);
        lvChats.setAdapter(mChatAdapter);
    }




    private void getLastChats(){
        RetrofitAdapter.getInterface().getLastChats(mLastChatsCB);
    }

    public void reloadCurrentList() {
        getLastChats();
    }

}