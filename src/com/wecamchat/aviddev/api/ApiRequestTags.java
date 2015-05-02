package com.wecamchat.aviddev.api;

/**
 * The purpose of class is to provide request tags for api
 */
public class ApiRequestTags {

    public interface LOGIN_API {
        String USERNAME = "username";
        String PASSWORD = "password";
    }

    public interface PROFILE_API {
        String USER_IDS = "uIds";
        String USER_ID = "uId";
        String TAGS = "tags";
        String FRIENDS = "frnds";
    }

    public interface UPDATE_BIO_API {
        String BIO = "bio";
        String USER_ID = "uId";
        String LOC = "loc";// : <string><home location>
        String NAME = "nm";// : <string><name>
        String SEX = "sx";// :<Male/Female>
        String INTERESTED_IN = "inst";// : <String>
        String RELATIONSHIP_STATUS = "rels";// : <String>
        String DATE_OF_BIRTH = "dob";// :<long>
        String LAT = "lat";// :<string><Current location lat>
        String LNG = "lng";// :<string><Current location long>
        String EMAIL = "email";
    }

    public interface UPLOAD_IMAGE_API {
        String USER_ID = "uId";
        String FILE = "file";
    }

    public interface FRIENDS_API {
        String USER_ID = "uId";
        String NEXT = "nxt";
        String COUNT = "cnt";
        String SORT = "sort";
    }

    public interface UPDATE_NOTIFICATION_API {
        String ENABLED = "enabled";
        String PLATEFORM = "plateform";
        String DEVICEID = "deviceId";
        String DEVICETOKEN = "deviceToken";
        String USER_ID = "uId";
    }

    public interface SYNC_FRIENDS_API {
        String FSTS = "fsts";
        String USER_ID = "uId";
    }

    public interface UPDATE_WC_STATUS_API {
        String WC_STATUS = "wcStatus";
        String USER_ID = "uId";
    }

    public interface MAKE_CALL_API {
        String USER_ID = "uId";
        String INTRODUCE_TEXT = "iText";
        String DEVICE_ID = "dId";
        String USER_FRIEND = "uf";
        String TO_USER_ID = "toUid";
        String FROM_UNAME = "fromUName";
        String TO_UNAME = "toUName";
        String MSG = "msg";

    }

    public interface CALL_DETAIL_API {
        String FROM_USER_ID = "fromUid";
        String DETAIL = "dtl";
        String CALL_HISTORY_ID = "chId";
        String USER_ID = "uId";

        String CALL_HISTORY_THREAD_ID = "chtId";
    }

    public interface ACCEPTED_CALL_API {
        String USER_ID = "uId";
        String SESSION_ID = "sId";
        String DEVICEID = "dId";
        String CALL_HISTORY_ID = "chId";
        String FROM_USER_ID = "fromUid";

    }

    public interface IMAGE_CHAT_USER {
        String APP_VERSION = "appVer";
        String TOKEN = "token";
        String UID = "uId";
        String PROFILE_ID = "profileId";
    }

    public interface REJECTED_CALL_API {
        String FROM_USER_ID = "fromUid";
        String TO_USER_ID = "toUid";
        String SESSION_ID = "sId";
        String CALL_HISTORY_ID = "chId";
        String USER_ID = "uId";
        String DEVICEID = "dId";
    }

    public interface ENDED_CALL_API {
        String USER_ID = "uId";
        String SESSION_ID = "sId";
        String CALL_HISTORY_ID = "chId";
        String ENDED_USER_ID = "endUid";
    }

    public interface LOGOUT_API {
        String DEVICE_ID = "deviceId";
        String USER_ID = "uId";
    }

    public interface USER_TOKEN {
        String TOKEN = "token";
    }

    public interface SEARCH_FILTER_API {
        String USER_ID = "uId";
        String NEXT = "nxt";
        String COUNT = "cnt";
        String STYPE = "stype";
        String SRT = "srt";
        String TEXT = "text";
        String AGE_FROM = "ageFrom";
        String AGE_TO = "ageTo";
        String GENDER = "gndr";
        String INTERESTED_IN = "instrd";
        String RELATIONSHIP = "relst";
        String LOCATION = "lc";
        String TAGS = "tags";
        String PROFILE_ID = "pUid";
        String FRIEND_OF_FRIEND = "fof";

    }

    public interface GET_NOTY_UPDATES_API {

        String LST = "lst";
        String TOKEN = "token";
        String USER_ID = "uId";
        String NEXT = "nxt";
        String COUNT = "cnt";
    }

    public interface INVITE_FRIENDS_API {
        String USER_ID = "uId";
        String NEXT = "nxt";
        String COUNT = "cnt";
        String FRESH_LST = "freshLst";
    }

    public interface CONNECT_SN_API {
        String TOKEN = "token";// <token>
        String UID = "uId";// : <user id >
        String SNTYPE = "snType";// : <social network key> <0-fb, 1-gplus,
                                 // 2-twitter>
        String USERNAME = "username";// : <social network access token>
        String PASSWORD = "password";// : <social network access token_secret>
    }

    public interface ADD_FRIEND_API {
        String USER_ID = "uId";
        String TO_USER_ID = "toUid";
        String SN_ID = "snId";
        String SN_TYPE = "snType";
    }

    public interface UPDATE_FRND_REQUEST_API {
        String USER_ID = "uId";
        String ACT_ID = "actId";
        String REQUEST_ID = "requestId";
    }

    public interface POST_STATUS_API {
        String TOKEN = "token";
        String USER_ID = "uId";
        String MSG = "msg";
        String SN_TYPES = "snTypes";
    }

    public interface DISCONNECT_SN {
        String TOKEN = "token";
        String USER_ID = "uId";
        String SN_TYPE = "snType";
    }

    public interface MATCHES_API {
        String TOKEN = "token";
        String USER_ID = "uId";
        String NEXT = "nxt";
        String COUNT = "cnt";
    }

    public interface FRIENDS_OF_FIRENDS_API {
        String USER_ID = "uId";
        String PROFILE_USER_ID = "pUId";
        String NEXT = "nxt";
        String COUNT = "cnt";
    }

    public interface ADD_TAG_API {
        String TOKEN = "token";
        String USER_ID = "uId";
        String TAG = "tag";
    }

    public interface GET_TAGS_API {
        String TOKEN = "token";
        String USER_ID = "uId";
        String PROFILE_ID = "uPid";
    }

    public interface SEARCH_TAGS_API {
        String USER_ID = "uId";
        String SEARCH_STRING = "str";
    }

    public interface REMOVE_TAGS_API {
        String TOKEN = "token";// : <token>
        String USER_ID = "uId";// : <user id >
        String TAG = "tag";// :<String> to remove
    }

    public interface TRENDING_TAGS_API {
        String TOKEN = "token";// : <token>
        String USER_ID = "uId";// : <user id >
    }

    public interface NEAR_ME_API {
        String USER_ID = "uId";
        String NEXT = "nxt";
        String COUNT = "cnt";
        String LATITUDE = "lat";
        String LONGITUDE = "lng";
        String IS_NEAR_ME = "isNearMe";
        String MIN_LAT = "minLat";
        String MIN_LNG = "minLng";
        String MAX_LAT = "maxLat";
        String MAX_LNG = "maxLng";
        String NEAR_ME_MODE = "nearmeMode";
    }

    public interface SHARE_LOCATION_API {
        String TOKEN = "token";// : <token>
        String USER_ID = "uId";// : <user id >
        String SHARE_LOC = "shareLoc";
        String LATITUDE = "lat";
        String LONGITUDE = "lng";
    }

    public interface REPORT_USER_API {
        String USER_ID = "uId";
        String TO_USER_ID = "toUid";
        String DESCRIPTION = "desc";
    }

    public interface ADS_SUBSCRIPTION_API {
        String USER_ID = "uId";
        String ADS_STATUS = "adsSt";
    }

    public interface SERVER_HANDSHAKING_API {
        String TOKEN = "token";// : <token>
        String USER_ID = "uId";// : <user id >

    }

    public interface TERMS_AND_CONDITIONS_API {
        String USER_ID = "uId";
        String tc_status = "tcSt";
    }

    public interface REMOVE_FRIEND_API {
        String USER_ID = "uId";
        String FRIEND_ID = "pId";
        String ACTION = "act";
        String IS_FRIEND = "isFrnd";
    }

    public interface CLEARALL_NOTIFICATION_API {

        String USER_ID = "uId";
        String TOKEN = "token";
        String CLEAR_ALL = "clearAll";
    }

    public interface BULK_INVITE_FRIENDS_API {
        String USER_ID = "uId";
        String TOKEN = "token";
        String WECAM_TAG = "3";
        String FB_TAG = "0";
        String TWITTER_TAG = "2";
        String LINKEDIN_TAG = "4";
        String GPLUS_TAG = "1";
    }

    public interface CHANGE_PASSWORD_API {
        String USER_ID = "uId";
        String TOKEN = "token";
        String NEW_PASSWORD = "nwPwd";
        String OLD_PASSWORD = "oldPwd";
    }

    public interface HELP_VIDEO_API {
        String USER_ID = "uId";
        String UPDATES_ID = "updatesId";
        String STATUS = "st";
    }

    public interface CHAT_CONVERSATION_API {
        String USER_ID = "uId";
        String TOKEN = "token";
        String THREAD_ID = "threadId";
        String TO_UID = "toUId";
        String LAST_MSG_ID = "lst";
        String COUNT = "cnt";
    }

    public interface CHAT_RECENT_API {
        String USER_ID = "uId";
        String TOKEN = "token";
        String NEXT = "nxt";
        String LASTMSGTIME = "lstTm";
        String COUNT = "cnt";
        String AFTER_TIME = "afterTime";
    }

    public interface FORGOT_PASSWORD_API {
        String EMAIL = "email";
    }

    public interface SIGNUP_API {
        String USER_ID = "uId";
        String STEP = "step";
        String F_NAME = "fNm";
        String L_NAME = "lNm";
        String EMAIL = "email";
        String SEX = "sx";
        String DOB = "dob";
        String LOC = "loc";
        String RELS = "rels";
        String INST = "inst";
        String PWD = "pwd";
    }

    public interface SIGNUP_API_COMPLETE {
        String TOKEN = "token";
        String USER_ID = "uId";
        String STEP = "step";
        String ENABLED = "enabled";
        String PLATFORM = "plateform";
        String DEVICE_ID = "deviceId";
        String DEVICE_TOKEN = "deviceToken";
        String LANGUAGE = "lng";
    }

    public interface DEL_CONVERSATION_THREAD_API {
        String USER_ID = "uId";
        String THREAD_ID = "threadId";

        String ROOM_ID = "roomId";
    }

    public interface UNJOIN_CHAT_API {
        String USER_ID = "uId";
        String ROOM_ID = "roomId";
        String ST = "st";
    }

    public interface GET_ACTIVE_ROOMS {

        String USER_ID = "uId";
        String TO_USER_ID = "toId";
    }

    public interface CALL_LOG {
        String TOKEN = "token";
        String USER_ID = "uId";
        String APP_VERSION = "appVer";
        String CH_ID = "chId";
        String EVENT_STRING = "evntStr";
        String DTL = "dtl";
        String TYPE = "type";

    }

    public interface ACTIVE_CALL_REMINDER_API {
        String USER_ID = "uId";
        String CHAT_ID = "chId";
    }

    public interface FETCH_CHAT_HISTORY_API {
        String USER_ID = "uId";
        String CID = "cid";
        String PASSWORD = "pwd";
        String ROOM_ID = "roomId";
        String TIME = "time";
        String COUNT = "count";
        String AFTER_TIME = "afterTime";
    }

    public interface INVITE_EMAIL_CONTACT_API {
        String CONTACTS = "contacts";
    }

    public interface BG_APP_API {
        String DEVICE_ID = "deviceId";
        String USER_ID = "uId";
        String DEVICE_TOKEN = "deviceToken";
        String RECIEVE_PUSH = "recievePush";
        String PLATEFORM = "plateform";
    }
}
