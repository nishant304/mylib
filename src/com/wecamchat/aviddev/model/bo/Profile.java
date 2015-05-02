package com.wecamchat.aviddev.model.bo;

import java.util.List;

public class Profile {

    private String _id;
    private Position pos;
    private String name;
    private String email;
    private String pass;
    private String phone;
    private long lastSeen;
    private MetaData metaData;

    private List<MediaContent> pics;

    /**
     * { "statusCode": 1, "result": { "phone": "08447628087", "pics": [ {
     * "type": "image", "key": 1421757111254, "url":
     * "https://avid-dev.s3.amazonaws.com/1421743800505.PNG" } ], "_id":
     * "54be4ab79c27f83c13f2e287", "email": "gaurav.dixit@mobicules.com",
     * "metaData": {}, "pos": { "type": "Point", "coordinates": [ 77.375, 28.622
     * ] }, "name": "Gaurav" } }
     * 
     * 
     */

    /**
     * @return the _id
     */
    public String get_id() {
        return _id;
    }

    /**
     * @param _id
     *            the _id to set
     */
    public void set_id(String _id) {
        this._id = _id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email
     *            the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the pass
     */
    public String getPass() {
        return pass;
    }

    /**
     * @param pass
     *            the pass to set
     */
    public void setPass(String pass) {
        this.pass = pass;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone
     *            the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return the lastSeen
     */
    public long getLastSeen() {
        return lastSeen;
    }

    /**
     * @param lastSeen
     *            the lastSeen to set
     */
    public void setLastSeen(Long lastSeen) {
        this.lastSeen = lastSeen;
    }

    /**
     * @return the pics
     */
    public List<MediaContent> getPics() {
        return pics;
    }

    /**
     * @param pics
     *            the pics to set
     */
    public void setPics(List<MediaContent> pics) {
        this.pics = pics;
    }

    public MetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(MetaData metaData) {
        this.metaData = metaData;
    }

    /**
     * @return the pos
     */
    public Position getPos() {
        return pos;
    }

    /**
     * @param pos
     *            the pos to set
     */
    public void setPos(Position pos) {
        this.pos = pos;
    }

}
