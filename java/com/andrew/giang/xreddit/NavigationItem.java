package com.andrew.giang.xreddit;

import android.graphics.drawable.Drawable;

/**
 * Created by Andrew on 8/31/13.
 */
public class NavigationItem {
    public static enum ACTION {
        FRONTPAGE, ALL, PROFILE, LOGIN, SUBREDDIT, MULTI_REDDIT, HEADER, CASUAL
    }


    public final String title;
    public final ACTION mAction;
    public final Drawable icon;


    public NavigationItem(String title, ACTION action, Drawable icon) {
        this.title = title;
        this.mAction = action;
        this.icon = icon;
    }

    public NavigationItem(String title) {
        this.title = title;
        this.mAction = ACTION.HEADER;
        this.icon = null;
    }
}
