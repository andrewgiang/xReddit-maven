package com.andrew.giang.xreddit.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andrew.giang.xreddit.R;
import com.andrew.giang.xreddit.activity.ImageViewActivity;
import com.andrew.giang.xreddit.common.BetterSSB;
import com.andrew.giang.xreddit.common.Constants;
import com.andrew.giang.xreddit.common.CustomTagHandler;
import com.andrew.giang.xreddit.common.Util;
import com.andrew.giang.xreddit.network.GsonRequest;
import com.andrew.giang.xreddit.network.RequestManager;
import com.andrew.giang.xreddit.thing.Post;
import com.andrew.giang.xreddit.thing.Thing;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.laurencedawson.activetextview.ActiveTextView;

import org.apache.commons.lang3.StringEscapeUtils;

import java.util.ArrayList;
import java.util.List;

import in.uncod.android.bypass.Bypass;

/**
 * Created by andrew on 8/28/13.
 */
public class PostListingAdapter extends BaseAdapter {

    private final GsonRequest<Thing> thingGsonRequest;
    private final RequestManager requestManager;
    private final FragmentManager mFragmentManager;
    private final Context mContext;
    private final ImageLoader mImageLoader;
    private final String url;
    private List<Post> mPostList;

    public PostListingAdapter(Context context, String url, FragmentManager fragmentManager) {
        this.mFragmentManager = fragmentManager;
        this.mContext = context;
        this.url = url;
        this.mPostList = new ArrayList<Post>();
        requestManager = RequestManager.getInstance(context);
        mImageLoader = requestManager.getImageLoader();
        thingGsonRequest = getThingGsonRequest(url);

        requestManager.makeRequest(thingGsonRequest);

    }

    public void doRefresh() {
        final RequestManager instance = RequestManager.getInstance(mContext);
        mPostList.clear();
        instance.getCache().invalidate(url, true);
        instance.makeRequest(getThingGsonRequest(url));
        notifyDataSetChanged();
    }

    private GsonRequest<Thing> getThingGsonRequest(String url) {
        //TODO VALIDATE URL
        return new GsonRequest<Thing>(url, Thing.class, null, new Response.Listener<Thing>() {

            @Override
            public void onResponse(Thing thing) {

                mPostList.addAll(thing.getData(Post.class));
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

    @Override
    public int getCount() {
        return mPostList.size();
    }

    @Override
    public Object getItem(int position) {
        return mPostList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    SpannableStringBuilder getSubtitle(Post post) {
/*        int reddit_orange = Color.rgb(255, 139, 36);
        int reddit_blue = Color.rgb(148, 148, 255);//TODO constants*/
        BetterSSB ssb = new BetterSSB();
/*        ssb.append(Integer.toString(post.ups), BetterSSB.FOREGROUND_COLOR, reddit_orange, 0, 1f);
        ssb.append(" | ", 0);
        ssb.append(Integer.toString(post.downs), BetterSSB.FOREGROUND_COLOR, reddit_blue, 0, 1f);*/
        final String sep = " " + Character.toString((char) 187) + " ";
        ssb.append(sep, 0);
        ssb.append(post.author, 0);
        ssb.append(sep, 0);
        ssb.append(DateUtils.getRelativeTimeSpanString(post.created_utc * 1000L).toString(), 0);
        ssb.append(sep, 0);
        ssb.append(post.subreddit, 0);
        ssb.append(sep, 0);
        ssb.append(post.domain, 0);


        return ssb.get();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Post post = mPostList.get(position);


        convertView = LayoutInflater.from(mContext).inflate(R.layout.reddit_post, parent, false);
        ViewHolder holder = new ViewHolder();
        holder.card = (LinearLayout) convertView.findViewById(R.id.card);
        holder.title = (TextView) convertView.findViewById(R.id.post_title);
        holder.subtitle = (TextView) convertView.findViewById(R.id.post_subtitle);
        holder.networkImageView = (NetworkImageView) convertView.findViewById(R.id.thumbnail);
        holder.networkImageView.setImageDrawable(null);
        holder.comments = (TextView) convertView.findViewById(R.id.tv_comments);
        holder.points = (TextView) convertView.findViewById(R.id.tv_points);
        convertView.setTag(holder);

        holder = (ViewHolder) convertView.getTag();
        holder.title.setText(post.title);
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri uri = Uri.parse(Constants.Reddit.getUrl(post.permalink));
                if (uri != null) {
                    final Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(uri);
                    mContext.startActivity(intent);

                }
            }
        });

        if (Util.isImage(post.url)) {
            ((NetworkImageView) holder.networkImageView).setImageUrl(post.url, mImageLoader);

            holder.networkImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(mContext, ImageViewActivity.class);
                    i.putExtra("image_url", post.url);
                    mContext.startActivity(i);


                }
            });
        } else {
            if (post.is_self) {
                holder.networkImageView.setVisibility(View.GONE);
                if (post.selftext_html != null) {
                    String source;
                    if (post.selftext.length() > 200) {
                        source = post.selftext.substring(0, 200) + " ...";
                    } else {
                        source = post.selftext;
                    }

                    if (!TextUtils.isEmpty(source)) {
                        Bypass bypass = new Bypass();

                        ActiveTextView textView = new ActiveTextView(mContext);
                        textView.setLinkClickedListener(new ActiveTextView.OnLinkClickedListener() {
                            @Override
                            public void onClick(String url) {
                                if (url != null) {
                                    Intent i = new Intent(Intent.ACTION_VIEW);
                                    i.setData(Uri.parse(url));
                                    mContext.startActivity(i);
                                }
                            }
                        });
                        CharSequence spannedText;
                        try {

                            spannedText = bypass.markdownToSpannable(source);
                        } catch (NullPointerException ex) {
                            source = StringEscapeUtils.unescapeHtml4(post.selftext_html);
                            spannedText = Html.fromHtml(source, null, new CustomTagHandler());

                            Log.e("BYPASS", source);
                        }
                        textView.setText(spannedText);
                        textView.setMovementMethod(LinkMovementMethod.getInstance());
                        textView.setMinimumHeight(Util.dpToPixels(mContext, 160));
                        holder.card.addView(textView, 1);


                    }

                }
            } else {

                final View view = LayoutInflater.from(mContext).inflate(R.layout.thumbnail_view, parent, false);
                final TextView tvDomain = (TextView) view.findViewById(R.id.tv_domain);
                final TextView tvUrl = (TextView) view.findViewById(R.id.tv_url);
                final NetworkImageView thumbnailImageView = (NetworkImageView) view.findViewById(R.id.imageViewThumbnail);

                tvDomain.setText(post.domain);
                tvUrl.setText(post.url);
                thumbnailImageView.setImageUrl(Util.getThumbnail(post),
                        mImageLoader);
                holder.card.addView(view, 1);
                holder.networkImageView.setVisibility(View.GONE);

            }
        }

        holder.subtitle.setText(getSubtitle(post));
        holder.points.setText(String.valueOf(post.score) + " points");
        holder.comments.setText(String.valueOf(post.num_comments) + " comments");

        if (getCount() > 3 && position > mPostList.size() - 3) {//TODO onScrollListener?
            Post lastPost = mPostList.get(mPostList.size() - 1);
            String url = this.url + "?after=" + lastPost.name;
            GsonRequest<Thing> request = getThingGsonRequest(url);
            requestManager.makeRequest(request);
        }
        return convertView;

    }


    static class ViewHolder {
        LinearLayout card;
        TextView title;
        TextView subtitle;
        TextView points;
        TextView comments;
        ImageView networkImageView;

    }

}
