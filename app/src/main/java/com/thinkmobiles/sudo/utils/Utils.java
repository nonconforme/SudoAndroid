package com.thinkmobiles.sudo.utils;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.thinkmobiles.sudo.core.APIConstants;

import java.util.List;

/**
 * Created by omar on 23.04.15.
 */
public class Utils {
    public static boolean checkString(String s) {
        if (s != null && !s.equalsIgnoreCase(""))
            return true;
        return false;
    }
    public static boolean checkList(List list) {
        if (list != null && list.size() >0)
            return true;
        return false;
    }

    public static void setImagePicasso(Context context, String imageUrl, int dimen, ImageView imageView){
        Picasso.with(context).load(APIConstants.SERVER_URL + "/" + imageUrl)
                .resize(dimen, dimen)
                .into(imageView);
    }
}
