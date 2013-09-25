package com.andrew.giang.xreddit.network;

import android.content.Context;
import android.text.TextUtils;
import com.andrew.giang.xreddit.cache.DiskBitmapCache;
import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by andrew on 8/30/13.
 */
public class RequestManager {


    private static final String TAG = "VolleyRequests";
    private static RequestManager mSingleton;
    private final Context mContext;
    private RequestQueue mRequestQueue;

    public static synchronized RequestManager getInstance(Context context) {
        if (mSingleton == null) {
            mSingleton = new RequestManager(context.getApplicationContext());
        }
        return mSingleton;

    }

    private RequestManager(Context context) {
        this.mRequestQueue = Volley.newRequestQueue(context);
        this.mContext = context;
    }


    public Request makeRequest(Request request) {
        return addToRequestQueue(request);
    }

    public Cache getCache() {
        return mRequestQueue.getCache();
    }

    public <T> Request<T> addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);

        VolleyLog.d("Adding request to queue: %s", req.getUrl());

        return mRequestQueue.add(req);
    }

    public ImageLoader getImageLoader() {//TODO Take as param the ImageCache? set a default cache?
        return new ImageLoader(mRequestQueue, new DiskBitmapCache(mContext.getCacheDir()));
    }


    /**
     * Adds the specified request to the global queue using the Default TAG.
     *
     * @param req
     */
    public <T> Request<T> addToRequestQueue(Request<T> req) {
        // set the default tag if tag is empty
        req.setTag(TAG);

        return mRequestQueue.add(req);
    }

    /**
     * Cancels all pending requests by the specified TAG, it is important
     * to specify a TAG so that the pending/ongoing requests can be cancelled.
     *
     * @param tag
     */
    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}
