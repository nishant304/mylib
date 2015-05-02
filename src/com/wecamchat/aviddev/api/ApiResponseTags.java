package com.wecamchat.aviddev.api;

/**
 * The purpose of class is to provide response tags for api
 */
public class ApiResponseTags {

    public interface API_CALL_STATUS_TAGS {
        String STATUS = "st";
        String STATUS_SUCCESS = "1";
        String STATUS_FAILURE = "2";
        String STATUS_LOGOUT = "3";
        String RESULT = "rs";
    }

    public interface API_ERROR_TAGS {
        String ERROR = "er";
        String CODE = "code";
        String MESSAGE = "message";
    }

    public interface LOGIN_API {
        String ID = "uId";
        String NAME = "nm";
        String STATUS_TEXT = "bio";
        String IMAGE_ID = "iid";
        String ONLINE_STATUS = "ws";
        String GENDER = "sx";
        String AGE = "age";
        String INTEREST = "inst";
        String RELATIONSHIP_STATUS = "rels";
        String LOCATION = "loc";
        String FRIEND_SYNC_TIME_STAMP = "fsts";
    }

    public interface UPDATE_NOTIFICATION_API {
        String ENABLED = "enbld";
        String PLATEFORM = "pt";
        String DEVICEID = "dId";

    }

    public interface TRENDING_TAGS_API {
        String HOTTEST = "hottest";
        String NEARME = "nearme";
    }
}
