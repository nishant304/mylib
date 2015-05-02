package com.wecamchat.aviddev.api.io;

import org.json.JSONException;
import org.json.JSONObject;

import com.wecamchat.aviddev.api.ApiOutput;

public class MediaDeleteOutput extends ApiOutput {

    private String statusCode;
    private boolean result;

    /**
     * @return the statusCode
     */
    public String getStatusCode() {
        return statusCode;
    }

    /**
     * @param statusCode
     *            the statusCode to set
     */
    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    /**
     * @return the result
     */
    public boolean isResult() {
        return result;
    }

    /**
     * @param result
     *            the result to set
     */
    public void setResult(boolean result) {
        this.result = result;
    }

    @Override
    public boolean createFromJson(JSONObject jsonObject) {
        try {
            setStatusCode(jsonObject.getString("statusCode"));
            // setResult(jsonObject.getString("result"));
        } catch (JSONException e) {

            e.printStackTrace();
            return false;
        }

        return true;
    }

}
