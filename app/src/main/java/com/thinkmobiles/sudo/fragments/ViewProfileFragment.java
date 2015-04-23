package com.thinkmobiles.sudo.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.thinkmobiles.sudo.R;
import com.thinkmobiles.sudo.Utils;
import com.thinkmobiles.sudo.activities.ProfileActivity;
import com.thinkmobiles.sudo.adapters.ProfileViewNumbersAdapter;
import com.thinkmobiles.sudo.custom_views.NonScrollListView;
import com.thinkmobiles.sudo.models.addressbook.NumberModel;
import com.thinkmobiles.sudo.models.addressbook.UserModel;

import java.util.List;

/**
 * Created by omar on 23.04.15.
 */
public class ViewProfileFragment extends Fragment {
    private Toolbar toolbar;
    private ImageButton btnHome, btnAccept;
    private View.OnClickListener onToolbarClickListener;
    private TextView tvCardName, tvUserFirstName, tvUserLastName;
    private ImageView ivAvatar;
    private NonScrollListView lvNumbers;
    private ProfileViewNumbersAdapter profileViewNumbersAdapter;

    private UserModel thisUserModel;
    private String firstName, lastName, urlAvatar;
    private List<NumberModel> myNumberList;
    private ActionBarActivity context;


    public static ViewProfileFragment newInstance(UserModel userModel) {
        ViewProfileFragment f = new ViewProfileFragment();
        Bundle args = new Bundle();
        args.putSerializable(ProfileActivity.USER_MODEL, userModel);
        f.setArguments(args);
        return f;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        thisUserModel = (UserModel) getArguments().getSerializable(ProfileActivity.USER_MODEL);

        super.onCreate(savedInstanceState);

    }

    @Override
    public void onAttach(Activity activity) {
        context = (ActionBarActivity) activity;
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_view_profile, container,
                false);

        initToolbar(rootView);
        initComponent(rootView);
        initListeners();
        loadContent();
        setContent();


        return rootView;
    }

    private void initToolbar(View container) {
        toolbar = (Toolbar) container.findViewById(R.id.profileViewToolBar);

        context.setSupportActionBar(toolbar);

        btnHome = (ImageButton) container.findViewById(R.id.ibHome_AVC);
        btnAccept = (ImageButton) container.findViewById(R.id.ibEdit_AVC);

    }

    private void setContent() {
        if (Utils.checkString(firstName)) {
            tvCardName.setText(firstName);
            tvUserFirstName.setText(firstName);
        }

        if (Utils.checkString(lastName)) {
            tvCardName.setText(tvCardName.getText() + " " + lastName);
            tvUserLastName.setText(firstName);
        }

        if (Utils.checkString(urlAvatar)) {
            int dimen = (int) getResources().getDimension(R.dimen.avc_card_avatar_size);
            Utils.setImagePicasso(context, urlAvatar, dimen, ivAvatar);
        }
        if (Utils.checkList(myNumberList)) {
            profileViewNumbersAdapter = new ProfileViewNumbersAdapter(context, myNumberList);
            lvNumbers.setAdapter(profileViewNumbersAdapter);
        }

    }

    private void loadContent() {
        firstName = thisUserModel.getCompanion();
     /*   lastName = thisUserModel.getCompanion();*/
        urlAvatar = thisUserModel.getAvatar();
        myNumberList = thisUserModel.getNumbers();
    }

    private void initComponent(View container) {
        tvCardName = (TextView) container.findViewById(R.id.tvCardNameToolbar_AVC);
        tvUserFirstName = (TextView) container.findViewById(R.id.tvUserFirstName_AVC);
        tvUserLastName = (TextView) container.findViewById(R.id.tvUserLastName_AVC);
        ivAvatar = (ImageView) container.findViewById(R.id.ivAvatar_AVC);
        lvNumbers = (NonScrollListView) container.findViewById(R.id.lvPhoneNumbersView_AVC);
    }

    private void initListeners() {
        initToolbarOnCLickListener();
    }

    private void initToolbarOnCLickListener() {
        onToolbarClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.ibHome_AVC:
                        Toast.makeText(context, "Home clicked", Toast.LENGTH_SHORT).show();
                        context.onBackPressed();
                        break;
                    case R.id.ibEdit_AVC:
                        Toast.makeText(context, "Home EditClicked", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
        btnHome.setOnClickListener(onToolbarClickListener);
        btnAccept.setOnClickListener(onToolbarClickListener);
    }


}
