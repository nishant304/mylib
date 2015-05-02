package com.wecamchat.aviddev.api;

import java.io.File;
import java.io.InputStream;

import org.apache.http.HttpEntity;
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

public class HttpClientProfileImageUpload {

    public static org.apache.http.Header[] cookieHeader;

    public static String uploadImageVideo(File file, String url, int position,
            String header, ApiResponseHandler handler) {

        final StringBuilder serverResponse = new StringBuilder();
        try {
            final HttpClient httpclient = new DefaultHttpClient();
            HttpContext localContext = new BasicHttpContext();

            final HttpPost httppost = new HttpPost(url);

            final MultipartEntity reqEntity = new MultipartEntity(
                    HttpMultipartMode.BROWSER_COMPATIBLE);

            final FileBody image = new FileBody(file);

            reqEntity.addPart("image", image);

            reqEntity.addPart("position",
                    new StringBody(String.valueOf(position)));

            httppost.setEntity(reqEntity);

            httppost.addHeader("Cookie", header);

            final HttpResponse response = httpclient.execute(httppost,
                    localContext);

            cookieHeader = response.getHeaders("set-cookie");

            final HttpEntity resEntity = response.getEntity();

            if (resEntity != null) {
                final InputStream stream = resEntity.getContent();
                byte bytes[] = new byte[4096];
                int numBytes;
                while ((numBytes = stream.read(bytes)) != -1) {
                    if (numBytes != 0) {
                        serverResponse.append(new String(bytes, 0, numBytes));
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return serverResponse.toString();

    }
}
