package com.andrew.giang.xreddit.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.andrew.giang.xreddit.network.RequestManager;
import com.andrew.giang.xreddit.thing.Thing;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by Andrew on 9/14/13.
 */
public class CommentListingAdapter extends BaseAdapter {
    private final Context mContext;
    private RequestManager requestManager;

    CommentListingAdapter(Context context) {
        mContext = context;
        requestManager = RequestManager.getInstance(mContext);
        JsonArrayRequest comments = new JsonArrayRequest("http://www.reddit.com/r/AdviceAnimals/comments/1lw0bi/suspicious_dog_is_getting_suspicious/.json", new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray thing) {
                try {
                    final String data = thing.getString(0);
                    Gson gson = new Gson();
                    final Thing thing1 = gson.fromJson(data, Thing.class);
                    final String comments = thing.getString(1);

                    final Thing thing2 = gson.fromJson(comments, Thing.class);
                    Log.w("Comments-data", thing1.toString());
                    Log.w("Comments-comments", thing2.toString());
                } catch (JSONException e) {
                    Log.e("COMMENT", e.getMessage());
                }
                Log.w("Comments", thing.toString());

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.w(PostListingAdapter.class.getName(), volleyError.getMessage());
            }
        }
        );
        requestManager.makeRequest(comments);
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
