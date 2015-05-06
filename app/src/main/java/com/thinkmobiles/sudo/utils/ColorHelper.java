package com.thinkmobiles.sudo.utils;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.widget.EditText;

/**
 * Created by njakawaii on 06.05.2015.
 */
public abstract class ColorHelper {
    public static void changeEditTextUnderlineColor(EditText editText) {
        int color = Color.parseColor("#ff0d7e40");
        Drawable drawable = editText.getBackground();
        drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        editText.setBackground(drawable);
    }
}
