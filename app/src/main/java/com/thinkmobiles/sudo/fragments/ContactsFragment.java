package com.thinkmobiles.sudo.fragments;

/**
 * Created by njakawaii on 14.04.2015.
 */

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;
import com.thinkmobiles.sudo.R;
import com.thinkmobiles.sudo.adapters.ContactsListAdapter;
import com.thinkmobiles.sudo.callbacks.ContactsFragmentCallback;
import com.thinkmobiles.sudo.core.rest.RetrofitAdapter;
import com.thinkmobiles.sudo.models.addressbook.UserModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
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
    private Callback<List<UserModel>> mContactsCB;

    private StickyListHeadersListView stickyList;
    private ContactsListAdapter stickyListAdapter;
    private List<UserModel> contactsList;

    private ContactsFragmentCallback contactsFragmentCallback;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.contacts, container, false);
        initComponent();
        setListener();
        initGetContacts();
        makeGetUserRequest();
        return mView;
    }


    private void makeGetUserRequest() {
        RetrofitAdapter.getInterface().getContacts(mContactsCB);
    }

    private void initGetContacts() {
        mContactsCB = new Callback<List<UserModel>>() {
            @Override
            public void success(List<UserModel> userModels, Response response) {
                contactsList = userModels;
                reloadList(userModels);
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
        contactsFragmentCallback = (ContactsFragmentCallback) activity;
    }

    private void initComponent() {

        mBTNAddFriend = (FloatingActionButton) mView.findViewById(R.id.btnAddFriend_CF);

        context = getActivity().getApplicationContext();
        stickyList = (StickyListHeadersListView) mView.findViewById(R.id.lwContactsList);
        stickyListAdapter = new ContactsListAdapter(mActivity);
        stickyList.setAdapter(stickyListAdapter);


    }

    public void reloadList(List<UserModel> contacts) {

        stickyListAdapter.reloadList(contacts);
    }

    private void setListener() {
        mBTNAddFriend.setOnClickListener(this);
        stickyList.setOnItemClickListener(this);
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
        contactsFragmentCallback.setCurrentContact(contactsList.get(i));


        ((ActionBarActivity) mActivity).getSupportActionBar().setTitle(contactsList.get(i).getCompanion());

        String imageUrl = contactsList.get(i).getAvatar();

        if (imageUrl != null && !imageUrl.equalsIgnoreCase("")) {
            Drawable drawable = getDrawableFromUrl(imageUrl);
            ((ActionBarActivity) mActivity).getSupportActionBar().setIcon(drawable);
        }

    }

    private Drawable getDrawableFromUrl(String imageUrl){
        Drawable drawable = null;


            int dimen = (int) context.getResources().getDimension(R.dimen.sc_avatar_size);
            ImageView tempView = new ImageView(context);

            try {
                Bitmap bm = Picasso.with(context)
                        .load(imageUrl)
                        .resize(dimen, dimen).get();
                drawable = new BitmapDrawable(bm);
            } catch (IOException e) {
                e.printStackTrace();
            }



        return  drawable;
    }

    public void searchContactsList(String querry){

        List<UserModel> tempContactsArrayList = new ArrayList<UserModel>();
        if(contactsList != null){
        for(UserModel userModel : contactsList){
            if (userModel.getCompanion().equalsIgnoreCase(querry))
                tempContactsArrayList.add(userModel);
        }
        reloadList(tempContactsArrayList);}
       

    }

    public void reloadCurrentList() {
        if(contactsList != null)
        reloadList(contactsList);
    }
}
