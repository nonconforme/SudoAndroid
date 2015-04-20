package com.thinkmobiles.sudo.fragments;

/**
 * Created by njakawaii on 14.04.2015.
 */

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.thinkmobiles.sudo.R;
import com.thinkmobiles.sudo.adapters.ContactsListAdapter;
import com.thinkmobiles.sudo.models.Contacts;

import java.util.ArrayList;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * Created by hp1 on 21-01-2015.
 */
public class ContactsFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    private Activity mActivity;
    private FloatingActionButton mBTNAddFriend;
    private View mView;
    private Dialog mDialog;
    private EditText mETName, mETNumber;
    private Button mBTNCancel, mBTNSave;
    private Context context;

    private StickyListHeadersListView stickyList;
    private ContactsListAdapter stickyListAdapter;
    ArrayList<Contacts> contactsArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.contacts, container, false);
        initComponent();
        setListener();
        reloadList(contactsArrayList);
        return mView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    private void initComponent() {

        mBTNAddFriend = (FloatingActionButton) mView.findViewById(R.id.btnAddFriend_CF);

        context = getActivity().getApplicationContext();
        stickyList = (StickyListHeadersListView) mView.findViewById(R.id.lwContactsList);
        stickyListAdapter = new ContactsListAdapter(context);
        stickyList.setAdapter(stickyListAdapter);
    }

    private void reloadList(ArrayList<Contacts> contacts) {

        stickyListAdapter.reloadList(contacts);
    }

    private void setListener() {
        mBTNAddFriend.setOnClickListener(this);
    }

    private void initDialogComponent() {
        mETName = (EditText) mView.findViewById(R.id.etDialogNameCF);
        mETNumber = (EditText) mView.findViewById(R.id.etDialogNumberCF);
        mBTNCancel = (Button) mView.findViewById(R.id.btnDialogCancelCF);
        mBTNSave = (Button) mView.findViewById(R.id.btnDialogSaveCF);
    }

    private void setDialogListener() {
        mBTNCancel.setOnClickListener(this);
        mBTNSave.setOnClickListener(this);
    }

    // Create dialog for add new friend
    private void addFriendDalog() {
        LayoutInflater inflater = mActivity.getLayoutInflater();
        mView = inflater.inflate(R.layout.dialog_add_friend, null);
        mDialog = new Dialog(mActivity);
        mDialog.setContentView(mView);
        mDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimationTest;
        initDialogComponent();
        setDialogListener();
        mDialog.setTitle(getResources().getString(R.string.cf_dialog_title));
        mDialog.show();
    }

    private boolean isValidateParam(EditText _et) {
        String check = _et.getText().toString();
        return !check.isEmpty();
    }


    private void loadContactList() {
        Contacts contact = new Contacts();
        contact.setFirstName(null);
        contact.setLastName(null);
        contact.setAvatarUrl(null);
        contact.setPhoneNumber1(null);
        contact.setPhoneNumber2(null);
        contact.setPhoneNumber3(null);

        contactsArrayList.add(contact);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddFriend_CF:
                addFriendDalog();
                break;
            case R.id.btnDialogCancelCF:
                mDialog.dismiss();
                break;
            case R.id.btnDialogSaveCF:
                if (isValidateParam(mETName) && isValidateParam(mETNumber)) //TODO Save friends
                    break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(context, "Item " + i + " clicked!", Toast.LENGTH_SHORT).show();
    }
}
