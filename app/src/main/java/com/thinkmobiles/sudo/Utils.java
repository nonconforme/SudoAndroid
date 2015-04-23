package com.thinkmobiles.sudo;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

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
        Picasso.with(context)
                .load(imageUrl)
                .resize(dimen, dimen)
                .into(imageView);
    }
}
