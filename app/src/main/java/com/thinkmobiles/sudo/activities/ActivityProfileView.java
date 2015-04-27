package com.thinkmobiles.sudo.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.thinkmobiles.sudo.R;
import com.thinkmobiles.sudo.Utils;
import com.thinkmobiles.sudo.adapters.ProfileViewNumbersAdapter;
import com.thinkmobiles.sudo.custom_views.NonScrollListView;
import com.thinkmobiles.sudo.models.addressbook.NumberModel;
import com.thinkmobiles.sudo.models.addressbook.UserModel;

import java.util.List;

/**
 * Created by omar on 23.04.15.
 */
public class ActivityProfileView extends BaseProfileActivity {

    private TextView tvUserFirstName;
    private ImageView ivAvatar;
    private NonScrollListView lvNumbers;
    private ProfileViewNumbersAdapter profileViewNumbersAdapter;

    private UserModel thisUserModel;
    private String firstName, urlAvatar;
    private List<NumberModel> myNumberList;

    public static final String EXTRA_IMAGE = "DetailActivity:image";

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_view_profile;
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

            tvUserFirstName.setText(firstName);
        }

   /*     if (Utils.checkString(lastName)) {
            tvUserLastName.setText(firstName);
        }
*/

        if (Utils.checkList(myNumberList)) {
            profileViewNumbersAdapter = new ProfileViewNumbersAdapter(this, myNumberList);
            lvNumbers.setAdapter(profileViewNumbersAdapter);
        }

    }

    private void loadContent() {
        firstName = thisUserModel.getCompanion();
        urlAvatar = thisUserModel.getAvatar();
        myNumberList = thisUserModel.getNumbers();

    }


    private void initComponent() {

        tvUserFirstName = (TextView) findViewById(R.id.tvUserFirstName_AVC);
/*
        tvUserLastName = (TextView) findViewById(R.id.tvUserSecondName_AVC);
*/

        lvNumbers = (NonScrollListView) findViewById(R.id.lvPhoneNumbersView_AVC);

    }


    private void loadUserModel() {
        thisUserModel = (UserModel) getIntent().getExtras().getBundle(BaseProfileActivity.USER_MODEL).getSerializable(BaseProfileActivity.USER_MODEL);
        Log.d(TAG, thisUserModel.getCompanion());
    }


    public static void launch(Activity activity, View transitionView, UserModel userModel) {


        ActivityOptionsCompat options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                        activity, transitionView, EXTRA_IMAGE);

        Intent intent = new Intent(activity, ActivityProfileView.class);
        intent.putExtra(EXTRA_IMAGE, "https://unseenflirtspoetry.files.wordpress.com/2012/05/homer-excited.png");

        Bundle b = new Bundle();
        b.putSerializable(BaseProfileActivity.USER_MODEL, userModel);
        intent.putExtra(BaseProfileActivity.USER_MODEL, b);


        ActivityCompat.startActivity(activity, intent, options.toBundle());
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

        if (id == R.id.action_edit) {
            ActivityProfileEdit.launch(this,null,thisUserModel);

            return true;

        }
        return super.onOptionsItemSelected(item);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(" activity result",String.valueOf(resultCode));
        if (resultCode != -1) return;
        if (requestCode == ActivityProfileEdit.START_EDIT_PROFILE_ACTIVITY_CODE){
            reloadUserModel(data);
            loadContent();
            reloadAvatar();
            setContent();

        }

    }
    public void reloadUserModel(Intent intent){
        thisUserModel =      (UserModel) intent
                .getExtras()
                .getBundle(BaseProfileActivity.USER_MODEL)
                .getSerializable(BaseProfileActivity.USER_MODEL);

    }

    public void reloadAvatar(){
        Picasso.with(this).load(urlAvatar).into(ivAvatar);
    }
}
