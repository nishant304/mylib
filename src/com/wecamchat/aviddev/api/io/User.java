
package com.wecamchat.aviddev.api.io;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.wecamchat.aviddev.model.bo.Unique;

public class User implements DBClass{

    @SerializedName("_id")
    @Expose
    @Unique
    private String Id;
    @Expose
    private String name;
    @Expose
    private MetaData metaData;
    @Expose
    private List<Pic> pics = new ArrayList<Pic>();
    @Expose
    private String email;
    @Expose
    private String phone;
    @Expose
    private Pos pos;
    @Expose
    private Boolean hot;
    @Expose
    private Double distance;

    /**
     * 
     * @return
     *     The Id
     */
    public String getId() {
        return Id;
    }

    /**
     * 
     * @param Id
     *     The _id
     */
    public void setId(String Id) {
        this.Id = Id;
    }

    /**
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return
     *     The metaData
     */
    public MetaData getMetaData() {
        return metaData;
    }

    /**
     * 
     * @param metaData
     *     The metaData
     */
    public void setMetaData(MetaData metaData) {
        this.metaData = metaData;
    }

    /**
     * 
     * @return
     *     The pics
     */
    public List<Pic> getPics() {
        return pics;
    }

    /**
     * 
     * @param pics
     *     The pics
     */
    public void setPics(List<Pic> pics) {
        this.pics = pics;
    }

    /**
     * 
     * @return
     *     The email
     */
    public String getEmail() {
        return email;
    }

    /**
     * 
     * @param email
     *     The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 
     * @return
     *     The phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 
     * @param phone
     *     The phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 
     * @return
     *     The pos
     */
    public Pos getPos() {
        return pos;
    }

    /**
     * 
     * @param pos
     *     The pos
     */
    public void setPos(Pos pos) {
        this.pos = pos;
    }

    /**
     * 
     * @return
     *     The hot
     */
    public Boolean getHot() {
        return hot;
    }

    /**
     * 
     * @param hot
     *     The hot
     */
    public void setHot(Boolean hot) {
        this.hot = hot;
    }

    /**
     * 
     * @return
     *     The distance
     */
    public Double getDistance() {
        return distance;
    }

    /**
     * 
     * @param distance
     *     The distance
     */
    public void setDistance(Double distance) {
        this.distance = distance;
    }

}
