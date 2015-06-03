package com.thinkmobiles.sudo.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import com.thinkmobiles.sudo.R;


/**
 * Created by Виталий on 09/10/2014.
 */
public abstract class FragmentReplacer {



    public static final void replaceCurrentFragment(final FragmentActivity _activity,
                                                    final Fragment _fragment) {
        _activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, _fragment).addToBackStack(_fragment.getClass().getName()).commit();

    }


}
