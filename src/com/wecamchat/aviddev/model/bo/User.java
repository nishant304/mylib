package com.wecamchat.aviddev.model.bo;

import java.util.List;

public class User {

    private long uId;
    private String uName;
    private Double mLatitude;
    private Double mLongitude;
    private int activeStatus;
    private String uImg;
    private int userType;
    private List<MediaContent> mediaContents;
    private int winkCount;
    private String profileDesc;
    private String lastActiveTime;
    private String distance;

    private int age;

    private MetaData metaData;

    private String privateNotes;
    private boolean isUser = true;

    /**
     * @return the isUser
     */
    public boolean isUser() {
        return isUser;
    }

    /**
     * @param isUser
     *            the isUser to set
     */
    public void setUser(boolean isUser) {
        this.isUser = isUser;
    }

    // /**
    // * @return the height
    // */
    // public String getHeight() {
    // return height;
    // }
    //
    // /**
    // * @param height
    // * the height to set
    // */
    // public void setHeight(String height) {
    // this.height = height;
    // }
    //
    // /**
    // * @return the drink
    // */
    // public String getDrink() {
    // return drink;
    // }
    //
    // /**
    // * @param drink
    // * the drink to set
    // */
    // public void setDrink(String drink) {
    // this.drink = drink;
    // }
    //
    // /**
    // * @return the eyeColor
    // */
    // public String getEyeColor() {
    // return eyeColor;
    // }
    //
    // /**
    // * @param eyeColor
    // * the eyeColor to set
    // */
    // public void setEyeColor(String eyeColor) {
    // this.eyeColor = eyeColor;
    // }
    //
    // /**
    // * @return the hairColor
    // */
    // public String getHairColor() {
    // return hairColor;
    // }
    //
    // /**
    // * @param hairColor
    // * the hairColor to set
    // */
    // public void setHairColor(String hairColor) {
    // this.hairColor = hairColor;
    // }
    //
    // /**
    // * @return the smoke
    // */
    // public String getSmoke() {
    // return smoke;
    // }
    //
    // /**
    // * @param smoke
    // * the smoke to set
    // */
    // public void setSmoke(String smoke) {
    // this.smoke = smoke;
    // }
    //
    // /**
    // * @return the outTo
    // */
    // public String getOutTo() {
    // return outTo;
    // }
    //
    // /**
    // * @param outTo
    // * the outTo to set
    // */
    // public void setOutTo(String outTo) {
    // this.outTo = outTo;
    // }
    //
    // /**
    // * @return the ethnicity
    // */
    // public String getEthnicity() {
    // return ethnicity;
    // }
    //
    // /**
    // * @param ethnicity
    // * the ethnicity to set
    // */
    // public void setEthnicity(String ethnicity) {
    // this.ethnicity = ethnicity;
    // }

    /**
     * @return the mediaContents
     */
    public List<MediaContent> getMediaContents() {
        return mediaContents;
    }

    /**
     * @param mediaContents
     *            the mediaContents to set
     */
    public void setMediaContents(List<MediaContent> mediaContents) {
        this.mediaContents = mediaContents;
    }

    /**
     * @return the winkCount
     */
    public int getWinkCount() {
        return winkCount;
    }

    /**
     * @param winkCount
     *            the winkCount to set
     */
    public void setWinkCount(int winkCount) {
        this.winkCount = winkCount;
    }

    /**
     * @return the profileDesc
     */
    public String getProfileDesc() {
        return profileDesc;
    }

    /**
     * @param profileDesc
     *            the profileDesc to set
     */
    public void setProfileDesc(String profileDesc) {
        this.profileDesc = profileDesc;
    }

    /**
     * @return the lastActiveTime
     */
    public String getLastActiveTime() {
        return lastActiveTime;
    }

    /**
     * @param lastActiveTime
     *            the lastActiveTime to set
     */
    public void setLastActiveTime(String lastActiveTime) {
        this.lastActiveTime = lastActiveTime;
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

    /**
     * @return the age
     */
    public int getAge() {
        return age;
    }

    /**
     * @param age
     *            the age to set
     */
    public void setAge(int age) {
        this.age = age;
    }

    // /**
    // * @return the orientation
    // */
    // public String getOrientation() {
    // return orientation;
    // }
    //
    // /**
    // * @param orientation
    // * the orientation to set
    // */
    // public void setOrientation(String orientation) {
    // this.orientation = orientation;
    // }
    //
    // /**
    // * @return the bodyType
    // */
    // public String getBodyType() {
    // return bodyType;
    // }
    //
    // /**
    // * @param bodyType
    // * the bodyType to set
    // */
    // public void setBodyType(String bodyType) {
    // this.bodyType = bodyType;
    // }
    //
    // /**
    // * @return the temperment
    // */
    // public String getTemperment() {
    // return temperment;
    // }
    //
    // /**
    // * @param temperment
    // * the temperment to set
    // */
    // public void setTemperment(String temperment) {
    // this.temperment = temperment;
    // }
    //
    // /**
    // * @return the size
    // */
    // public String getSize() {
    // return size;
    // }
    //
    // /**
    // * @param size
    // * the size to set
    // */
    // public void setSize(String size) {
    // this.size = size;
    // }
    //
    // /**
    // * @return the hivStatus
    // */
    // public String getHivStatus() {
    // return hivStatus;
    // }
    //
    // /**
    // * @param hivStatus
    // * the hivStatus to set
    // */
    // public void setHivStatus(String hivStatus) {
    // this.hivStatus = hivStatus;
    // }
    //
    // /**
    // * @return the upFor
    // */
    // public String getUpFor() {
    // return upFor;
    // }
    //
    // /**
    // * @param upFor
    // * the upFor to set
    // */
    // public void setUpFor(String upFor) {
    // this.upFor = upFor;
    // }
    //
    // /**
    // * @return the role
    // */
    // public String getRole() {
    // return role;
    // }
    //
    // /**
    // * @param role
    // * the role to set
    // */
    // public void setRole(String role) {
    // this.role = role;
    // }
    //
    // /**
    // * @return the persona
    // */
    // public String getPersona() {
    // return persona;
    // }
    //
    // /**
    // * @param persona
    // * the persona to set
    // */
    // public void setPersona(String persona) {
    // this.persona = persona;
    // }
    //
    // /**
    // * @return the bodyHair
    // */
    // public String getBodyHair() {
    // return bodyHair;
    // }
    //
    // /**
    // * @param bodyHair
    // * the bodyHair to set
    // */
    // public void setBodyHair(String bodyHair) {
    // this.bodyHair = bodyHair;
    // }
    //
    // /**
    // * @return the cut
    // */
    // public String getCut() {
    // return cut;
    // }
    //
    // /**
    // * @param cut
    // * the cut to set
    // */
    // public void setCut(String cut) {
    // this.cut = cut;
    // }

    /**
     * @return the privateNotes
     */
    public String getPrivateNotes() {
        return privateNotes;
    }

    /**
     * @param privateNotes
     *            the privateNotes to set
     */
    public void setPrivateNotes(String privateNotes) {
        this.privateNotes = privateNotes;
    }

    /**
     * @return the userType
     */
    public int getUserType() {
        return userType;
    }

    /**
     * @param userType
     *            the userType to set
     */
    public void setUserType(int userType) {
        this.userType = userType;
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
     * @return the mLatitude
     */
    public Double getmLatitude() {
        return mLatitude;
    }

    /**
     * @param mLatitude
     *            the mLatitude to set
     */
    public void setmLatitude(Double mLatitude) {
        this.mLatitude = mLatitude;
    }

    /**
     * @return the mLongitude
     */
    public Double getmLongitude() {
        return mLongitude;
    }

    /**
     * @param mLongitude
     *            the mLongitude to set
     */
    public void setmLongitude(Double mLongitude) {
        this.mLongitude = mLongitude;
    }

    /**
     * @return the activeStatus
     */
    public int getActiveStatus() {
        return activeStatus;
    }

    /**
     * @param activeStatus
     *            the activeStatus to set
     */
    public void setActiveStatus(int activeStatus) {
        this.activeStatus = activeStatus;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "User [uId=" + uId + ", uName=" + uName + ", mLatitude="
                + mLatitude + ", mLongitude=" + mLongitude + ", activeStatus="
                + activeStatus + ", uImg=" + uImg + ", userType=" + userType
                + ", mediaContents=" + mediaContents + ", winkCount="
                + winkCount + ", profileDesc=" + profileDesc
                + ", lastActiveTime=" + lastActiveTime + ", distance="

                + distance + ", age=" + age + ", orientation="
                + getMetaData().getOrientation() + ", bodyType="
                + getMetaData().getBody_type() + ", temperment="
                + getMetaData().getTemperament() + ", size="
                + getMetaData().getSize() + ", hivStatus="
                + getMetaData().getHiv_status() + ", upFor="
                + getMetaData().getUp_for() + ", role="
                + getMetaData().getRole() + ", persona="
                + getMetaData().getPersona() + ", bodyHair="
                + getMetaData().getBody_hair() + ", cut="
                + getMetaData().getCut() + ", privateNotes=" + privateNotes
                + ", height=" + getMetaData().getHeight() + ", drink="
                + getMetaData().getDrink() + ", eyeColor="
                + getMetaData().getEye_color() + ", hairColor="
                + getMetaData().getHair_color() + ", smoke="
                + getMetaData().getSmoke() + ", outTo="
                + getMetaData().getOut_to() + ", ethnicity="
                + getMetaData().getEthnicity() + ", isUser=" + isUser + "]";
    }

    public MetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(MetaData metaData) {
        this.metaData = metaData;
    }

}
