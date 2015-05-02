package com.wecamchat.aviddev.api.io;

import com.wecamchat.aviddev.api.ApiInput;

public class ProfileEditInput extends ApiInput {

    private String name;
    private String email;
    private String phone;
    private String metaData;
    private Long dob;
    private Double lat;
    private Double lng;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        setValueInMap("name", name);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        setValueInMap("email", email);
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
        setValueInMap("phone", phone);
    }

    public String getMetaData() {
        return metaData;
    }

    public void setMetaData(String metaData) {
        this.metaData = metaData;
        setValueInMap("metaData", metaData);
    }

    public Long getDob() {
        return dob;
    }

    public void setDob(Long dob) {
        this.dob = dob;
        setValueInMap("dob", dob + "");
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
        setValueInMap("lat", lat + "");
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
        setValueInMap("lng", lng + "");
    }

}
