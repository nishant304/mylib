
package com.wecamchat.aviddev.api.io;

import com.google.gson.annotations.Expose;

public class Pic {

    @Expose
    private String url;
    @Expose
    private String type;
    @Expose
    private Long key;

    /**
     * 
     * @return
     *     The url
     */
    public String getUrl() {
        return url;
    }

    /**
     * 
     * @param url
     *     The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 
     * @return
     *     The type
     */
    public String getType() {
        return type;
    }

    /**
     * 
     * @param type
     *     The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 
     * @return
     *     The key
     */
    public Long getKey() {
        return key;
    }

    /**
     * 
     * @param key
     *     The key
     */
    public void setKey(Long key) {
        this.key = key;
    }

}
