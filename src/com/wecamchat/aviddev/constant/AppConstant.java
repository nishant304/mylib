package com.wecamchat.aviddev.constant;

import java.util.HashMap;
import java.util.Map;

import com.wecamchat.aviddev.R;

import android.app.Activity;
import android.net.Uri;

public class AppConstant {

    public static String CONTENT_PROVIDER_AUTHORITY_NAME() {
        return "com.wecamchat.aviddev.contentprovider";
    }

    public interface PreferenceKeeperNames {

        String HAND_PREFS = "hand_prefs";
        String IS_PASSCODE_ENABLED = "is_passcode_enabled";
        String PASSCODE = "passcode";

        String FILTER_UP_FOR = "upForFilter";
        String FILTER_ORIENTATION = "orientationFilter";
        String FILTER_BODY_TYPE = "bodyTypeFilter";
        String FILTER_TEMPERAMENT = "temperamentFilter";
        String FILTER_SIZE = "sizeFilter";
        String FILTER_HIV_STATUS = "hivStatusFilter";
        String FILTER_HEIGHT = "heightFilter";
        String FILTER_DRINK = "drinkFilter";
        String FILTER_EYE_COLOR = "eyeColorFilter";
        String FILTER_ROLE = "roleFilter";
        String FILTER_PERSONA = "personaFilter";
        String FILTER_BODY_HAIR = "bodyHairFilter";
        String FILTER_CUT = "cutFilter";
        String FILTER_ETHNICITY = "ethnicityFilter";
        String FILTER_OUT_TO = "outToFilter";
        String FILTER_SMOKE = "smokeFilter";
        String FILTER_HAIR_COLOR = "hairColorFilter";

        String FILTER_RANGE = "rangeFilter";
        String FILTER_AGE_LEFT_THUMB = "ageLeftThumbFilter";
        String FILTER_AGE_RIGHT_THUMB = "ageRightThumbFilter";
        String FILTER_HIDE_INACTIVE_UERS = "hideInactiveUsersFilter";
        String FILTER_HIDE_LOCATIONS = "hideLocationsFilter";

        String DISTANCE_PREFS = "distance_prefs";

        String IS_APP_USED = "isAppUsed";

        String PROFILE_COOKIE_VALUE = "profileCookieValue";
        String PROFILE_COOKIE_KEY = "profileCookieKey";

        String USER_NAME = "user_name";
        String PROFILE_IS_ACTIVE = "isActive";
        String HOT_UNHOT_STATUS = "hotUnhotStatus";
        String PROFILE_REGISTRATION_IMAGE_URL = "profileregistrationimage";

        String PROFILE_UPLOAD_OBJECT = "profileuploadobject";

        String PROFILE_TOTAL_UPLOAD = "profiletotalupload";

        String IS_ACCOUNT_VERIFIED = "isAccountVerified";

        String USER_META_DATA = "userMetaData";
    }

    public interface CameraCaptureConstants {
        String PROFILE_REGISTRATION_IMAGE_KEY = "profile_registration_image_key";

        String HOT_UNHOT_STATUS = "hot_unhot_status";

        int REQ_CODE_GALLERY_IMAGE_AND_VIDEO_PICK = 0;

        int CROP_RESULT_CODE = 1;
    }

    int REQ_CODE_CAMERA_IMAGE_PICK = 1;

    int REQ_CODE_CAMERA_VIDEO_PICK = 2;

    int REQ_CODE_GALLERY_IMAGE_AND_VIDEO_PICK = 3;

    int CROP_RESULT_CODE = 4;

    public interface RangebarThumbs {
        int AGE_LEFT_INDEX = 4;
        int AGE_RIGHT_INDEX = 37;

        int RANGE_INDEX = 100;
    }

    public static float MAP_SCREEN_ZOOM_LEVEL = 17.0f;

    public static float MAP_SCREEN_MIN_ZOOM_LEVEL = 14.0f;

    public static final int SDK_VERSION = android.os.Build.VERSION.SDK_INT;

    public static String hockeyId_Constant() {

        String appId = "b1a97b735146933087bc1d9d162509f9";

        return appId;

    }

    public interface DialogConstant {
        int ENABLE_LOCATION_SERVICES = 0;

    }

    public static final int LOCATION_SETTING_REQUEST_CODE = 1;

    public interface FooterConstant {
        public static final int SET_DEFAULT_FRAGMENT_INDEX = 4;
        public static final int FOOTER_OPTION_SIZE = 6;
        public static final int FOOTER_DEFAULT_PRE_POSITION = -2;

    }

    public static class PrefrenceData {
        public static final String MY_PREF_NAME = "MYPREFNAME";
        public static final String ZOOM_LEVEL_LAT = "ZOOMLEVELLAT";
        public static final String ZOOM_LEVEL_LNG = "ZOOMLEVELLNG";
        public static final String ZOOM_LEVEL = "ZOOMLEVEL";
        public static final String LOCATION_LATITUDE = null;
        public static final String LOCATION_LONGITUDE = null;

        public interface Profile {

            String NAME = "name";
            String PRIMARYURL = "primaryUrl";
            String ID = "id";
            String EMAIL = "email";
            String LAST_SEEN = "lastseen";
            String DOB = "dob";
        }
    }

    public static float CAMERA_IMAGE_SIZE = 160;

    public static int VIDEO_CAPTURE_LIMIT = 10 * 1000; // 10sec

    public final static String MEDIA_STORAGE_NAME = "AvidMedia";
    public final static int MAX_PROFILE_IMAGE = 5;

    public static class FragmentStackName {
        public static final String CONFIRM_FAVORITE_FRAGMENT = "CONFIRM_FAVORITE_FRAGMENT";
        public static final String CONFIRM_FLAME_TOGGLE_FRAGMENT = "CONFIRM_FLAME_TOGGLE_FRAGMENT";
        public static final String CONFIRM_IMAGE_DELETE_FRAGMENT = "CONFIRM_IMAGE_DELETE_FRAGMENT";
        public static final String CAMERA_OPEN_FRAGMENT = "CAMERA_OPEN_FRAGMENT";
        public static final String CREATE_PROFILE_FRAGMENT = "CREATE_PROFILE_FRAGMENT";
    }

    /**
     * This method is used to return map with header informations for api.
     * 
     * @return Map
     */
    public static Map<String, String> getHeaders() {

        Map<String, String> params = new HashMap<String, String>();
        // put content type here
        params.put("Content-Type", "application/x-www-form-urlencoded");
        return params;

    }

    public interface IErrorCode {

        String defaultErrorCode = "ER001";
        String notInternetConnErrorCode = "ER002";

    }

    public interface IErrorMessage {

        String defaultErrorMessage = "Some error occured, Please try again.";

        String notInternetConnectMessage = "Please check internet connection.";

    }

    public interface IAPI_STATUS_CODE {
        int SUCESS = 1;
        int FAILED = 0;
    }

    public interface LOCATION_CONSTANTS {

        long MINIMUM_TIME_INTERVAL = 0;
        float MINIMUM_DISTANCE = 500;

    }

    public interface Launcher {
        int DELAY = 1100;
    }

    public static String getVideoPath(final Activity activity, final int resourceId) {

        return "android.resource://" + activity.getPackageName() + "/"
                + resourceId;

    }

}
