package com.thinkmobiles.sudo.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.text.format.DateFormat;
import android.view.Display;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import com.thinkmobiles.sudo.global.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by omar on 23.04.15.
 */
public class Utils {


    private static int screenWidth = 0;
    private static int screenHeight = 0;

    public static boolean checkString(String s) {
        if (s != null && !s.equalsIgnoreCase("")) return true;
        return false;
    }

    public static boolean isAndroid5() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    public static boolean checkList(List list) {
        if (list != null && list.size() > 0) return true;
        return false;
    }

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int getScreenHeight(Context c) {
        if (screenHeight == 0) {
            WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            screenHeight = size.y;
        }
        return screenHeight;
    }

    public static String stringToDate(String aDate) {
        Date date = null;
        SimpleDateFormat format = new SimpleDateFormat(Constants.SERVER_DATE_FORMAT);
        try {
            date = format.parse(aDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date == null) return null;
        return DateFormat.format(Constants.CLIENT_DATE_FORMAT, date).toString();
    }


    public static boolean stringContains(String source, String toCheck) {
        return source.toLowerCase().contains(toCheck.toLowerCase()) || source.toUpperCase().contains(toCheck.toUpperCase());
    }

    public static String getDateServerStyle() {
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.SERVER_DATE_FORMAT);
        return sdf.format(new Date());
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
