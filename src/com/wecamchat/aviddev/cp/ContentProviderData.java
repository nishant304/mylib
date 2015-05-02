package com.wecamchat.aviddev.cp;

import android.net.Uri;

import com.wecamchat.aviddev.constant.AppConstant;

public class ContentProviderData {

    public static final String AUTHORITY = AppConstant
            .CONTENT_PROVIDER_AUTHORITY_NAME();

    // The URI scheme used for content URIs
    public static final String SCHEME = "content";

    /**
     * The DataProvider content URI
     */
    public static final Uri CONTENT_URI = Uri.parse(SCHEME + "://" + AUTHORITY);

    public static final int ID_MAP_USER = 1;
    public static final int ID_BROWSE_USER = 2;
    
    public static final int ID_MEDIA_CONTENT = 3;

}
