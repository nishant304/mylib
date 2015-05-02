package com.wecamchat.aviddev.api.io;

import com.wecamchat.aviddev.api.ApiInput;

public class ProfileMediaMoveInput extends ApiInput {

    private String key;
    private String position;

    public String getKey() {
        return key;
    }

    public String getPosition() {
        return position;
    }

    public void setKey(String key) {
        this.key = key;

    }

    public void setPosition(String position) {
        this.position = position;
    }

    public ProfileMediaMoveInput() {
        super();
    }
}
