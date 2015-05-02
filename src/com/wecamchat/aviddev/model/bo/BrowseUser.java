package com.wecamchat.aviddev.model.bo;

public class BrowseUser {

    private long uId;
    private String uName;
    private int userMarkType;
    private int outlineImageType;
    private String uImg;
    private String distance;

    /**
     * @return the uId
     */
    public long getuId() {
        return uId;
    }

    /**
     * @param uId
     *            the uId to set
     */
    public void setuId(long uId) {
        this.uId = uId;
    }

    /**
     * @return the uName
     */
    public String getuName() {
        return uName;
    }

    /**
     * @param uName
     *            the uName to set
     */
    public void setuName(String uName) {
        this.uName = uName;
    }

    /**
     * @return the userMarkType
     */
    public int getUserMarkType() {
        return userMarkType;
    }

    /**
     * @param userMarkType
     *            the userMarkType to set
     */
    public void setUserMarkType(int userMarkType) {
        this.userMarkType = userMarkType;
    }

    /**
     * @return the outlineImageType
     */
    public int getOutlineImageType() {
        return outlineImageType;
    }

    /**
     * @param outlineImageType
     *            the outlineImageType to set
     */
    public void setOutlineImageType(int outlineImageType) {
        this.outlineImageType = outlineImageType;
    }

    /**
     * @return the uImg
     */
    public String getuImg() {
        return uImg;
    }

    /**
     * @param uImg
     *            the uImg to set
     */
    public void setuImg(String uImg) {
        this.uImg = uImg;
    }

    /**
     * @return the distance
     */
    public String getDistance() {
        return distance;
    }

    /**
     * @param distance
     *            the distance to set
     */
    public void setDistance(String distance) {
        this.distance = distance;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "BrowseUser [uId=" + uId + ", uName=" + uName
                + ", userMarkType=" + userMarkType + ", outlineImageType="
                + outlineImageType + ", uImg=" + uImg + ", distance="
                + distance + "]";
    }

}
