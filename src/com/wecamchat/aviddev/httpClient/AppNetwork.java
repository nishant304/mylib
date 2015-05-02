package com.wecamchat.aviddev.httpClient;

import java.io.IOException;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.HurlStack;
import com.wecamchat.aviddev.util.AppFile;
import com.wecamchat.aviddev.util.PreferenceKeeper;

/**
 * Created by tsingh on 20/1/15.
 */
public class AppNetwork extends HurlStack {

    Context context;

    AppNetwork(Context context){
        this.context = context;
    }

    @Override
    public HttpResponse performRequest(Request<?> request, Map<String, String> additionalHeaders) throws IOException, AuthFailureError {
        AppHttpRequest appRequest = (AppHttpRequest)request;
        HttpResponse resp;
//        TODO: Add Cookie header here.
        additionalHeaders.put("cookie", PreferenceKeeper.getCookie(context));
        if(appRequest.getFileParams() != null){
            resp = getResponse(appRequest.getFileParams(), appRequest.getParams(), additionalHeaders, request.getUrl());
        }else{
            resp = super.performRequest(request, additionalHeaders);
        }
//        TODO: Extract cookie header here.
        String cookie = getCookie(resp);
        if(cookie!=null)
            PreferenceKeeper.saveCookie(cookie, context);
        return resp;
    }

    private String getCookie(HttpResponse resp) {
        Header[] headers = resp.getHeaders("set-cookie");
        if(headers.length==1){
            return headers[0].getValue();
        }
        return null;
    }

    public HttpResponse getResponse(Map<String, AppFile> files,
                                           Map<String, String> params,
                                           Map<String, String> headers,
                                           String url) throws IOException {

        HttpClient httpclient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        final HttpPost httppost = new HttpPost(url);
        final MultipartEntity reqEntity = new MultipartEntity(
                HttpMultipartMode.BROWSER_COMPATIBLE);

        for(String key : files.keySet()){
            AppFile appFile = files.get(key);
            reqEntity.addPart(key, new FileBody(appFile.getFile(), appFile.getMimeType()));
        }
        if(params!=null){
            for(String key : params.keySet()){
                reqEntity.addPart(key, new StringBody(params.get(key).toString()));
            }
        }
        httppost.setEntity(reqEntity);
        if (headers != null) {
            for(String key : headers.keySet()){
                httppost.addHeader(key, headers.get(key));
            }
        }
        return httpclient.execute(httppost, localContext);
    }
}
