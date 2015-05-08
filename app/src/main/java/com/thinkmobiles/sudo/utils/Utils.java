package com.thinkmobiles.sudo.utils;

import android.content.Context;
import android.text.format.DateFormat;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.thinkmobiles.sudo.R;
import com.thinkmobiles.sudo.core.APIConstants;
import com.thinkmobiles.sudo.global.CircleTransform;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by omar on 23.04.15.
 */
public class Utils {

    public static final String SERVER_DATE_FORMAT = "yyyy-MM-dd'T'hh:mm:ss.SSS'Z'";
    public static final String CLIENT_DATE_FORMAT = "MMM-dd, hh:mm";

    public static boolean checkString(String s) {
        if (s != null && !s.equalsIgnoreCase("")) return true;
        return false;
    }

    public static boolean checkList(List list) {
        if (list != null && list.size() > 0) return true;
        return false;
    }


    public static String stringToDate(String aDate) {

        Date date = null;

        SimpleDateFormat format = new SimpleDateFormat(SERVER_DATE_FORMAT);

        try {
            date = format.parse(aDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date == null) return null;

        return DateFormat.format(CLIENT_DATE_FORMAT, date).toString();

    }

    public static void setAvatar(final Context context, final ImageView imageView, String imageUrl, final int pos) {
        if (imageUrl != null && !imageUrl.equalsIgnoreCase("")) {
            Picasso.with(context).load(APIConstants.SERVER_URL + "/" + imageUrl).transform(new CircleTransform()).into(imageView, new Callback() {
                @Override
                public void onSuccess() {
                    if ((int) imageView.getTag() != pos) {
                        Picasso.with(context).load(R.drawable.ic_launcher).transform(new CircleTransform()).into(imageView);
                    }
                }

                @Override
                public void onError() {
                    Picasso.with(context).load(R.drawable.ic_launcher).transform(new CircleTransform()).into(imageView);

                }
            });


        } else {
            Picasso.with(context).load(R.drawable.ic_launcher).transform(new CircleTransform()).into(imageView);

        }

    }

    public static String getDateServerStyle() {
        SimpleDateFormat sdf = new SimpleDateFormat(Utils.SERVER_DATE_FORMAT);
        return sdf.format(new Date());
    }
}
