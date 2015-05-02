package com.wecamchat.aviddev.api;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.wecamchat.aviddev.activity.AvidMediaApplication;
import com.wecamchat.aviddev.util.PreferenceKeeper;

public class MultipartRequest extends Request<String> {

    private MultipartEntity entity;

    private static final String FILE_PART_NAME = "image";
    private static final String STRING_PART_NAME = "name";
    // private static final String SELFIE_IMAGE = "selfieImage";
    // private static final String SELFIE_CAPTION = "cap";

    private final Response.Listener<String> mListener;
    private final File mFilePart;
    private final String mStringPart;

    private PreferenceKeeper prefs;
    ApiResponseHandler mHandler;

    public MultipartRequest(String url, Response.ErrorListener errorListener,
            Response.Listener<String> listener, File file, String stringPart,
            ApiResponseHandler handler) {
        super(Method.POST, url, errorListener);

        mListener = listener;
        mFilePart = file;
        mStringPart = stringPart;
        mHandler = handler;
        entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);

        buildMultipartEntity();
    }

    @Override
    public int getMethod() {
        return Method.POST;
        // return super.getMethod();
    }

    private void buildMultipartEntity() {
        System.out.println("buildMultipartEntity");

        final FileBody image = new FileBody(mFilePart, "image/pjpeg");
        entity.addPart(FILE_PART_NAME, image);
        try {
            entity.addPart(STRING_PART_NAME, new StringBody(mStringPart));
        } catch (UnsupportedEncodingException e) {
            VolleyLog.e("UnsupportedEncodingException");
        }
    }

    @Override
    public String getBodyContentType()

    {
        String contentType = "multipart/form-data; boundary=----WebKitFormBoundaryUi1zdRkapaiJWQ7G";
        System.out.println("getBodyContentType");
        // return entity.getContentType().getValue();

        return contentType;
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        System.out.println("getBody");

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            entity.writeTo(bos);
        } catch (IOException e) {
            VolleyLog.e("IOException writing to ByteArrayOutputStream");
        }
        return bos.toByteArray();
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        Map<String, String> mapHeader = response.headers;

        // mHandler.setCookie(mapHeader);

        AvidMediaApplication.get().checkSessionCookie(mapHeader);

        try {
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            return Response.success(jsonString,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }

        // return Response.success("Uploaded", getCacheEntry());
    }

    @Override
    protected void deliverResponse(String response) {
        mListener.onResponse(response);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = super.getHeaders();

        if (headers == null || headers.equals(Collections.emptyMap())) {
            headers = new HashMap<String, String>();
        }

        AvidMediaApplication.get().addSessionCookie(headers);

        return headers;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(STRING_PART_NAME, mStringPart);
        return map;
    }

    // @Override
    // public Map<String, String> getHeaders() throws AuthFailureError {
    // Map<String, String> headers = super.getHeaders();
    // System.out.println("getHeaders  " + headers);
    //
    // if (headers == null || headers.equals(Collections.emptyMap())) {
    // headers = new HashMap<String, String>();
    // }
    //
    // // // // AppController.getInstance().addSessionCookie(headers);
    //
    // return headers;
    //
    // }

}
