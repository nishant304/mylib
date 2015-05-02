
package com.wecamchat.aviddev.api.io;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

public class Pos {

    @Expose
    private String type;
    @Expose
    private List<Double> coordinates = new ArrayList<Double>();

    /**
     * 
     * @return
     *     The type
     */
    public String getType() {
        return type;
    }

    /**
     * 
     * @param type
     *     The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 
     * @return
     *     The coordinates
     */
    public List<Double> getCoordinates() {
        return coordinates;
    }

    /**
     * 
     * @param coordinates
     *     The coordinates
     */
    public void setCoordinates(List<Double> coordinates) {
        this.coordinates = coordinates;
    }

}
