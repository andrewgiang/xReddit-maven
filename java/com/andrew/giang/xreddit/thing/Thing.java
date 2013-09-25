package com.andrew.giang.xreddit.thing;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by andrew on 8/28/13.
 */
public class Thing {
    @Override
    public String toString() {
        return "Thing{" +
                "kind='" + kind + '\'' +
                ", data=" + data +
                '}';
    }

    public static enum Kind {
        POST(Post.class),
        USER(User.class),
        COMMENT(Comment.class),
        MESSAGE(Message.class),
        SUBREDDIT(Subreddit.class),
        MORE_COMMENTS(null),
        LISTING(null);
        private final Class clazz;

        Kind(Class o) {
            this.clazz = o;
        }

        public Class getClazz() {
            return clazz;
        }
    }

    private static final Hashtable<String, Kind> kinds;

    static {
        kinds = new Hashtable<String, Kind>();
        kinds.put("t1", Kind.COMMENT);
        kinds.put("t2", Kind.USER);
        kinds.put("t3", Kind.POST);
        kinds.put("t4", Kind.MESSAGE);
        kinds.put("t5", Kind.SUBREDDIT);
        kinds.put("more", Kind.MORE_COMMENTS);
        kinds.put("Listing", Kind.LISTING);
    }

    public String kind;

    public JsonObject data;

    public Kind getKind() {
        return kinds.get(kind);
    }

    public Kind getKind(String kind) {
        return kinds.get(kind);
    }

    public <T> List<T> getData(Class<T> type) {
        List<T> dataList = null;
        if (getKind().equals(Kind.LISTING)) {
            if (data != null) {
                JsonArray children = data.getAsJsonArray("children");
                if (children != null) {
                    dataList = new ArrayList<T>();
                    for (JsonElement child : children) {
                        JsonObject thing = child.getAsJsonObject();
                        String kind = thing.get("kind").getAsString();
                        if (getKind(kind).getClazz().equals(type)) {
                            JsonElement jsonElement = thing.get("data");
                            Gson gson = new Gson();
                            T data = gson.fromJson(jsonElement, type);
                            dataList.add(data);
                        }

                    }
                }

            }
        }
        return dataList;
    }
}
