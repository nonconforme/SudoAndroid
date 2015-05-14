package com.thinkmobiles.sudo.utils;

import android.content.Context;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import com.thinkmobiles.sudo.R;
import com.thinkmobiles.sudo.global.CircleTransform;
import static com.thinkmobiles.sudo.global.Constants.*;
/**
 * Created by omar on 14.05.15.
 */
public class CountryHelper {


    static public void setCountryByIso(Context context, ImageView imageView, String iso) {


        setCountry(context, imageView, getCountryByISO(iso));

    }

    static public void setCountryByIso(Context context, ImageView imageView, String iso, int dimen) {


        setCountry(context, imageView, getCountryByISO(iso), dimen);

    }


    private static int getCountryByISO(String iso) {
        switch (iso) {
            case UNITED_STATES_ISO:
                return R.drawable.ic_us_unitedstates;

            case UNITED_KINGDOM_ISO:
                return  R.drawable.ic_uk_unitedkingdom;

            case CANADA_ISO:
                return  R.drawable.ic_ca_canada;

            case SWEDEN_ISO:
                return R.drawable.ic_se_sweden;

            case PAKISTAN_ISO:
                return R.drawable.ic_ua_ukraine;

            case AUSTRALIA_ISO:
                return R.drawable.ic_au_australia;

            default:
                return 0;
        }

    }


    private static void setCountry(Context context, final ImageView imageView, int id) {
        if (id == 0) {
            Picasso.with(context).load(R.drawable.ic_launcher).transform(new CircleTransform()).into(imageView);

        } else {
            Picasso.with(context).load(id).transform(new CircleTransform()).into(imageView);

        }

    }
    private static void setCountry(Context context, final ImageView imageView,  int id, int dimen) {
        if (id == 0) {
            Picasso.with(context).load(R.drawable.ic_launcher).resize(dimen,dimen).transform(new CircleTransform())
                    .into(imageView);

        } else {
            Picasso.with(context).load(id).resize(dimen,dimen).transform(new CircleTransform()).into(imageView);

        }

    }

}
