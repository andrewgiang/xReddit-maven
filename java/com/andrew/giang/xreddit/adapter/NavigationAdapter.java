package com.andrew.giang.xreddit.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.andrew.giang.xreddit.NavigationItem;
import com.andrew.giang.xreddit.R;
import com.andrew.giang.xreddit.common.Util;
import com.andrew.giang.xreddit.network.GsonRequest;
import com.andrew.giang.xreddit.network.RequestManager;
import com.andrew.giang.xreddit.thing.Subreddit;
import com.andrew.giang.xreddit.thing.Thing;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrew on 8/31/13.
 */
public class NavigationAdapter extends BaseAdapter {
    private final Context mContext;
    private List<NavigationItem> items = new ArrayList<NavigationItem>();

    public NavigationAdapter(final Context context) {
        this.mContext = context;
        addDefaultItems(context);
        final RequestManager instance = RequestManager.getInstance(context);

        String url = "http://www.reddit.com/subreddits/.json";

        instance.makeRequest(getThingGsonRequest(url));
        notifyDataSetChanged();
    }

    private void addDefaultItems(Context context) {
        items.add(new NavigationItem("My Account"));
        items.add(new NavigationItem("Front Page", NavigationItem.ACTION.FRONTPAGE,
                Util.getDrawable(context, R.drawable.ic_action_bookmark)));
        items.add(new NavigationItem("Sign in", NavigationItem.ACTION.LOGIN,
                Util.getDrawable(context, R.drawable.ic_action_key)));
        items.add(new NavigationItem("Casual Subreddit", NavigationItem.ACTION.CASUAL,
                Util.getDrawable(context, R.drawable.ic_action_casual)));
        items.add(new NavigationItem("Subscriptions"));
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    /**
     * Overriden method to disable headers
     *
     * @param position
     * @return
     */
    @Override
    public boolean isEnabled(int position) {
        return !items.get(position).mAction.equals(NavigationItem.ACTION.HEADER);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final NavigationItem navigationItem = items.get(position);
        if (navigationItem.mAction.equals(NavigationItem.ACTION.HEADER)) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.drawer_list_header, parent, false);
            final TextView header = (TextView) convertView.findViewById(R.id.navigation_header);

            header.setText(navigationItem.title);

        } else {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.drawer_list_item, parent, false);
            final ImageView drawerIcon = (ImageView) convertView.findViewById(R.id.drawer_icon);
            if (navigationItem.icon != null) {
                drawerIcon.setVisibility(View.VISIBLE);
                drawerIcon.setImageDrawable(navigationItem.icon);
            }

            final TextView drawerTitle = (TextView) convertView.findViewById(R.id.drawer_title);
            drawerTitle.setText(navigationItem.title);
        }

        return convertView;
    }

    private GsonRequest<Thing> getThingGsonRequest(String url) {
        //TODO VALIDATE URL
        return new GsonRequest<Thing>(url, Thing.class, null, new Response.Listener<Thing>() {

            @Override
            public void onResponse(Thing thing) {

                final List<Subreddit> data = thing.getData(Subreddit.class);
                for (Subreddit subreddit : data) {

                    items.add(new NavigationItem(subreddit.display_name, NavigationItem.ACTION.SUBREDDIT, null));
                }
                notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.w(PostListingAdapter.class.getName(), volleyError.getMessage());
            }
        }
        );
    }
}
