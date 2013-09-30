package com.andrew.giang.xreddit.common;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;

import com.andrew.giang.xreddit.thing.Post;

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


    /*
            part of this method was taken from RedReader
         */
    public static String getThumbnail(final Post post) {
        boolean hasThumbnail = post.thumbnail != null
                && post.thumbnail.length() != 0
                && !post.thumbnail.equalsIgnoreCase("nsfw")
                && !post.thumbnail.equalsIgnoreCase("self")
                && !post.thumbnail.equalsIgnoreCase("default");
        if (hasThumbnail) {
            return post.thumbnail;
        }
        return "http://i.imgur.com/pm6IKFb.png";
    }

    public static int dpToPixels(final Context context, final float dp) {
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics()));
    }
}
