package com.wecamchat.aviddev.api.io;

import com.wecamchat.aviddev.api.ApiInput;

public class MediaDeleteInput extends ApiInput {

    private String key;

    /**
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * @param key
     *            the key to set
     */
    public void setKey(String key) {
        this.key = key;
    }
}
