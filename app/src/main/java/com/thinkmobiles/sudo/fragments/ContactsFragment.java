package com.thinkmobiles.sudo.fragments;

/**
 * Created by njakawaii on 14.04.2015.
 */

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.thinkmobiles.sudo.R;
import com.thinkmobiles.sudo.activities.BaseProfileActivity;
import com.thinkmobiles.sudo.activities.ProfileAddActivity;
import com.thinkmobiles.sudo.activities.ProfileEditActivity;
import com.thinkmobiles.sudo.adapters.ContactsListAdapter;
import com.thinkmobiles.sudo.callbacks.ContactsFragmentCallback;
import com.thinkmobiles.sudo.core.rest.RetrofitAdapter;
import com.thinkmobiles.sudo.global.CircleTransform;
import com.thinkmobiles.sudo.models.DefaultResponseModel;
import com.thinkmobiles.sudo.models.addressbook.UserModel;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp1 on 21-01-2015.
 */
public class ContactsFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    private Activity context;
    private FloatingActionButton mBTNAddFriend;
    private View mView;


    private Callback<List<UserModel>> mContactsCB;

    private StickyListHeadersListView stickyList;
    private ContactsListAdapter stickyListAdapter;

    private Callback<DefaultResponseModel> mAddContactCB;
    private List<UserModel> contactsList;
    private Target mTarget;
    private ContactsFragmentCallback contactsFragmentCallback;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.contacts, container, false);
        initComponent();
        setListener();
        initGetContactsCB();
        initAddContactCB();
        initTarget();
        makeGetUserRequest();

        return mView;
    }

    private void initAddContactCB() {
        mAddContactCB = new Callback<DefaultResponseModel>() {
            @Override
            public void success(DefaultResponseModel defaultResponseModel, Response response) {

                makeGetUserRequest();
                Log.d("retrofit", "contact request");

            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("retrofit", "contact request failure");
            }
        };
    }


    private void makeGetUserRequest() {
        RetrofitAdapter.getInterface().getContacts(mContactsCB);
    }

    private void initGetContactsCB() {
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
        context = activity;
        contactsFragmentCallback = (ContactsFragmentCallback) activity;
    }

    private void initComponent() {

        mBTNAddFriend = (FloatingActionButton) mView.findViewById(R.id.btnAddFriend_CF);


        stickyList = (StickyListHeadersListView) mView.findViewById(R.id.lwContactsList);
        stickyList.setDivider(null);
        stickyList.setDividerHeight(0);
        stickyListAdapter = new ContactsListAdapter(context);
        stickyList.setAdapter(stickyListAdapter);


    }

    public void reloadList(List<UserModel> contacts) {

        stickyListAdapter.reloadList(contacts);
    }

    private void setListener() {
        mBTNAddFriend.setOnClickListener(this);
        stickyList.setOnItemClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddFriend_CF:
                addFriendActivity();
                break;

        }
    }

    private void addFriendActivity() {
        ProfileAddActivity.launch(context);
    }


    private void makeAddContactFromActivity(UserModel model) {
        RetrofitAdapter.getInterface().addContact(model, mAddContactCB);
    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        contactsFragmentCallback.setCurrentContact(contactsList.get(i));


        ((ActionBarActivity) context).getSupportActionBar().setTitle(contactsList.get(i).getCompanion());

        String imageUrl = contactsList.get(i).getAvatar();

        if (imageUrl != null && !imageUrl.equalsIgnoreCase("")) {
            Picasso.with(context).load(imageUrl).transform(new CircleTransform()).into(mTarget);
        }

    }

    private void initTarget() {

        mTarget = new Target() {
            ImageView imageView = new ImageView(context);

            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                imageView.setImageBitmap(bitmap);
                Drawable image = imageView.getDrawable();
                ((ActionBarActivity) context).getSupportActionBar().setIcon(image);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {


            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }



        };
    }


    public void searchContactsList(String querry) {

        List<UserModel> tempContactsArrayList = new ArrayList<>();
        if (contactsList != null) {
            for (UserModel userModel : contactsList) {
                if (stringContains(userModel.getCompanion(), querry)) tempContactsArrayList.add(userModel);

            }
            reloadList(tempContactsArrayList);
        }


    }

    public void reloadCurrentList() {
        if (contactsList != null) reloadList(contactsList);
    }

    public static boolean stringContains(String source, String toCheck) {

        return source.toLowerCase().contains(toCheck.toLowerCase()) || source.toUpperCase().contains(toCheck.toUpperCase());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(context, "result", Toast.LENGTH_LONG).show();
        if (resultCode != -1) return;
        if (requestCode == ProfileEditActivity.START_EDIT_PROFILE_ACTIVITY_CODE) {
            makeAddContactFromActivity(loadUserModelFromProfileEditor(data));
        }
    }

    public UserModel loadUserModelFromProfileEditor(Intent intent) {
        return (UserModel) intent.getExtras().getBundle(BaseProfileActivity.USER_MODEL).getSerializable(BaseProfileActivity.USER_MODEL);

    }
}
