package com.andrew.giang.xreddit.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
        this.setMinimumHeight(Util.dpToPixels(context, 160));
        setOrientation(HORIZONTAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        this.setLayoutParams(layoutParams);
        NetworkImageView thumbnail = new NetworkImageView(context);

        thumbnail.setLayoutParams(new LayoutParams(Util.dpToPixels(context, 75),
                Util.dpToPixels(context, 75)));
        thumbnail.setImageUrl(Util.getThumbnail(post),
                RequestManager.getInstance(context.getApplicationContext()).getImageLoader());
        thumbnail.setScaleType(ImageView.ScaleType.FIT_CENTER);
        thumbnail.setPadding(10, 10, 10, 10);


        addView(thumbnail);
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(VERTICAL);
        final LayoutParams layoutParams1 = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams1.setMargins(Util.dpToPixels(context, 10), 0, 0, 0);
        layout.setLayoutParams(layoutParams1);
        TextView tvDomain = new TextView(context);
        tvDomain.setText(post.domain);

        TextView tvLink = new TextView(context);
        tvLink.setText(post.url);
        tvLink.setMaxLines(1);

        final ViewGroup.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.
                MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
        layout.addView(tvDomain, params);
        layout.addView(tvLink, params);
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
