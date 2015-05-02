package com.wecamchat.aviddev.api.io;

import com.wecamchat.aviddev.api.ApiInput;

public class UserFindWithInInput extends ApiInput {

    private float lngBottomLeft;
    private float latBottomLeft;
    private float lngUpperRight;
    private float latUpperRight;
    private String metaData;
    private int userId;
    private boolean isFavBy;
    private boolean isWinkedBy;
    private int limit;
    private int skip;

    /**
     * @return the lngBottomLeft
     */
    public float getLngBottomLeft() {
        return lngBottomLeft;
    }

    /**
     * @param lngBottomLeft
     *            the lngBottomLeft to set
     */
    public void setLngBottomLeft(float lngBottomLeft) {
        this.lngBottomLeft = lngBottomLeft;
        setValueInMap("lngBottomLeft", lngBottomLeft + "");
    }

    /**
     * @return the latBottomLeft
     */
    public float getLatBottomLeft() {
        return latBottomLeft;
    }

    /**
     * @param latBottomLeft
     *            the latBottomLeft to set
     */
    public void setLatBottomLeft(float latBottomLeft) {
        this.latBottomLeft = latBottomLeft;
        setValueInMap("latBottomLeft", latBottomLeft + "");
    }

    /**
     * @return the lngUpperRight
     */
    public float getLngUpperRight() {
        return lngUpperRight;
    }

    /**
     * @param lngUpperRight
     *            the lngUpperRight to set
     */
    public void setLngUpperRight(float lngUpperRight) {
        this.lngUpperRight = lngUpperRight;
        setValueInMap("lngUpperRight", lngUpperRight + "");
    }

    /**
     * @return the latUpperRight
     */
    public float getLatUpperRight() {
        return latUpperRight;
    }

    /**
     * @param latUpperRight
     *            the latUpperRight to set
     */
    public void setLatUpperRight(float latUpperRight) {
        this.latUpperRight = latUpperRight;
        setValueInMap("latUpperRight", latUpperRight + "");
    }

    /**
     * @return the metaData
     */
    public String getMetaData() {
        return metaData;
    }

    /**
     * @param metaData
     *            the metaData to set
     */
    public void setMetaData(String metaData) {
        this.metaData = metaData;
        setValueInMap("metaData", metaData + "");
    }

    /**
     * @return the userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @param userId
     *            the userId to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
        setValueInMap("userId", userId + "");
    }

    /**
     * @return the isFavBy
     */
    public boolean isFavBy() {
        return isFavBy;
    }

    /**
     * @param isFavBy
     *            the isFavBy to set
     */
    public void setFavBy(boolean isFavBy) {
        this.isFavBy = isFavBy;
        setValueInMap("isFavBy", isFavBy + "");
    }

    /**
     * @return the isWinkedBy
     */
    public boolean isWinkedBy() {
        return isWinkedBy;
    }

    /**
     * @param isWinkedBy
     *            the isWinkedBy to set
     */
    public void setWinkedBy(boolean isWinkedBy) {
        this.isWinkedBy = isWinkedBy;
        setValueInMap("isWinkedBy", isWinkedBy + "");
    }

    /**
     * @return the limit
     */
    public int getLimit() {
        return limit;
    }

    /**
     * @param limit
     *            the limit to set
     */
    public void setLimit(int limit) {
        this.limit = limit;
        setValueInMap("limit", limit + "");
    }

    /**
     * @return the skip
     */
    public int getSkip() {
        return skip;
    }

    /**
     * @param skip
     *            the skip to set
     */
    public void setSkip(int skip) {
        this.skip = skip;
        setValueInMap("skip", skip + "");
    }

}
