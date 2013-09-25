package com.andrew.giang.xreddit.thing;

import com.google.gson.JsonObject;

/**
 * Created by andrew on 8/28/13.
 */
public class Comment {
    String subreddit, id, subreddit_id, author, parent_id, body, name, link_id;
    int gilded, downs, ups;
    long created, created_utc;
    boolean edited, score_hidden;
    JsonObject replies;
}
