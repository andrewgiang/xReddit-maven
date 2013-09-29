package com.andrew.giang.xreddit.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andrew.giang.xreddit.R;
import com.andrew.giang.xreddit.common.Util;
import com.andrew.giang.xreddit.network.RequestManager;
import com.andrew.giang.xreddit.thing.Post;
import com.android.volley.toolbox.NetworkImageView;

/**
 * Created by Andrew on 9/29/13.
 */
public class ThumbnailView extends LinearLayout {
    public ThumbnailView(Context context, final Post post) {
        super(context);
        setOrientation(HORIZONTAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(0, Util.dpToPx(5, context), 0, 0);
        this.setLayoutParams(layoutParams);
        NetworkImageView thumbnail = new NetworkImageView(context);
        thumbnail.setErrorImageResId(R.drawable.reddit_image_error);
        thumbnail.setDefaultImageResId(R.drawable.reddit_image_error);
        thumbnail.setLayoutParams(new LayoutParams(Util.dpToPx(75, context), Util.dpToPx(75, context)));
        thumbnail.setImageUrl(Util.getThumbnail(post), RequestManager.getInstance(context.getApplicationContext()).getImageLoader());
        thumbnail.setScaleType(ImageView.ScaleType.FIT_XY);
        thumbnail.setPadding(5, 5, 5, 5);


        addView(thumbnail);
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(VERTICAL);

        TextView tvDomain = new TextView(context);
        tvDomain.setText(post.domain);

        TextView tvLink = new TextView(context);
        tvLink.setText(post.url);
        tvLink.setMaxLines(1);
        layout.addView(tvDomain);
        layout.addView(tvLink);
        addView(layout);
    }

    public ThumbnailView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public ThumbnailView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
}
