package com.wecamchat.aviddev.api;

import java.util.Map;

import com.android.volley.VolleyError;

public abstract class ApiResponseHandler {

    public abstract void onSuccess(int reqId, ApiOutput output, ApiName type);

    public abstract void onFailure(int reqId, ErrorObject errorObject, ApiName type);

    public abstract void VolleyError(int reqId, VolleyError error, ApiName type);

    public void setCookie(Map<String, String> cookie) {
    }


}
