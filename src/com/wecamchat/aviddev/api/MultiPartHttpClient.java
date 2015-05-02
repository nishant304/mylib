package com.wecamchat.aviddev.api;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import android.util.Log;

public class MultiPartHttpClient {

    /**
     * This methos is used to upload photo on server and return true or false
     * response.
     * 
     * @param addphoto
     * @return
     * @throws MasterMatchMakersNetworkException
     * @throws MasterMatchMakersTimeOutException
     */

    private static final String TAG = "MultiPartHttpClient";
    /**
     * appId appKey productId recipientName companyName address1 address2
     * address3 city postalCode region countryCode image
     * 
     * @return
     * @throws NetworkException
     * @throws TimeOutException
     */

    public static org.apache.http.Header[] cookieHeader;
    private static FileBody fileBody;

    public static String uploadPhoto(File file, String url, String name,
            String check, String header, boolean type,
            ApiResponseHandler handler) {

        final StringBuilder serverResponse = new StringBuilder();
        try {
            final HttpClient httpclient = new DefaultHttpClient();
            HttpContext localContext = new BasicHttpContext();

            final HttpPost httppost = new HttpPost(url);

            final MultipartEntity reqEntity = new MultipartEntity(
                    HttpMultipartMode.BROWSER_COMPATIBLE);

            System.out.println("filePath :: " + file.getAbsolutePath());

            String fileFormatType = getFileFormat(file);

            if (type) {
                fileBody = new FileBody(file, "image/" + fileFormatType);

            } else {
                fileBody = new FileBody(file, "video/" + fileFormatType);

            }
            reqEntity.addPart("image", fileBody);
            if (check.equalsIgnoreCase("Profile")) {
                reqEntity.addPart("position", new StringBody(name));

            } else {
                reqEntity.addPart("name", new StringBody(name));
            }

            httppost.setEntity(reqEntity);

            if (header != null) {
                httppost.addHeader("Cookie", header);
            }

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

        Log.d(TAG, "server response :: " + serverResponse.toString());

        return serverResponse.toString();

    }

    private static String getFileFormat(File file) {

        String fileFormat = "";

        try {
            fileFormat = (file.getName().split(".")[1]);

        } catch (Exception e) {
            e.printStackTrace();
            fileFormat = "jpeg";
        }

        return fileFormat;
    }

}
