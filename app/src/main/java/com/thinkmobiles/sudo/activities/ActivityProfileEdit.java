package com.thinkmobiles.sudo.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
    private ImageView ivAvatar;
    private NonScrollListView lvNumbers;
    private ProfileEditNumbersAdapter profileEditNumbersAdapter;

    private UserModel thisUserModel;
    private String firstName, lastName, urlAvatar;
    private List<NumberModel> myNumberList;


    public static final String EXTRA_IMAGE = "DetailActivity:image";

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_edit_profile;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ivAvatar = (ImageView) findViewById(R.id.image);
        ViewCompat.setTransitionName(ivAvatar, EXTRA_IMAGE);
        Picasso.with(this).load(getIntent().getStringExtra(EXTRA_IMAGE)).into(ivAvatar);
        loadUserModel();

        initComponent();

        loadContent();
        setContent();

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
        activity. startActivity(intent);

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

    private void addBlancListItem(){

    }
}

