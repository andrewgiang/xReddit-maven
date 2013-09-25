package com.andrew.giang.xreddit.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import com.andrew.giang.xreddit.R;
import com.andrew.giang.xreddit.adapter.PostListingAdapter;
import com.andrew.giang.xreddit.thing.Post;


/**
 * Created by Andrew on 9/2/13.
 */
public class SubredditListFragment extends Fragment {

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_post_listing, container, false);
        final Bundle args = getArguments();
        String subreddit = null;
        if (args != null) {
            subreddit = args.getString(SUBREDDIT);
        }


        if (subreddit == null) {
            mUrl = "http://www.reddit.com/.json";
        } else {
            mUrl = "http://www.reddit.com/r/" + subreddit + "/.json";
        }

        GridView gridview = (GridView) root.findViewById(R.id.gridview);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Toast.makeText(getActivity(), "" + position, Toast.LENGTH_SHORT).show();
                Post item = (Post) mPostAdapter.getItem(position);

                String url = item.url;
                Uri uri = Uri.parse(url);
                if (uri != null) {
                    final Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(uri);
                    startActivity(intent);
                }
            }
        });
        mPostAdapter = new PostListingAdapter(getActivity(), mUrl, getFragmentManager());
        gridview.setAdapter(mPostAdapter);


        return root;
    }


}