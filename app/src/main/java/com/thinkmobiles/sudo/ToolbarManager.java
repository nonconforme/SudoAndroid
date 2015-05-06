package com.thinkmobiles.sudo;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.thinkmobiles.sudo.global.CircleTransform;


/**
 * Created by omar on 30.04.15.
 */
public class ToolbarManager {

    private static ActionBarActivity mActivity;
    public static Toolbar mToolbar;
    private static ToolbarManager sToolbarManager;
    private Target mTarget;


    public static void setmActivity(ActionBarActivity _mActivity) {
        mActivity = _mActivity;

    }

    public static ToolbarManager getInstance(Activity _actionBarActivity) {
        if (sToolbarManager == null) {
            sToolbarManager = new ToolbarManager();
            mActivity = (ActionBarActivity) _actionBarActivity;
            initToolbar();
        } else {
            mActivity = (ActionBarActivity) _actionBarActivity;
            initToolbar();
        }
        return sToolbarManager;
    }

    public static void initToolbar() {
        if (sToolbarManager != null) {
            mToolbar = (Toolbar) mActivity.findViewById(R.id.toolbar);
            mActivity.setSupportActionBar(mToolbar);
            mActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            mActivity.getSupportActionBar().setHomeButtonEnabled(true);
            mActivity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    public Toolbar getToolbar() {
        return mToolbar;
    }


    public void changeToolbarTitleAndIcon(int title, int image) {
        mToolbar.setTitle(title);
        if (image == 0) {
            mActivity.getSupportActionBar().setIcon(new ColorDrawable(mActivity.getResources().getColor(android.R.color.transparent)));
        } else {
            mActivity.getSupportActionBar().setIcon(mActivity.getResources().getDrawable(image));
        }
    }

    public void changeToolbarTitleAndIcon(int title, String image) {
        mToolbar.setTitle(title);
        if (image == null) {
            mActivity.getSupportActionBar().setIcon(new ColorDrawable(mActivity.getResources().getColor(android.R.color.transparent)));
        }
    }

    public void changeToolbarTitleAndIcon(String title, int image) {
        mToolbar.setTitle(title);
        if (image == 0) {
            mActivity.getSupportActionBar().setIcon(new ColorDrawable(mActivity.getResources().getColor(android.R.color.transparent)));
        } else {
            mActivity.getSupportActionBar().setIcon(mActivity.getResources().getDrawable(image));
        }
    }


    public void changeToolbarTitleAndIcon(String title, String imageURL) {
        mToolbar.setTitle(title);
        if (imageURL == null || imageURL.equalsIgnoreCase("")) {
            mActivity.getSupportActionBar().setIcon(new ColorDrawable(mActivity.getResources().getColor(android.R.color.transparent)));
        } else {
            setToolbarIcon(imageURL);
        }
    }

    public void changeToolbarTitle(int title) {
        mToolbar.setTitle(title);
    }

    public void changeToolbarTitle(String title) {
        mToolbar.setTitle(title);

    }

    public void setToolbarIcon(String imageURL) {

        initTarget();
        Picasso.with(mActivity).load(imageURL).transform(new CircleTransform()).into(mTarget);

    }

    private void initTarget() {

        mTarget = new Target() {
            ImageView imageView = new ImageView(mActivity);

            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                imageView.setImageBitmap(bitmap);
                Drawable image = imageView.getDrawable();
                mActivity.getSupportActionBar().setIcon(image);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {


            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }


        };
    }

}
