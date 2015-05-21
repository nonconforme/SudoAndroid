package com.thinkmobiles.sudo.adapters;

/**
 * Created by njakawaii on 14.04.2015.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.thinkmobiles.sudo.fragments.ChatsFragment;
import com.thinkmobiles.sudo.fragments.ContactsFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {


    private ContactsFragment contactsFragment;
    private ChatsFragment chatsFragment;

    private CharSequence Titles[];
    private int NumbOfTabs;

    public ViewPagerAdapter(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);
        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;

    }

    @Override
    public Fragment getItem(int position) {

        if (position == 0) {
            contactsFragment = new ContactsFragment();
            return contactsFragment;
        } else {
            chatsFragment = new ChatsFragment();
            return chatsFragment;
        }


    }


    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }


    @Override
    public int getCount() {
        return NumbOfTabs;
    }


}
