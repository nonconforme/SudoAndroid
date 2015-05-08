package com.thinkmobiles.sudo.utils;

import com.thinkmobiles.sudo.global.App;
import com.thinkmobiles.sudo.models.ProfileNumberModel;

import java.util.List;

/**
 * Created by njakawaii on 08.05.2015.
 */
public abstract class ContactManager {
    public static boolean isMyNumber(final String _number){
        boolean res = false;
        List<ProfileNumberModel> temp = App.getCurrentUser().getNumbers();
        for ( ProfileNumberModel numberItem : temp){
            if (numberItem.getNumber().equals(_number)) {
                res = true;
                break;
            }
        }
        return res;
    }
}
