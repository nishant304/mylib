package com.wecamchat.aviddev.model.bo;

/**
 * This Class is used to contain data info of image and video for user.
 * 
 * @author gaurav
 * 
 */
public class MediaContent {

    private String url;
    private String type;
    private long key;

    private int position;
    private int uId;

    private String tn;

    public String getTn() {
        return tn;
    }

    public void setTn(String tn) {
        this.tn = tn;
    }

    public int getuId() {
        return uId;
    }

    public void setuId(int uId) {
        this.uId = uId;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * @return the key
     */
    public long getKey() {
        return key;
    }

    /**
     * @param key
     *            the key to set
     */
    public void setKey(long key) {
        this.key = key;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url
     *            the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

}
