package com.wecamchat.aviddev.api.io;

import java.lang.reflect.Type;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.wecamchat.aviddev.api.ApiOutput;
import com.wecamchat.aviddev.model.bo.Profile;

public class ProfileMediaMoveOutput extends ApiOutput {

    private int statusCode;
    private int result;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    @Override
    public boolean createFromJson(JSONObject jsonObject) {

        Gson gson = new Gson();

        Type userProfileListType = new TypeToken<ArrayList<Profile>>() {
        }.getType();

        try {
            statusCode = jsonObject.getInt("statusCode");
        } catch (JSONException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        try {
            result = gson.fromJson(jsonObject.getJSONObject("result")
                    .toString(), userProfileListType);
        } catch (JsonSyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
        return false;
    }

}
