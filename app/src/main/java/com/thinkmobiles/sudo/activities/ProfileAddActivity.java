package com.thinkmobiles.sudo.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.thinkmobiles.sudo.R;
import com.thinkmobiles.sudo.models.addressbook.UserModel;

/**
 * Created by omar on 23.04.15.
 */
public class ProfileAddActivity extends BaseProfileEditActivity {


    @Override
    protected int getLayoutResource() {
        return R.layout.activity_edit_profile;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        thisUserModel = new UserModel();


    }

    public static void launch(Activity activity) {


        Intent intent = new Intent(activity, ProfileAddActivity.class);

        activity.startActivityForResult(intent, START_EDIT_PROFILE_ACTIVITY_CODE);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}

