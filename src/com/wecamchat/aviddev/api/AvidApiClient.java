package com.wecamchat.aviddev.api;

import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.wecamchat.aviddev.util.volley.AvidVolley;

public class AvidApiClient {

    protected static final String TAG = "AvidApiClient";

    private static AvidApiClient client;

    private RequestQueue queue;

    private AvidApiClient() {
        queue = AvidVolley.getRequestQueue();
    }

    public static synchronized AvidApiClient getInstance() {
        if (client == null) {
            client = new AvidApiClient();

        }
        return client;
    }

    public void post(final int reqId, final String url,
            final Map<String, String> params,
            final Map<String, String> headers, final ApiName apiName,
            final ApiOutput output, final ApiResponseHandler handler) {

        Request<JSONObject> request = new CustomRequest(Method.POST, url,
                params, headers, new Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        Log.d(TAG,
                                " post request on response : "
                                        + jsonObject.toString());

                        parseReponse(jsonObject, handler, reqId, output,
                                apiName);

                    }

                }, new ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError arg0) {

                        Log.d(TAG, " post response : " + arg0.toString());

                        handler.VolleyError(reqId, arg0, apiName);
                    }
                });

        queue.add(request);
    }

    public void get(final int reqId, String url,
            final Map<String, String> params,
            final Map<String, String> headers, final ApiName apiName,
            final ApiOutput output, final ApiResponseHandler handler) {

        url = createGetUrl(url, params);

        Request<JSONObject> request = new CustomRequest(Method.GET, url, null,
                headers, new Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        Log.d(TAG, " get response : " + jsonObject.toString());

                        parseReponse(jsonObject, handler, reqId, output,
                                apiName);

                    }
                }, new ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError arg0) {

                        Log.d(TAG,
                                " get request on Error Response : "
                                        + arg0.toString());

                        handler.VolleyError(reqId, arg0, apiName);
                    }
                });

        queue.add(request);

    }

    private void parseReponse(JSONObject jsonObject,
            ApiResponseHandler handler, int reqId, ApiOutput output,
            ApiName apiName) {
        new JSONParser(jsonObject, handler, reqId, output, apiName).execute();
    }

    private String createGetUrl(String url, Map<String, String> params) {

        if (params != null) {

            RequestParams requestParams = new RequestParams(params);

            return url + requestParams.getParamString();
        }

        return url;
    }

    private boolean isValidResponse(JSONObject jsonObject) {
        try {
            if (jsonObject.has("statusCode")
                    && (jsonObject.getInt("statusCode")) == 1) {

                return true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    private class JSONParser extends AsyncTask<Void, Void, Boolean> {
        JSONObject jsonObject;
        ApiResponseHandler handler;
        int reqId;
        ApiOutput output;
        ApiName apiName;
        ErrorObject errorObject;

        public JSONParser(JSONObject jsonObject, ApiResponseHandler handler,
                int reqId, ApiOutput output, ApiName apiName) {
            this.jsonObject = jsonObject;
            this.handler = handler;
            this.reqId = reqId;
            this.output = output;
            this.apiName = apiName;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            if (isValidResponse(jsonObject)) {
                output.createFromJson(jsonObject);
                return true;

            }

            errorObject = new ErrorObject();
            errorObject.createFromJson(jsonObject);
            return false;

        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

            if (result) {
                output.createFromJson(jsonObject);
                handler.onSuccess(reqId, output, apiName);

                if (CustomRequest.cookieHeader != null) {

                    handler.setCookie(CustomRequest.cookieHeader);

                }

            } else {

                ErrorObject errorObject = new ErrorObject();

                errorObject.createFromJson(jsonObject);

                handler.onFailure(reqId, errorObject, apiName);

            }

        }

    }
}
