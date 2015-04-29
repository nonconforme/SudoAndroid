package com.thinkmobiles.sudo.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import com.squareup.picasso.Picasso;
import com.thinkmobiles.sudo.R;
import com.thinkmobiles.sudo.Utils;
import com.thinkmobiles.sudo.adapters.ProfileEditNumbersAdapter;
import com.thinkmobiles.sudo.custom_views.NonScrollListView;
import com.thinkmobiles.sudo.models.addressbook.NumberModel;
import com.thinkmobiles.sudo.models.addressbook.UserModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by omar on 23.04.15.
 */
abstract public class BaseProfileEditActivity extends BaseProfileActivity {

    private EditText etUserFirstName;
    private ImageView ivChangeAvatar;
    private Button btnChangeAvatar;
    private NonScrollListView lvNumbers;
    private ProfileEditNumbersAdapter profileEditNumbersAdapter;


    private View.OnClickListener mOnClickListener;

    protected UserModel thisUserModel;
    private String firstName,  urlAvatar, filepathAvatar;
    private List<NumberModel> myNumberList;
    private DoneOnEditorActionListener doneOnEditorActionListener;


    public static final String EXTRA_IMAGE = "DetailActivity:image";
    private static final int GET_IMAGE_REQUEST_CODE = 1;
    public static final int START_EDIT_PROFILE_ACTIVITY_CODE = 231;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_edit_profile;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initComponent();
        defineOnClickListener();
        setOnClickListener();

    }

    protected void setContent() {
        if (Utils.checkString(firstName)) {
            etUserFirstName.setText(firstName);
        }


        if (Utils.checkList(myNumberList)) {
            profileEditNumbersAdapter.reloadList(myNumberList);

        }
        Picasso.with(this).load(getIntent().getStringExtra(EXTRA_IMAGE)).into(ivChangeAvatar);
    }

    protected void loadContent() {
        firstName = thisUserModel.getCompanion();
        urlAvatar = thisUserModel.getAvatar();
        myNumberList = thisUserModel.getNumbers();
    }

    protected void initComponent() {
        etUserFirstName = (EditText) findViewById(R.id.etUserFirstName_AVC);

        lvNumbers = (NonScrollListView) findViewById(R.id.lvPhoneNumbersView_AVC);
        ivChangeAvatar = (ImageView) findViewById(R.id.ivChangeAvatarIcon_AVC);
        btnChangeAvatar = (Button) findViewById(R.id.btnChangeAvatar_AVC);
        doneOnEditorActionListener = new DoneOnEditorActionListener();
        etUserFirstName.setOnEditorActionListener(doneOnEditorActionListener);

        profileEditNumbersAdapter = new ProfileEditNumbersAdapter(this);
        lvNumbers.setDivider(null);
        lvNumbers.setDividerHeight(0);
        lvNumbers.setAdapter(profileEditNumbersAdapter);

        View footerView = ((LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footer_add_phone_number_item, null, false);
        footerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profileEditNumbersAdapter.addBlankNumberView();
            }
        });
        lvNumbers.addFooterView(footerView);
    }

    protected void loadUserModel() {
        thisUserModel = (UserModel) getIntent().getExtras().getBundle(BaseProfileActivity.USER_MODEL).getSerializable(BaseProfileActivity.USER_MODEL);
        Log.d(TAG, thisUserModel.getCompanion());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                setResult(RESULT_CANCELED, null);
                onBackPressed();
                break;
            case R.id.action_accept:
                updateNumberList();
                etUserFirstName.onEditorAction(1);
                updateUserModel();
                returnEditedProfile();

                break;

        }
        return super.onOptionsItemSelected(item);
    }


    protected void setOnClickListener() {
        btnChangeAvatar.setOnClickListener(mOnClickListener);
    }

    protected void defineOnClickListener() {
        mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.btnChangeAvatar_AVC) reLoadAvatar();
            }
        };
    }


    protected void reLoadAvatar() {
        loadAvatarFromGallery();

    }

    protected void updateNumberList() {
        myNumberList = profileEditNumbersAdapter.getNumbersList();
        thisUserModel.setNumbers(myNumberList);
    }

    protected void updateUserModel() {
        updateNumberList();
        thisUserModel.setAvatar(urlAvatar);
    }


    protected void loadAvatarFromGallery() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, GET_IMAGE_REQUEST_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("mediastore result ", String.valueOf(resultCode));
        if (resultCode != -1) return;

        if (requestCode == GET_IMAGE_REQUEST_CODE) {

            Uri avatarUri = data.getData();
            if (avatarUri != null) {
                int dimen;

                Log.d("mediastore uri ", avatarUri.toString());

                dimen = (int) getResources().getDimension(R.dimen.avc_change_avatar_size);
                Picasso.with(this).load(avatarUri).resize(dimen, dimen).centerCrop().into(ivChangeAvatar);
            }

        }

    }


    abstract protected void returnEditedProfile();

    protected boolean checkNewName() {

         if (firstName == null || firstName.equalsIgnoreCase("")) {
            Toast.makeText(this, "Name cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;

    }

    protected boolean checkNewPhone() {

        if (myNumberList == null || myNumberList.size() < 1) {
            Toast.makeText(this, "Add a phone number", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;

    }

    protected class DoneOnEditorActionListener implements EditText.OnEditorActionListener {
        EditText editText;


        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            editText = (EditText) v;
            firstName =  editText.getText().toString();
            if (firstName != null && !firstName.equalsIgnoreCase("")) {


                switch (v.getId()) {

                    case R.id.etUserFirstName_AVC:
                        thisUserModel.setCompanion(firstName);
                        break;

                }

                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
            return true;

        }
    }


}

