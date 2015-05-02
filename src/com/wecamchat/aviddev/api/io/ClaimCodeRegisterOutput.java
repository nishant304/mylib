package com.wecamchat.aviddev.api.io;

import org.json.JSONObject;

import com.wecamchat.aviddev.api.ApiOutput;

public class ClaimCodeRegisterOutput extends ApiOutput {

    private String statusCode;
    private String result;

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
    public String getResult() {
        return result;
    }

    /**
     * @param result
     *            the result to set
     */
    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public boolean createFromJson(JSONObject jsonObject) {

        try {
            setResult(jsonObject.getString("result"));
            setStatusCode(jsonObject.getString("statusCode"));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

}
