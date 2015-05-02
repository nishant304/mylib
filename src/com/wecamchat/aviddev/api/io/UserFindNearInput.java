package com.wecamchat.aviddev.api.io;

import com.wecamchat.aviddev.api.ApiInput;

public class UserFindNearInput extends ApiInput {

    private float lng;
    private float lat;
    private int maxDistance;
    private int minDistance;
    private String metaData;
    private int userId;
    private boolean isFavBy;
    private boolean isWinkedBy;
    private int limit;
    private int skip;

    /**
     * @return the lng
     */
    public float getLng() {
        return lng;
    }

    /**
     * @param lng
     *            the lng to set
     */
    public void setLng(float lng) {
        this.lng = lng;
        setValueInMap("lng", lng + "");
    }

    /**
     * @return the lat
     */
    public float getLat() {
        return lat;
    }

    /**
     * @param lat
     *            the lat to set
     */
    public void setLat(float lat) {
        this.lat = lat;
        setValueInMap("lat", lat + "");
    }

    /**
     * @return the maxDistance
     */
    public int getMaxDistance() {
        return maxDistance;
    }

    /**
     * @param maxDistance
     *            the maxDistance to set
     */
    public void setMaxDistance(int maxDistance) {
        this.maxDistance = maxDistance;
        setValueInMap("maxDistance", maxDistance + "");
    }

    /**
     * @return the minDistance
     */
    public int getMinDistance() {
        return minDistance;
    }

    /**
     * @param minDistance
     *            the minDistance to set
     */
    public void setMinDistance(int minDistance) {
        this.minDistance = minDistance;
        setValueInMap("minDistance", minDistance + "");
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
