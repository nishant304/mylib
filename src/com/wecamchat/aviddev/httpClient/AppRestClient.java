package com.wecamchat.aviddev.httpClient;

import android.content.Context;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;

/**
 * Created by tsingh on 20/1/15.
 */
public class AppRestClient {

    RequestQueue queue;

    private static AppRestClient client = null;

    private AppRestClient(RequestQueue queue) {
        this.queue = queue;
    }

    public static synchronized AppRestClient getClient(){
        return client;
    }

    public void sendRequest(AppHttpRequest request, Object tag){
        request.setTag(tag);
        queue.add(request);
    }

    /**
     * cancels all the request that came from this tag.
     * Activity shall call cancelAll on its onStop
     * @param tag
     */
    public void cancelAll(Object tag){
        queue.cancelAll(tag);
    }

    public static void init(Context context) {
        Cache cache = new DiskBasedCache(context.getCacheDir(), 1024 * 1024); // 1MB cap
        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new AppNetwork(context));
        RequestQueue queue = new RequestQueue(cache, network);
        queue.start();
        client = new AppRestClient(queue);
    }
}
