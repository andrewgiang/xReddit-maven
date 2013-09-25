package com.andrew.giang.xreddit.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;
import com.andrew.giang.xreddit.adapter.PostListingAdapter;
import com.andrew.giang.xreddit.thing.Post;


/**
 * Created by Andrew on 9/2/13.
 */
public class SubredditListFragment extends ListFragment {

    public static final String SUBREDDIT = "subreddit";
    private PostListingAdapter mPostAdapter;
    private String mUrl;

    public static SubredditListFragment getInstance(String subreddit, String sort) {

        SubredditListFragment fragment = new SubredditListFragment();

        Bundle args = new Bundle();
        args.putString(SUBREDDIT, subreddit);
        args.putString("sort", sort);
        fragment.setArguments(args);

        return fragment;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        final Bundle args = getArguments();
        String subreddit = null;
        if (args != null) {
            subreddit = args.getString(SUBREDDIT);
        }
        getListView().setDivider(null);
        getListView().setDividerHeight(0);


        if (subreddit == null) {
            mUrl = "http://www.reddit.com/.json";
        } else {
            mUrl = "http://www.reddit.com/r/" + subreddit + "/.json";
        }
        mPostAdapter = new PostListingAdapter(getActivity(), mUrl, getFragmentManager());
        setListAdapter(mPostAdapter);

        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Post item = (Post) mPostAdapter.getItem(position);

        String url = item.url;
        Uri uri = Uri.parse(url);
        if (uri != null) {
            final Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(uri);
            startActivity(intent);
        }
        super.onListItemClick(l, v, position, id);
    }

}