package com.wecamchat.aviddev.activity;

import java.util.Map;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.wecamchat.aviddev.httpClient.AppRestClient;
import com.wecamchat.aviddev.util.volley.AvidVolley;

public class AvidMediaApplication extends Application {

    private static AvidMediaApplication _instance;

    private static final String SET_COOKIE_KEY = "set-cookie";
    private static final String COOKIE_KEY = "Cookie";
    private static final String SESSION_COOKIE = "connect.sid";
    private SharedPreferences _preferences;

    /**
     * Initializes the application level data
     */

    @Override
    public void onCreate() {
        super.onCreate();
        initImageLoader(getApplicationContext());
        initializeVolley();
        _instance = this;
        _preferences = PreferenceManager.getDefaultSharedPreferences(this);

    }

    private void initializeVolley() {
        AvidVolley.init(getBaseContext());
        AppRestClient.init(getBaseContext());
    }

    public static void initImageLoader(Context context) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                context).threadPriority(Thread.NORM_PRIORITY)
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .writeDebugLogs() // Remove for release app
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }

    public static AvidMediaApplication get() {
        return _instance;
    }

    /**
     * Checks the response headers for session cookie and saves it if it finds
     * it.
     * 
     * @param headers
     *            Response Headers.
     */
    public final void checkSessionCookie(Map<String, String> headers) {
        if (headers.containsKey(SET_COOKIE_KEY)
                && headers.get(SET_COOKIE_KEY).startsWith(SESSION_COOKIE)) {
            String cookie = headers.get(SET_COOKIE_KEY);
            if (cookie.length() > 0) {
                String[] splitCookie = cookie.split(";");
                String[] splitSessionId = splitCookie[0].split("=");
                cookie = splitSessionId[1];
                Editor prefEditor = _preferences.edit();
                prefEditor.putString(SESSION_COOKIE, cookie);
                prefEditor.commit();
            }
        }
    }

    /**
     * Adds session cookie to headers if exists.
     * 
     * @param headers
     */

    public Map<String, String> getHeader() {
        System.out.println("header map " + map);
        return map;
    }

    Map<String, String> map;

    public final void addSessionCookie(Map<String, String> headers) {
        String sessionId = _preferences.getString(SESSION_COOKIE, "");
        System.out.println("header " + sessionId);
        if (sessionId.length() > 0) {
            StringBuilder builder = new StringBuilder();
            builder.append(SESSION_COOKIE);
            builder.append("=");
            builder.append(sessionId);
            if (headers.containsKey(COOKIE_KEY)) {
                builder.append("; ");
                builder.append(headers.get(COOKIE_KEY));
            }
            headers.put(COOKIE_KEY, builder.toString());
            this.map = headers;
        }
    }

}
