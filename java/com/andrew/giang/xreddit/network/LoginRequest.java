package com.andrew.giang.xreddit.network;

import android.util.Log;
import com.android.volley.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import static com.android.volley.toolbox.HttpHeaderParser.parseCacheHeaders;
import static com.android.volley.toolbox.HttpHeaderParser.parseCharset;

/**
 * Created by Andrew on 9/2/13.
 */
public class LoginRequest extends Request<JSONObject> {
    private final Response.Listener<JSONObject> mListener;
    private Map<String, String> mParams = new HashMap<String, String>();

    public LoginRequest(String user, String pw, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(Method.POST, "http://www.reddit.com/api/login/", errorListener);
        this.mListener = listener;
        mParams.put("user", user);
        mParams.put("passwd", pw);
        mParams.put("api_type", "json");

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return mParams;
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        Log.w("COOKIE", response.headers.get("Set-Cookie"));
        try {
            String jsonString =
                    new String(response.data, parseCharset(response.headers));
            return Response.success(new JSONObject(jsonString),
                    parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }

    }

    @Override
    protected void deliverResponse(JSONObject jsonObject) {
        mListener.onResponse(jsonObject);
    }
}
