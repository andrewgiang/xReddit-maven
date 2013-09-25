package com.andrew.giang.xreddit.common;

import android.content.Context;
import android.graphics.drawable.Drawable;

import java.util.regex.Pattern;

/**
 * Created by Andrew on 9/1/13.
 */
public class Util {
    public static Drawable getDrawable(Context context, int resource) {
        return context.getResources().getDrawable(resource);
    }


    private static final Pattern imagePattern = Pattern.compile("([^\\s]+(\\.(?i)(jpg|png|gif|bmp))$)");

    public static boolean isImage(String url) {
        return imagePattern.matcher(url).matches();
    }


}
