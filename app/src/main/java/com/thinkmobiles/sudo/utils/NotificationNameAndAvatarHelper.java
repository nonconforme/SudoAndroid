package com.thinkmobiles.sudo.utils;

import com.thinkmobiles.sudo.global.App;
import com.thinkmobiles.sudo.models.addressbook.NumberModel;
import com.thinkmobiles.sudo.models.addressbook.UserModel;

/**
 * Created by omar on 21.05.15.
 */
public class NotificationNameAndAvatarHelper {




    public static String[] discoverCompanionNameAndAvatar(final String number) {
        String[] result = new String[2];
        String companion;
        String avatar;

            for (UserModel userModel : App.getContactsList()) {
                for (NumberModel numberModel : userModel.getNumbers())
                    if (numberModel.getNumber().equalsIgnoreCase(number)) {
                        result[0] = userModel.getCompanion();
                        result[1] = userModel.getAvatar();
                        return result;
                    }


            result[0] = number;
            result[1] = "";
        }
        return result;

    }


}