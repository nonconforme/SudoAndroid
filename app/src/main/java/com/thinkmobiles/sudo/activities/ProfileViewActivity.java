package com.thinkmobiles.sudo.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.thinkmobiles.sudo.R;
import com.thinkmobiles.sudo.Utils;
import com.thinkmobiles.sudo.adapters.ProfileViewNumbersAdapter;
import com.thinkmobiles.sudo.custom_views.NonScrollListView;
import com.thinkmobiles.sudo.models.addressbook.NumberModel;
import com.thinkmobiles.sudo.models.addressbook.UserModel;

import java.util.List;

/**
 * Created by omar on 22.04.15.
 */
public class ProfileViewActivity extends ActionBarActivity {
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

    public static final String USER_MODEL = "user_model";
    public static final String TAG = "profile view activity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "started");
        setContentView(R.layout.activity_view_profile);
        loadUserModel();
        initToolbar();
        initComponent();
        initListeners();
        loadContent();
        setContent();
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
            Utils.setImagePicasso(this, urlAvatar, dimen, ivAvatar);
        }
        if (Utils.checkList(myNumberList)) {
            profileViewNumbersAdapter = new ProfileViewNumbersAdapter(this, myNumberList);
            lvNumbers.setAdapter(profileViewNumbersAdapter);
        }

    }

    private void loadContent() {
        firstName = thisUserModel.getCompanion();
        lastName = thisUserModel.getCompanion();
        urlAvatar = thisUserModel.getAvatar();
        myNumberList = thisUserModel.getNumbers();
    }


    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.profileViewToolBar);

        setSupportActionBar(toolbar);

        btnHome = (ImageButton) findViewById(R.id.ibHome_AVC);
        btnAccept = (ImageButton) findViewById(R.id.ibEdit_AVC);

    }

    private void initComponent() {
        tvCardName = (TextView) findViewById(R.id.tvCardNameToolbar_AVC);
        tvUserFirstName = (TextView) findViewById(R.id.tvUserFirstName_AVC);
        tvUserLastName = (TextView) findViewById(R.id.tvUserLastName_AVC);
        ivAvatar = (ImageView) findViewById(R.id.ivAvatar_AVC);
        lvNumbers = (NonScrollListView) findViewById(R.id.lvPhoneNumbersView_AVC);
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
                        Toast.makeText(ProfileViewActivity.this, "Home clicked", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                        break;
                    case R.id.ibEdit_AVC:
                        Toast.makeText(ProfileViewActivity.this, "Home EditClicked", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
        btnHome.setOnClickListener(onToolbarClickListener);
        btnAccept.setOnClickListener(onToolbarClickListener);
    }

    private void loadUserModel() {
        thisUserModel = (UserModel) getIntent().getExtras().getBundle(ProfileViewActivity.USER_MODEL).getSerializable(ProfileViewActivity.USER_MODEL);
    }


}
