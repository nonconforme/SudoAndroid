package com.thinkmobiles.sudo.utils;

import com.thinkmobiles.sudo.global.App;
import com.thinkmobiles.sudo.models.ProfileNumberModel;
import com.thinkmobiles.sudo.models.addressbook.NumberModel;

import java.util.List;

/**
 * Created by njakawaii on 08.05.2015.
 */
public abstract class ContactManager {
    public  static String[] getNumbers(){
        String [] numbers = new String[App.getCurrentUser().getNumbers().size()];
        for ( int i = 0; i < App.getCurrentUser().getNumbers().size(); i++) {
            numbers[i] = App.getCurrentUser().getNumbers().get(i).getNumber();
        }
        return numbers;
    }
    public static boolean isMyNumber(final String _number){
        boolean res = false;
        List<NumberModel> temp = App.getCurrentUser().getNumbers();
        for ( NumberModel numberItem : temp){
            if (numberItem.getNumber().equals(_number)) {
                res = true;
                break;
            }
        }
        return res;
    }
}
