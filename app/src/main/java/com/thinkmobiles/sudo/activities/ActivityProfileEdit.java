package com.thinkmobiles.sudo.activities;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.thinkmobiles.sudo.R;
import com.thinkmobiles.sudo.Utils;
import com.thinkmobiles.sudo.adapters.ProfileEditNumbersAdapter;
import com.thinkmobiles.sudo.custom_views.NonScrollListView;
import com.thinkmobiles.sudo.models.addressbook.NumberModel;
import com.thinkmobiles.sudo.models.addressbook.UserModel;

import java.util.List;

/**
 * Created by omar on 23.04.15.
 */
public class ActivityProfileEdit extends BaseProfileActivity {

    private EditText etUserFirstName, etUserLastName;
    private ImageView ivAvatar, ivChangeAvatar;
    private Button btnChangeAvatar;
    private NonScrollListView lvNumbers;
    private ProfileEditNumbersAdapter profileEditNumbersAdapter;

    private View.OnClickListener mOnClickListener;

    private UserModel thisUserModel;
    private String firstName, lastName, urlAvatar, filepathAvatar;
    private List<NumberModel> myNumberList;


    public static final String EXTRA_IMAGE = "DetailActivity:image";
    private static final int REQUEST_CODE = 1;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_edit_profile;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initAvatar();
        loadUserModel();
        initComponent();
        loadContent();
        setContent();
        defineOnClickListener();
        setOnClickListener();

    }

    private void setContent() {
        if (Utils.checkString(firstName)) {
            etUserFirstName.setText(firstName);
        }

        if (Utils.checkString(lastName)) {
            etUserLastName.setText(firstName);
        }
        if (Utils.checkList(myNumberList)) {
            profileEditNumbersAdapter = new ProfileEditNumbersAdapter(this, myNumberList);
            lvNumbers.setAdapter(profileEditNumbersAdapter);
        }
        Picasso.with(this).load(getIntent().getStringExtra(EXTRA_IMAGE)).into(ivChangeAvatar);
    }

    private void loadContent() {
        firstName = thisUserModel.getCompanion();
        urlAvatar = thisUserModel.getAvatar();
        myNumberList = thisUserModel.getNumbers();
    }

    private void initComponent() {
        etUserFirstName = (EditText) findViewById(R.id.etUserFirstName_AVC);
        etUserLastName = (EditText) findViewById(R.id.etUserSecondName_AVC);
        lvNumbers = (NonScrollListView) findViewById(R.id.lvPhoneNumbersView_AVC);
        ivChangeAvatar = (ImageView) findViewById(R.id.ivChangeAvatarIcon_AVC);
        btnChangeAvatar = (Button) findViewById(R.id.btnChangeAvatar_AVC);

    }

    private void loadUserModel() {
        thisUserModel = (UserModel) getIntent().getExtras().getBundle(BaseProfileActivity.USER_MODEL).getSerializable(BaseProfileActivity.USER_MODEL);
        Log.d(TAG, thisUserModel.getCompanion());
    }

    public static void launch(Activity activity, View transitionView, UserModel userModel) {
        /*ActivityOptionsCompat options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                        activity, transitionView, EXTRA_IMAGE);*/

        Intent intent = new Intent(activity, ActivityProfileEdit.class);
        intent.putExtra(EXTRA_IMAGE, "https://unseenflirtspoetry.files.wordpress.com/2012/05/homer-excited.png");

        Bundle b = new Bundle();
        b.putSerializable(BaseProfileActivity.USER_MODEL, userModel);
        intent.putExtra(BaseProfileActivity.USER_MODEL, b);


/*        ActivityCompat.startActivity(activity, intent, options.toBundle());*/
        activity.startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_view_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        if (id == R.id.action_accept) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setOnClickListener() {
        btnChangeAvatar.setOnClickListener(mOnClickListener);
    }

    private void defineOnClickListener() {
        mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.btnChangeAvatar_AVC) reLoadAvatar();
            }
        };
    }

    private void initAvatar() {
        ivAvatar = (ImageView) findViewById(R.id.image);
        ViewCompat.setTransitionName(ivAvatar, EXTRA_IMAGE);
        Picasso.with(this).load(getIntent().getStringExtra(EXTRA_IMAGE)).into(ivAvatar);
    }

    private void reLoadAvatar() {
        loadAvatarFromGallery();

    }

    private void updateNumberList() {
        myNumberList = profileEditNumbersAdapter.getNumbersList();
    }

    private void updateUserModel() {
        updateNumberList();
        thisUserModel.setNumbers(myNumberList);
        firstName = etUserFirstName.getText().toString();
        thisUserModel.setCompanion(firstName);
        thisUserModel.setAvatar(urlAvatar);
    }

    private void sendUpdateAvatarToServer() {
        urlAvatar = "new url from server";
    }

    private void sendUpdateUserModelToServer() {
        updateUserModel();

    }



    public void loadAvatarFromGallery() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, REQUEST_CODE);
    }

    public void setAvatarFromGallery(Uri uri) {

        LoadAvatarFromUri loadAvatarFromUri = new LoadAvatarFromUri(new ImageView[]{ivAvatar, ivChangeAvatar});
        loadAvatarFromUri.execute(uri);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1 ||resultCode == 0) return;

        if (requestCode == REQUEST_CODE) {

            Uri avatarUri = data.getData();
            if (avatarUri != null) setAvatarFromGallery(avatarUri);
        }
    }

    private class LoadAvatarFromUri extends AsyncTask<Uri, Void, String> {
        private ImageView[] targetArray;

        public LoadAvatarFromUri(ImageView[] targetArray) {
            this.targetArray = targetArray;
        }

        @Override
        protected String doInBackground(Uri... params) {
            String path = getFilePathFromUri(params[0]);
            return path;
        }

        @Override
        protected void onPostExecute(String result) {
            filepathAvatar = result;
            for (ImageView target : targetArray) {
                Picasso.with(ActivityProfileEdit.this)
                        .load(result)
                        .resize(target.getWidth(), target.getHeight())
                        .into(target);
            }
            sendUpdateAvatarToServer();
        }

        private String getFilePathFromUri(Uri uri) {
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = ActivityProfileEdit.this.getContentResolver().query(uri,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String pathFromUri = cursor.getString(columnIndex);
            cursor.close();
            return pathFromUri;
        }
    }
}

