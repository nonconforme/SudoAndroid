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

import com.thinkmobiles.sudo.R;

/**
 * Created by hp1 on 21-01-2015.
 */
public class ChatsFragment extends Fragment {


    private Activity mActivity;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_contacts,container,false);
        return v;
    }
    public void searchChatList(String querry) {
    }
    public void reloadCurrentChat() {
    }
}