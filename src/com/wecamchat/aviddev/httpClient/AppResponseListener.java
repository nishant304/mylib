package com.wecamchat.aviddev.httpClient;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.JsonObject;
import com.wecamchat.aviddev.api.ErrorObject;
import com.wecamchat.aviddev.util.AppUtil;

/**
 * Created by tsingh on 20/1/15.
 */
public abstract class AppResponseListener<T> implements
        Response.Listener<String>, Response.ErrorListener {

    private final Class<T> t;

    @Override
    public void onErrorResponse(VolleyError error) {
        onError(new ErrorObject("cncws",
                "Oops, could not communicate with server"));
    }

    protected AppResponseListener(Class<T> t) {
        this.t = t;
    }

    @Override
    public void onResponse(String response) {
        JsonObject obj = AppUtil.parseJson(response);
        if (obj.get("statusCode").getAsInt() == 1) {
            onSuccess(AppUtil.parseJson(obj.get("result"), t));
        } else {
            onError(AppUtil.parseJson(obj.get("result"), ErrorObject.class));
        }
    }

    public abstract void onSuccess(T t);

    public abstract void onError(ErrorObject error);

}
