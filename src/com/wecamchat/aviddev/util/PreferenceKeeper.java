package com.wecamchat.aviddev.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Location;
import android.location.LocationManager;
import android.preference.PreferenceManager;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.wecamchat.aviddev.R;
import com.wecamchat.aviddev.constant.AppConstant;
import com.wecamchat.aviddev.constant.AppConstant.PreferenceKeeperNames;
import com.wecamchat.aviddev.constant.AppConstant.PrefrenceData;
import com.wecamchat.aviddev.constant.AppConstant.RangebarThumbs;
import com.wecamchat.aviddev.model.bo.MetaData;
import com.wecamchat.aviddev.model.bo.Profile;
import com.wecamchat.aviddev.model.bo.User;

public class PreferenceKeeper {

    private SharedPreferences prefs;
    private static PreferenceKeeper keeper;
    Context context;

    public static PreferenceKeeper getInstance(Context context) {
        if (keeper == null) {
            keeper = new PreferenceKeeper(context);
        }
        return keeper;
    }

    private PreferenceKeeper(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        this.context = context;
    }

    public static void saveCookie(String cookie, Context context) {
        SharedPreferences pref = PreferenceManager
                .getDefaultSharedPreferences(context);
        pref.edit().putString("cookie", cookie).commit();
    }

    public static String getCookie(Context context) {
        SharedPreferences pref = PreferenceManager
                .getDefaultSharedPreferences(context);
        return pref.getString("cookie", "");
    }

    public void setHandPrefrence(boolean handPrefs) {
        prefs.edit()
                .putBoolean(AppConstant.PreferenceKeeperNames.HAND_PREFS,
                        handPrefs).commit();
    }

    public void saveProfile(Context context, Profile profile){
        SharedPreferences pref = PreferenceManager
                .getDefaultSharedPreferences(context);
        pref.edit().putString("profile", AppUtil.getJson(profile)).commit();
    }

    public static Profile getProfile(Context context) {
        SharedPreferences pref = PreferenceManager
                .getDefaultSharedPreferences(context);
        return AppUtil.parseJson(pref.getString("profile", "{}"), Profile.class);
    }

    public boolean getHandPrefrence() {
        return prefs.getBoolean(AppConstant.PreferenceKeeperNames.HAND_PREFS,
                false);
    }

    public void setDistancePrefrence(boolean handPrefs) {
        prefs.edit()
                .putBoolean(AppConstant.PreferenceKeeperNames.DISTANCE_PREFS,
                        handPrefs).commit();
    }

    public boolean getDistancePrefrence() {
        return prefs.getBoolean(
                AppConstant.PreferenceKeeperNames.DISTANCE_PREFS, false);
    }

    /**************************************************************************/

    public void setFilterUpFor(String string) {
        prefs.edit()
                .putString(AppConstant.PreferenceKeeperNames.FILTER_UP_FOR,
                        string).commit();
    }

    public String getFilterUpFor() {
        return prefs.getString(AppConstant.PreferenceKeeperNames.FILTER_UP_FOR,
                context.getResources().getStringArray(

                R.array.filter_up_for_array)[0]);

    }

    public void setFilterOrientation(String string) {
        prefs.edit()
                .putString(
                        AppConstant.PreferenceKeeperNames.FILTER_ORIENTATION,
                        string).commit();
    }

    public String getFilterOrientation() {
        return prefs.getString(
                AppConstant.PreferenceKeeperNames.FILTER_ORIENTATION, context
                        .getResources().getStringArray(

                        R.array.filter_orientation_array)[0]);
    }

    public void setFilterBodyType(String string) {
        prefs.edit()
                .putString(AppConstant.PreferenceKeeperNames.FILTER_BODY_TYPE,
                        string).commit();
    }

    public String getFilterBodyType() {
        return prefs.getString(
                AppConstant.PreferenceKeeperNames.FILTER_BODY_TYPE, context
                        .getResources().getStringArray(

                        R.array.filter_body_type_array)[0]);

    }

    public void setFilterTemperament(String string) {
        prefs.edit()
                .putString(
                        AppConstant.PreferenceKeeperNames.FILTER_TEMPERAMENT,
                        string).commit();
    }

    public String getFilterTemperament() {
        return prefs.getString(
                AppConstant.PreferenceKeeperNames.FILTER_TEMPERAMENT, context
                        .getResources().getStringArray(

                        R.array.filter_temperament_array)[0]);
    }

    public void setFilterSize(String string) {
        prefs.edit()
                .putString(AppConstant.PreferenceKeeperNames.FILTER_SIZE,
                        string).commit();
    }

    public String getFilterSize() {
        return prefs.getString(AppConstant.PreferenceKeeperNames.FILTER_SIZE,
                context.getResources()
                        .getStringArray(R.array.filter_size_array)[0]);
    }

    public void setFilterHivStatus(String string) {
        prefs.edit()
                .putString(AppConstant.PreferenceKeeperNames.FILTER_HIV_STATUS,
                        string).commit();
    }

    public String getFilterHivStatus() {
        return prefs.getString(
                AppConstant.PreferenceKeeperNames.FILTER_HIV_STATUS,
                context.getResources().getStringArray(
                        R.array.filter_hiv_status_array)[0]);
    }

    public void setFilterHeight(String string) {
        prefs.edit()
                .putString(AppConstant.PreferenceKeeperNames.FILTER_HEIGHT,
                        string).commit();
    }

    public String getFilterHeight() {
        return prefs.getString(
                AppConstant.PreferenceKeeperNames.FILTER_HEIGHT,
                context.getResources().getStringArray(
                        R.array.filter_height_array)[0]);
    }

    public void setFilterDrink(String string) {
        prefs.edit()
                .putString(AppConstant.PreferenceKeeperNames.FILTER_DRINK,
                        string).commit();
    }

    public String getFilterDrink() {
        return prefs.getString(
                AppConstant.PreferenceKeeperNames.FILTER_DRINK,
                context.getResources().getStringArray(
                        R.array.filter_drink_array)[0]);
    }

    public void setFilterEyeColor(String string) {
        prefs.edit()
                .putString(AppConstant.PreferenceKeeperNames.FILTER_EYE_COLOR,
                        string).commit();
    }

    public String getFilterEyeColor() {
        return prefs.getString(
                AppConstant.PreferenceKeeperNames.FILTER_EYE_COLOR,
                context.getResources().getStringArray(
                        R.array.filter_eye_color_array)[0]);
    }

    public void setFilterRole(String string) {
        prefs.edit()
                .putString(AppConstant.PreferenceKeeperNames.FILTER_ROLE,
                        string).commit();
    }

    public String getFilterRole() {
        return prefs.getString(AppConstant.PreferenceKeeperNames.FILTER_ROLE,
                context.getResources()
                        .getStringArray(R.array.filter_role_array)[0]);
    }

    public void setFilterPersona(String string) {
        prefs.edit()
                .putString(AppConstant.PreferenceKeeperNames.FILTER_PERSONA,
                        string).commit();
    }

    public String getFilterPersona() {
        return prefs.getString(
                AppConstant.PreferenceKeeperNames.FILTER_PERSONA,
                context.getResources().getStringArray(
                        R.array.filter_persona_array)[0]);
    }

    public void setFilterBodyHair(String string) {
        prefs.edit()
                .putString(AppConstant.PreferenceKeeperNames.FILTER_BODY_HAIR,
                        string).commit();
    }

    public String getFilterBodyHair() {
        return prefs.getString(
                AppConstant.PreferenceKeeperNames.FILTER_BODY_HAIR,
                context.getResources().getStringArray(
                        R.array.filter_body_hair_array)[0]);
    }

    public void setFilterCut(String string) {
        prefs.edit()
                .putString(AppConstant.PreferenceKeeperNames.FILTER_CUT, string)
                .commit();
    }

    public String getFilterCut() {
        return prefs
                .getString(
                        AppConstant.PreferenceKeeperNames.FILTER_CUT,
                        context.getResources().getStringArray(
                                R.array.filter_cut_array)[0]);
    }

    public void setFilterEthnicity(String string) {
        prefs.edit()
                .putString(AppConstant.PreferenceKeeperNames.FILTER_ETHNICITY,
                        string).commit();
    }

    public String getFilterEthnicity() {
        return prefs.getString(
                AppConstant.PreferenceKeeperNames.FILTER_ETHNICITY, context
                        .getResources().getStringArray(

                        R.array.filter_ethnicity_array)[0]);
    }

    public void setFilterOutTo(String string) {
        prefs.edit()
                .putString(AppConstant.PreferenceKeeperNames.FILTER_OUT_TO,
                        string).commit();
    }

    public String getFilterOutTo() {
        return prefs.getString(AppConstant.PreferenceKeeperNames.FILTER_OUT_TO,
                context.getResources().getStringArray(

                R.array.filter_out_to_array)[0]);
    }

    public void setFilterSmoke(String string) {
        prefs.edit()
                .putString(AppConstant.PreferenceKeeperNames.FILTER_SMOKE,
                        string).commit();
    }

    public String getFilterSmoke() {
        return prefs.getString(
                AppConstant.PreferenceKeeperNames.FILTER_SMOKE,
                context.getResources().getStringArray(
                        R.array.filter_smoke_array)[0]);
    }

    public void setFilterHairColor(String string) {
        prefs.edit()
                .putString(AppConstant.PreferenceKeeperNames.FILTER_HAIR_COLOR,
                        string).commit();
    }

    public String getFilterHairColor() {
        return prefs.getString(
                AppConstant.PreferenceKeeperNames.FILTER_HAIR_COLOR, context
                        .getResources().getStringArray(

                        R.array.filter_hair_color_array)[0]);
    }

    public void setFilterRange(int value) {
        prefs.edit()
                .putInt(AppConstant.PreferenceKeeperNames.FILTER_RANGE, value)
                .commit();
    }

    public int getFilterRange() {
        return prefs.getInt(AppConstant.PreferenceKeeperNames.FILTER_RANGE,
                RangebarThumbs.RANGE_INDEX);
    }

    public void setFilterAgeLeftThumb(int value) {
        prefs.edit()
                .putInt(AppConstant.PreferenceKeeperNames.FILTER_AGE_LEFT_THUMB,
                        value).commit();
    }

    public int getFilterAgeLeftThumb() {
        return prefs.getInt(
                AppConstant.PreferenceKeeperNames.FILTER_AGE_LEFT_THUMB,
                RangebarThumbs.AGE_LEFT_INDEX);
    }

    public void setFilterAgeRightThumb(int value) {
        prefs.edit()
                .putInt(AppConstant.PreferenceKeeperNames.FILTER_AGE_RIGHT_THUMB,
                        value).commit();
    }

    public int getFilterAgeRightThumb() {
        return prefs.getInt(
                AppConstant.PreferenceKeeperNames.FILTER_AGE_RIGHT_THUMB,
                RangebarThumbs.AGE_RIGHT_INDEX);
    }

    public void setFilterHideInactiveUsers(boolean value) {
        prefs.edit()
                .putBoolean(
                        AppConstant.PreferenceKeeperNames.FILTER_HIDE_INACTIVE_UERS,
                        value).commit();
    }

    public boolean isFilterHideInactiveUsers() {
        return prefs.getBoolean(
                AppConstant.PreferenceKeeperNames.FILTER_HIDE_INACTIVE_UERS,
                false);
    }

    public void setFilterHideLocations(boolean value) {
        prefs.edit()
                .putBoolean(
                        AppConstant.PreferenceKeeperNames.FILTER_HIDE_LOCATIONS,
                        value).commit();
    }

    public boolean isFilterHideLocations() {
        return prefs.getBoolean(
                AppConstant.PreferenceKeeperNames.FILTER_HIDE_LOCATIONS, false);
    }

    public boolean isAppUsed() {
        return prefs.getBoolean(AppConstant.PreferenceKeeperNames.IS_APP_USED,
                false);

    }

    public void setAppUsed(final boolean flag) {
        prefs.edit()
                .putBoolean(AppConstant.PreferenceKeeperNames.IS_APP_USED, flag)
                .commit();
    }

    public void setPasscodeInSettings(boolean flag) {
        prefs.edit()
                .putBoolean(
                        AppConstant.PreferenceKeeperNames.IS_PASSCODE_ENABLED,
                        flag).commit();
    }

    public void setPasscode(String passcode) {
        prefs.edit()
                .putString(AppConstant.PreferenceKeeperNames.PASSCODE, passcode)
                .commit();
    }

    public String getPasscode() {
        return prefs
                .getString(AppConstant.PreferenceKeeperNames.PASSCODE, null);
    }

    public boolean isPasscodeEnabled() {
        return prefs.getBoolean(
                AppConstant.PreferenceKeeperNames.IS_PASSCODE_ENABLED, false);
    }

    /*
     * set Cookie from upload multipart image of profile
     */

    public String getProfileCookieKey() {

        return prefs.getString(
                AppConstant.PreferenceKeeperNames.PROFILE_COOKIE_KEY, "");

    }

    public void setProfileCookieKey(String cookieKey) {

        prefs.edit()
                .putString(
                        AppConstant.PreferenceKeeperNames.PROFILE_COOKIE_KEY,
                        cookieKey).commit();

    }

    public String getProfileCookieValue() {

        return prefs.getString(
                AppConstant.PreferenceKeeperNames.PROFILE_COOKIE_VALUE, "");

    }

    public void setProfileCookieValue(String cookieValue) {

        prefs.edit()
                .putString(
                        AppConstant.PreferenceKeeperNames.PROFILE_COOKIE_VALUE,
                        cookieValue).commit();

    }

    public void setUserName(String name) {
        prefs.edit().putString(AppConstant.PrefrenceData.Profile.NAME, name)
                .commit();
    }

    public String getUserName() {
        return prefs.getString(AppConstant.PrefrenceData.Profile.NAME, null);
    }

    public LatLng getmZoomLevelLAtLng() {
        double lat = Double.parseDouble(prefs.getString(
                AppConstant.PrefrenceData.ZOOM_LEVEL_LAT, "37.0000"));
        double lng = Double.parseDouble(prefs.getString(
                AppConstant.PrefrenceData.ZOOM_LEVEL_LNG, "-120.0000"));

        return new LatLng(lat, lng);

    }

    public void setmZoomLevelLatLng(String mLat, String mLng) {

        Editor editor = prefs.edit();
        editor.putString(AppConstant.PrefrenceData.ZOOM_LEVEL_LAT, mLat);
        editor.putString(AppConstant.PrefrenceData.ZOOM_LEVEL_LNG, mLng);

        editor.commit();

    }

    public float getmZoomLevel() {

        return prefs.getFloat(AppConstant.PrefrenceData.ZOOM_LEVEL, 0);
    }

    public void setmZoomLevel(float mZoomLevel) {
        Editor editor = prefs.edit();
        editor.putFloat(AppConstant.PrefrenceData.ZOOM_LEVEL, mZoomLevel);
        editor.commit();
    }

    public boolean isProfileActivate() {

        return prefs.getBoolean(
                AppConstant.PreferenceKeeperNames.PROFILE_IS_ACTIVE, false);
    }

    public void setProfileActivate(boolean isActiveProfile) {

        prefs.edit()
                .putBoolean(
                        AppConstant.PreferenceKeeperNames.PROFILE_IS_ACTIVE,
                        isActiveProfile).commit();
    }

    public void setStatus(boolean status) {

        prefs.edit()
                .putBoolean(AppConstant.PreferenceKeeperNames.HOT_UNHOT_STATUS,
                        status).commit();
    }

    public boolean isHotEnabled() {

        return prefs.getBoolean(
                AppConstant.PreferenceKeeperNames.HOT_UNHOT_STATUS, false);
    }

    public void saveUserData(Profile profile) {
        Editor editor = prefs.edit();
        editor.putString(AppConstant.PrefrenceData.Profile.ID, profile.get_id());
        editor.putString(AppConstant.PrefrenceData.Profile.NAME,
                profile.getName());
        editor.putString(AppConstant.PrefrenceData.Profile.PRIMARYURL, profile
                .getPics().get(0).getUrl());
        editor.putLong(AppConstant.PrefrenceData.Profile.LAST_SEEN,
                profile.getLastSeen());
        editor.putString(AppConstant.PrefrenceData.Profile.EMAIL,
                profile.getEmail());
        setUserMetaData(profile.getMetaData());
        editor.commit();
    }

    public void setUserMetaData(MetaData metaData) {
        prefs.edit()
                .putString(PreferenceKeeperNames.USER_META_DATA,
                        new Gson().toJson(metaData).toString()).commit();
    }

    public MetaData getUserMetaData() {
        MetaData metaData = null;

        String metaDataString = prefs.getString(
                PreferenceKeeperNames.USER_META_DATA, null);
        Gson gson = new Gson();

        metaData = gson.fromJson(metaDataString, MetaData.class);

        return metaData;
    }

    public User getUserData() {

        User user = new User();

        // user.setuId(prefs.getString(IAppConstant.PrefrenceData.Profile.ID,
        // "0"));

        // profile.setEmail(prefs.getString(
        // IAppConstant.PrefrenceData.Profile.EMAIL, null));

        user.setLastActiveTime(prefs.getString(
                AppConstant.PrefrenceData.Profile.LAST_SEEN, null));

        user.setuName(prefs.getString(AppConstant.PrefrenceData.Profile.NAME,
                null));

        user.setuImg(prefs.getString(
                AppConstant.PrefrenceData.Profile.PRIMARYURL, ""));

        user.setMetaData(getUserMetaData());

        // user.setAge();

        return user;

    }

    public boolean isAccountVerified() {
        return prefs.getBoolean(
                AppConstant.PreferenceKeeperNames.IS_ACCOUNT_VERIFIED, false);

    }

    public void setAccountVerified(final boolean flag) {
        prefs.edit()
                .putBoolean(
                        AppConstant.PreferenceKeeperNames.IS_ACCOUNT_VERIFIED,
                        flag).commit();
    }

    /*
     * Set Image key when registration is successfully
     */

    public String getProfileRegistrationImageKey() {

        return prefs
                .getString(
                        AppConstant.PreferenceKeeperNames.PROFILE_REGISTRATION_IMAGE_URL,
                        "");

    }

    public void setProfileRegistrationImageUrl(String url) {
        prefs.edit()
                .putString(
                        AppConstant.PreferenceKeeperNames.PROFILE_REGISTRATION_IMAGE_URL,
                        url).commit();

    }

    public void setUserLocation(Location location) {
        prefs.edit()
                .putString(PrefrenceData.LOCATION_LATITUDE,
                        Double.toString(location.getLatitude())).commit();
        prefs.edit()
                .putString(PrefrenceData.LOCATION_LONGITUDE,
                        Double.toString(location.getLongitude())).commit();
    }

    public Location getUserLocationInLatLng() {
        String latInString = prefs.getString(PrefrenceData.LOCATION_LATITUDE,
                "0.0");
        String longInString = prefs.getString(PrefrenceData.LOCATION_LONGITUDE,
                "0.0");
        Double latInDouble = Double.parseDouble(latInString);
        Double lngInDouble = Double.parseDouble(longInString);

        Location lastCurrentLoc = new Location(LocationManager.NETWORK_PROVIDER);
        lastCurrentLoc.setLatitude(latInDouble);
        lastCurrentLoc.setLongitude(lngInDouble);
        return lastCurrentLoc;
    }

    public Double getUserLocationLatitude() {
        String latInString = prefs.getString(PrefrenceData.LOCATION_LATITUDE,
                "0.0");
        Double latInDouble = Double.parseDouble(latInString);
        return latInDouble;
    }

    public Double getUserLocationLongitude() {
        String longInString = prefs.getString(PrefrenceData.LOCATION_LONGITUDE,
                "0.0");
        Double longInDouble = Double.parseDouble(longInString);
        return longInDouble;
    }

    public void setUserDOB(long dob) {
        prefs.edit().putLong(AppConstant.PrefrenceData.Profile.DOB, dob)
                .commit();
    }

    public long getUserDOB() {
        return prefs.getLong(AppConstant.PrefrenceData.Profile.DOB, 0);
    }
}
