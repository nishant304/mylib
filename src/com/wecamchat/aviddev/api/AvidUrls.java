package com.wecamchat.aviddev.api;

public interface AvidUrls {

    String BASE_URL = "http://104.237.131.161:3001";

    // me
    String LOGIN = "/me/login";
    String MEDIA_DELETE = "/me/media/delete";
    String MEDIA_MOVE = "/me/media/move";
    String PIC_UPLOAD = "/me/pic/upload";
    String PROFILE = "/me/profile";
    String PROFILE_EDIT = "/me/profile/edit";
    String REGISTER = "/me/register";
    String VIDEO_UPLOAD = "/me/video/upload";

    String HOT = "/me/hot";

    // logout
    String LOGOUT = "/logout";

    // claim code

    String CLAIMCODEUSE = "/claimCode/use";

    // sms verification

    String VERIFY = "/me/verify";
    
    // user
    
    String RECOVER = "/user/recover";
}
