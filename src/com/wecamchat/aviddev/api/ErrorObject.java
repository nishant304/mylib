package com.wecamchat.aviddev.api;

import org.json.JSONException;
import org.json.JSONObject;

import com.wecamchat.aviddev.constant.AppConstant;

/**
 * The purpose of class is to contain error when api failure occurs
 */

public class ErrorObject extends ApiOutput {

    private String errorCode;
    private String errorMessage;

    public ErrorObject(String errorCode, String errorMessage) {
        super();
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public ErrorObject() {
        this(AppConstant.IErrorCode.defaultErrorCode,
                AppConstant.IErrorMessage.defaultErrorMessage);
    }

    /**
     * @return the errorCode
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * @return the errorMessage
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * @param error
     *            message
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * @param errorCode
     *            the errorCode to set
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public boolean createFromJson(JSONObject jsonObject) {

        try {
            JSONObject resultJsonObject = jsonObject.getJSONObject("result");
            setErrorCode(resultJsonObject.getString("errorCode"));
            setErrorMessage(resultJsonObject.getString("errorMessage"));

            return true;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return false;
    }

}
