
package com.wecamchat.aviddev.api.io;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MetaData {

    @SerializedName("up_for\u2026")
    @Expose
    private String upFor;
    @Expose
    private String orientation;
    @SerializedName("body_hair")
    @Expose
    private String bodyHair;
    @Expose
    private String cut;
    @Expose
    private String drink;
    @Expose
    private String ethnicity;
    @SerializedName("eye_color")
    @Expose
    private String eyeColor;
    @SerializedName("hair_color")
    @Expose
    private String hairColor;
    @Expose
    private String height;
    @SerializedName("out_to")
    @Expose
    private String outTo;
    @Expose
    private String persona;
    @Expose
    private String size;
    @Expose
    private String smoke;
    
   public  int count=0;

    /**
     * 
     * @return
     *     The upFor
     */
    public String getUpFor() {
        return upFor;
    }

    /**
     * 
     * @param upFor
     *     The up_for…
     */
    public void setUpFor(String upFor) {
        this.upFor = upFor;
        count++;
    }

    /**
     * 
     * @return
     *     The orientation
     */
    public String getOrientation() {
        return orientation;
    }

    /**
     * 
     * @param orientation
     *     The orientation
     */
    public void setOrientation(String orientation) {
        this.orientation = orientation;
        count++;
    }

    /**
     * 
     * @return
     *     The bodyHair
     */
    public String getBodyHair() {
        return bodyHair;
    }

    /**
     * 
     * @param bodyHair
     *     The body_hair
     */
    public void setBodyHair(String bodyHair) {
        this.bodyHair = bodyHair;
        count++;
    }

    /**
     * 
     * @return
     *     The cut
     */
    public String getCut() {
        return cut;
    }

    /**
     * 
     * @param cut
     *     The cut
     */
    public void setCut(String cut) {
        this.cut = cut;
        count++;
    }

    /**
     * 
     * @return
     *     The drink
     */
    public String getDrink() {
        return drink;
    }

    /**
     * 
     * @param drink
     *     The drink
     */
    public void setDrink(String drink) {
        this.drink = drink;
        count++;
    }

    /**
     * 
     * @return
     *     The ethnicity
     */
    public String getEthnicity() {
        return ethnicity;
    }

    /**
     * 
     * @param ethnicity
     *     The ethnicity
     */
    public void setEthnicity(String ethnicity) {
        this.ethnicity = ethnicity;
        count++;
    }

    /**
     * 
     * @return
     *     The eyeColor
     */
    public String getEyeColor() {
        return eyeColor;
    }

    /**
     * 
     * @param eyeColor
     *     The eye_color
     */
    public void setEyeColor(String eyeColor) {
        this.eyeColor = eyeColor;
        count++;
    }

    /**
     * 
     * @return
     *     The hairColor
     */
    public String getHairColor() {
        return hairColor;
    }

    /**
     * 
     * @param hairColor
     *     The hair_color
     */
    public void setHairColor(String hairColor) {
        this.hairColor = hairColor;
        count++;
    }

    /**
     * 
     * @return
     *     The height
     */
    public String getHeight() {
        return height;
    }

    /**
     * 
     * @param height
     *     The height
     */
    public void setHeight(String height) {
        this.height = height;
        count++;
    }

    /**
     * 
     * @return
     *     The outTo
     */
    public String getOutTo() {
        return outTo;
    }

    /**
     * 
     * @param outTo
     *     The out_to
     */
    public void setOutTo(String outTo) {
        this.outTo = outTo;
        count++;
    }

    /**
     * 
     * @return
     *     The persona
     */
    public String getPersona() {
        return persona;
    }

    /**
     * 
     * @param persona
     *     The persona
     */
    public void setPersona(String persona) {
        this.persona = persona;
        count++;
    }

    /**
     * 
     * @return
     *     The size
     */
    public String getSize() {
        return size;
    }

    /**
     * 
     * @param size
     *     The size
     */
    public void setSize(String size) {
        this.size = size;
        count++;
    }

    /**
     * 
     * @return
     *     The smoke
     */
    public String getSmoke() {
        return smoke;
    }

    /**
     * 
     * @param smoke
     *     The smoke
     */
    public void setSmoke(String smoke) {
        this.smoke = smoke;
        count++;
    }

}
