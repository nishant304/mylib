package com.wecamchat.aviddev.api.io;

import org.json.JSONException;
import org.json.JSONObject;

import com.wecamchat.aviddev.api.ApiOutput;

public class ProfileEditOutput extends ApiOutput {

    private int statusCode;
    private int result;

    /**
     * @return the statusCode
     */
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * @param statusCode
     *            the statusCode to set
     */
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public boolean createFromJson(JSONObject jsonObject) {

        try {
            statusCode = jsonObject.getInt("statusCode");
            result = jsonObject.getInt("result");
        } catch (JSONException e1) {
            e1.printStackTrace();
        }

        return true;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

}
