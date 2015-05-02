package com.wecamchat.aviddev.model.bo;

import java.util.List;

public class Position {

    private String type;
    private List<Double> coordinates;
    /**
     * @return the type
     */
    public String getType() {
        return type;
    }
    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }
    /**
     * @return the coordinates
     */
    public List<Double> getCoordinates() {
        return coordinates;
    }
    /**
     * @param coordinates the coordinates to set
     */
    public void setCoordinates(List<Double> coordinates) {
        this.coordinates = coordinates;
    }

    
    
}
