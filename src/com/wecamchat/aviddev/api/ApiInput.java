package com.wecamchat.aviddev.api;

import java.util.HashMap;
import java.util.Map;

public class ApiInput {

    private Map<String, String> requestParams;

    protected ApiInput() {
        requestParams = new HashMap<String, String>();
    }

    @Override
    public String toString() {
        return requestParams.toString();
    }

    public Map<String, String> getParams() {

        return requestParams;
    }

    public void setValueInMap(final String key, final String value) {

        requestParams.put(key, value);

    }

}
