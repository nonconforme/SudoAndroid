package com.thinkmobiles.sudo.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.*;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import com.squareup.picasso.Picasso;
import com.thinkmobiles.sudo.R;
import com.thinkmobiles.sudo.core.APIConstants;
import com.thinkmobiles.sudo.global.ProgressDialogWorker;
import com.thinkmobiles.sudo.utils.ImageHelper;
import com.thinkmobiles.sudo.utils.Utils;
import com.thinkmobiles.sudo.adapters.ProfileEditNumbersAdapter;
import com.thinkmobiles.sudo.custom_views.NonScrollListView;
import com.thinkmobiles.sudo.models.addressbook.NumberModel;
import com.thinkmobiles.sudo.models.addressbook.UserModel;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by omar on 23.04.15.
 */
abstract public class BaseProfileEditActivity extends BaseProfileActivity implements View.OnClickListener {

    private EditText etUserFirstName;
    protected ImageView ivChangeAvatar;
    private Button btnChangeAvatar ;
    private NonScrollListView lvNumbers;
    private ProfileEditNumbersAdapter mAdapter;
    protected View rootView;

    private Bitmap mCurrentPhoto;


    protected UserModel mUserModel;
    private String firstName, urlAvatar, filepathAvatar;
    private List<NumberModel> myNumberList;
    private DoneOnEditorActionListener doneOnEditorActionListener;


    public static final String EXTRA_IMAGE = "DetailActivity:image";
    private static final int SELECT_PHOTO = 1;
    public static final int START_EDIT_PROFILE_ACTIVITY_CODE = 231;

    @Override
    protected int getLayoutResource() {

        return R.layout.activity_edit_profile;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView  = getLayoutInflater().inflate(getLayoutResource(), null);
        setContentView(rootView);

        initComponent();
        setOnClickListener();

    }

    private void setOnClickListener() {
        btnChangeAvatar.setOnClickListener(this);

    }


    protected void setContent() {
        if (Utils.checkString(firstName)) {
            etUserFirstName.setText(firstName);
        }

        if (mUserModel.getColor() != null) {
            setStatusBarColor(mUserModel.getColor().getDarkColor());
            String  color = "#" + Integer.toHexString( mUserModel.getColor().getMainColor()).substring(2);
            getToolbar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(color)));
        }
        if (Utils.checkList(myNumberList)) {
            mAdapter.reloadList(myNumberList);

        }
        if (mUserModel.getAvatar() != null && !mUserModel.getAvatar().isEmpty())
            Picasso.with(this).load(APIConstants.SERVER_URL + "/" + mUserModel.getAvatar()).into(ivChangeAvatar);
    }

    protected void loadContent() {
        firstName = mUserModel.getCompanion();
        urlAvatar = mUserModel.getAvatar();
        myNumberList = mUserModel.getNumbers();
    }

    protected void initComponent() {
        etUserFirstName = (EditText) findViewById(R.id.etUserFirstName_AVC);

        lvNumbers = (NonScrollListView) findViewById(R.id.lvPhoneNumbersView_AVC);
        ivChangeAvatar = (ImageView) findViewById(R.id.ivChangeAvatarIcon_AVC);
        btnChangeAvatar = (Button) findViewById(R.id.btnChangeAvatar_AVC);


        doneOnEditorActionListener = new DoneOnEditorActionListener();
        etUserFirstName.setOnEditorActionListener(doneOnEditorActionListener);

        mAdapter = new ProfileEditNumbersAdapter(this);
        lvNumbers.setDivider(null);
        lvNumbers.setDividerHeight(0);
        lvNumbers.setAdapter(mAdapter);

        View footerView = ((LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footer_add_phone_number_item, null, false);
        footerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAdapter.addBlankNumberView();
            }
        });
        lvNumbers.addFooterView(footerView);
    }

    protected void loadUserModel() {
        mUserModel = (UserModel) getIntent().getExtras().getBundle(BaseProfileActivity.USER_MODEL).getSerializable(BaseProfileActivity.USER_MODEL);
        Log.d(TAG, mUserModel.getCompanion());
    }

    protected void setDefaultColor() {
        setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        getToolbar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
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


                if (isProfileChangesValid()) {
                    updateUserModel();
                    returnEditedProfile();
                }

                break;

        }
        return super.onOptionsItemSelected(item);
    }

    protected boolean isProfileChangesValid() {
        boolean[] errorsInNumbers = validateNumbers();
        if (errorsInNumbers == null && checkNewName()) return true;
        else {
            showNumberErrors(errorsInNumbers);
            return false;
        }
    }

    private void showNumberErrors(boolean[] errorsInNumbers) {
        mAdapter.showErrorsInNumbers(errorsInNumbers);

    }

    private boolean[] validateNumbers() {
        List<NumberModel> tempNumbers = mUserModel.getNumbers();
        boolean showNumbersError = false;
        boolean[] errors = new boolean[tempNumbers.size()];
        if (tempNumbers.size() < 1) {
            Toast.makeText(this, "Phone numbers cannot be empty", Toast.LENGTH_SHORT);
        }
        for (int i = 0; i < tempNumbers.size(); i++) {
            CharSequence tempNumber = tempNumbers.get(i).getNumber();
            if (tempNumber == null || tempNumber.length() < 11 || tempNumber.charAt(0) != '+') {
                showNumbersError = true;
                errors[i] = true;
            } else {
                errors[i] = false;
            }

        }

        if (showNumbersError) {
            Toast.makeText(this, "Phone numbers contain errors", Toast.LENGTH_SHORT).show();
            return errors;
        }
        return null;
    }


    protected void reLoadAvatar() {
        loadAvatarFromGallery();

    }

    protected void updateNumberList() {
        myNumberList = mAdapter.getNumbersList();
        mUserModel.setNumbers(myNumberList);
    }

    protected void updateUserModel() {
        updateNumberList();
        if (mCurrentPhoto == null) mCurrentPhoto = ((BitmapDrawable) ivChangeAvatar.getDrawable()).getBitmap();
        mUserModel.setAvatar(ImageHelper.encodeToBase64(mCurrentPhoto));
        firstName = etUserFirstName.getText().toString();
        mUserModel.setCompanion(firstName);
    }


    protected void loadAvatarFromGallery() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, SELECT_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("mediastore result ", String.valueOf(resultCode));
        if (resultCode != -1) return;
        if (requestCode == SELECT_PHOTO) {
            Uri avatarUri = data.getData();
            loadImage(avatarUri);

        }

    }

    private void loadImage(final Uri _avatarUri){
        if (_avatarUri != null) {
            try {
                final InputStream imageStream = getContentResolver().openInputStream(_avatarUri);
                Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                mCurrentPhoto = Bitmap.createScaledBitmap(selectedImage, 500, 500, true);
                ivChangeAvatar.setImageBitmap(selectedImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    abstract protected void returnEditedProfile();

    protected boolean checkNewName() {

        if (firstName == null || firstName.equalsIgnoreCase("")) {
            Toast.makeText(this, "Name cannot be empty", Toast.LENGTH_SHORT).show();
            etUserFirstName.setBackgroundResource(android.R.color.holo_red_dark);
            return false;
        } else {
            etUserFirstName.setBackgroundResource(android.R.color.transparent);

            return true;
        }
    }


    protected class DoneOnEditorActionListener implements EditText.OnEditorActionListener {
        EditText editText;


        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            editText = (EditText) v;
            firstName = editText.getText().toString();
            if (firstName != null && !firstName.equalsIgnoreCase("")) {

                switch (v.getId()) {

                    case R.id.etUserFirstName_AVC:
                        mUserModel.setCompanion(firstName);
                        break;
                }

                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
            return true;
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnChangeAvatar_AVC) reLoadAvatar();

    }

    protected void addNewNumber() {
        myNumberList = mAdapter.getNumbersList();
        myNumberList.add(myNumberList.size(),new NumberModel());
        mAdapter.reloadList(myNumberList);
    }


   protected void setNullAnim(){

    }
}

