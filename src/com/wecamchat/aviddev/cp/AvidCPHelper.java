package com.wecamchat.aviddev.cp;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.wecamchat.aviddev.db.BrowseContract;
import com.wecamchat.aviddev.db.MapContract;
import com.wecamchat.aviddev.db.MediaContentContract;
import com.wecamchat.aviddev.model.bo.MediaContent;
import com.wecamchat.aviddev.model.bo.MetaData;
import com.wecamchat.aviddev.model.bo.User;

public class AvidCPHelper {

    private static AvidCPHelper helper = null;

    private Context context;
    private ContentResolver resolver;

    public static class Uris {

        public final static Uri URI_MAP_USER = Uri.withAppendedPath(
                ContentProviderData.CONTENT_URI,
                MapContract.MapEntry.TABLE_NAME);

        public final static Uri URI_BROWSE_USER = Uri.withAppendedPath(
                ContentProviderData.CONTENT_URI,
                BrowseContract.BrowseEntry.TABLE_NAME);


        public final static Uri URI_MEDIA_CONTENT = Uri.withAppendedPath(
                ContentProviderData.CONTENT_URI,
                MediaContentContract.MediaContentContractEntry.TABLE_NAME);

    }

    public Cursor getCursorB(Uri uri, String[] projection, String selection,
            String[] selectionArgs, String sortOrder) {

        return resolver.query(uri, projection, selection, selectionArgs,
                sortOrder);

    }

    public static AvidCPHelper getInstance(Context context) {
        if (helper == null) {
            helper = new AvidCPHelper(context);
        }

        return helper;
    }

    private AvidCPHelper(Context context) {
        this.context = context;
        resolver = context.getContentResolver();
    }

    public Cursor getCursor(Uri uri, String[] projection, String selection,
            String[] selectionArgs, String sortOrder) {

        return resolver.query(uri, projection, selection, selectionArgs,
                sortOrder);

    

    
    }

    

    public boolean insertMapUserContentProviderData(List<User> mapUsers) {

        return bulkInsertMapUser(mapUsers);
    }

    @SuppressWarnings("boxing")
    private boolean bulkInsertMapUser(List<User> users) {
        ContentValues[] values = new ContentValues[users.size()];
        ContentValues value = null;
        User user = null;

        for (int index = 0; index < users.size(); index++) {
            value = new ContentValues();
            user = users.get(index);

            value.put(MapContract.MapEntry.COLUMN_NAME_AC_ST,
                    user.getActiveStatus());

            value.put(MapContract.MapEntry.COLUMN_NAME_AGE, user.getAge());

            value.put(MapContract.MapEntry.COLUMN_NAME_BODY_HAIR, user
                    .getMetaData().getBody_hair());

            value.put(MapContract.MapEntry.COLUMN_NAME_BODY_TYPE, user
                    .getMetaData().getBody_type());

            value.put(MapContract.MapEntry.COLUMN_NAME_CUT, user.getMetaData()
                    .getCut());

            value.put(MapContract.MapEntry.COLUMN_NAME_DISTANCE,
                    user.getDistance());

            value.put(MapContract.MapEntry.COLUMN_NAME_HIV_ST, user
                    .getMetaData().getHiv_status());

            value.put(MapContract.MapEntry.COLUMN_NAME_LAST_ACT_TIME,
                    user.getLastActiveTime());

            value.put(MapContract.MapEntry.COLUMN_NAME_LAT, user.getmLatitude());

            value.put(MapContract.MapEntry.COLUMN_NAME_LONG,
                    user.getmLongitude());

            value.put(MapContract.MapEntry.COLUMN_NAME_ORIENT, user
                    .getMetaData().getOrientation());

            value.put(MapContract.MapEntry.COLUMN_NAME_PERSONA, user
                    .getMetaData().getPersona());

            value.put(MapContract.MapEntry.COLUMN_NAME_PRIVATE_NOTE,
                    user.getPrivateNotes());

            value.put(MapContract.MapEntry.COLUMN_NAME_PROFILE_DESC,
                    user.getProfileDesc());

            value.put(MapContract.MapEntry.COLUMN_NAME_ROLE, user.getMetaData()
                    .getRole());

            value.put(MapContract.MapEntry.COLUMN_NAME_SIZE, user.getMetaData()
                    .getSize());

            value.put(MapContract.MapEntry.COLUMN_NAME_TEMPER, user
                    .getMetaData().getTemperament());

            value.put(MapContract.MapEntry.COLUMN_NAME_UID, user.getuId());

            value.put(MapContract.MapEntry.COLUMN_NAME_UIMG, user.getuImg());

            value.put(MapContract.MapEntry.COLUMN_NAME_UNAME, user.getuName());

            value.put(MapContract.MapEntry.COLUMN_NAME_UP_FOR, user
                    .getMetaData().getUp_for());

            value.put(MapContract.MapEntry.COLUMN_NAME_UTYPE,
                    user.getUserType());

            value.put(MapContract.MapEntry.COLUMN_NAME_WINK_CNT,
                    user.getWinkCount());

            value.put(MapContract.MapEntry.COLUMN_NAME_HEIGHT, user
                    .getMetaData().getHeight());

            value.put(MapContract.MapEntry.COLUMN_NAME_DRINK, user
                    .getMetaData().getDrink());

            value.put(MapContract.MapEntry.COLUMN_NAME_EYECOLOR, user
                    .getMetaData().getEye_color());

            value.put(MapContract.MapEntry.COLUMN_NAME_HAIRCOLOR, user
                    .getMetaData().getHair_color());

            value.put(MapContract.MapEntry.COLUMN_NAME_SMOKE, user
                    .getMetaData().getSmoke());

            value.put(MapContract.MapEntry.COLUMN_NAME_OUTTO, user
                    .getMetaData().getOut_to());

            value.put(MapContract.MapEntry.COLUMN_NAME_ETHNICITY, user
                    .getMetaData().getEthnicity());

            value.put(MapContract.MapEntry.COLUMN_NAME_IS_USER,
                    user.isUser() == true ? 1 : 0);

            values[index] = value;
        }

        int flag = resolver.bulkInsert(Uris.URI_MAP_USER, values);

        return flag >= 0 ? true : false;

    }

    public boolean insertBrowseUserContentProviderData(List<User> mapUsers) {

        return bulkInsertBrowseUser(mapUsers);
    }

    @SuppressWarnings("boxing")
    private boolean bulkInsertBrowseUser(List<User> users) {
        ContentValues[] values = new ContentValues[users.size()];
        ContentValues value = null;
        User user = null;

        for (int index = 0; index < users.size(); index++) {
            value = new ContentValues();
            user = users.get(index);

            value.put(MapContract.MapEntry.COLUMN_NAME_AC_ST,
                    user.getActiveStatus());

            value.put(MapContract.MapEntry.COLUMN_NAME_AGE, user.getAge());

            value.put(MapContract.MapEntry.COLUMN_NAME_BODY_HAIR, user
                    .getMetaData().getBody_hair());

            value.put(MapContract.MapEntry.COLUMN_NAME_BODY_TYPE, user
                    .getMetaData().getBody_type());

            value.put(MapContract.MapEntry.COLUMN_NAME_CUT, user.getMetaData()
                    .getCut());

            value.put(MapContract.MapEntry.COLUMN_NAME_DISTANCE,
                    user.getDistance());

            value.put(MapContract.MapEntry.COLUMN_NAME_HIV_ST, user
                    .getMetaData().getHiv_status());

            value.put(MapContract.MapEntry.COLUMN_NAME_LAST_ACT_TIME,
                    user.getLastActiveTime());

            value.put(MapContract.MapEntry.COLUMN_NAME_LAT, user.getmLatitude());

            value.put(MapContract.MapEntry.COLUMN_NAME_LONG,
                    user.getmLongitude());

            value.put(MapContract.MapEntry.COLUMN_NAME_ORIENT, user
                    .getMetaData().getOrientation());

            value.put(MapContract.MapEntry.COLUMN_NAME_PERSONA, user
                    .getMetaData().getPersona());

            value.put(MapContract.MapEntry.COLUMN_NAME_PRIVATE_NOTE,
                    user.getPrivateNotes());

            value.put(MapContract.MapEntry.COLUMN_NAME_PROFILE_DESC,
                    user.getProfileDesc());

            value.put(MapContract.MapEntry.COLUMN_NAME_ROLE, user.getMetaData()
                    .getRole());

            value.put(MapContract.MapEntry.COLUMN_NAME_SIZE, user.getMetaData()
                    .getSize());

            value.put(MapContract.MapEntry.COLUMN_NAME_TEMPER, user
                    .getMetaData().getTemperament());

            value.put(MapContract.MapEntry.COLUMN_NAME_UID, user.getuId());

            value.put(MapContract.MapEntry.COLUMN_NAME_UIMG, user.getuImg());

            value.put(MapContract.MapEntry.COLUMN_NAME_UNAME, user.getuName());

            value.put(MapContract.MapEntry.COLUMN_NAME_UP_FOR, user
                    .getMetaData().getUp_for());


            value.put(MapContract.MapEntry.COLUMN_NAME_UTYPE,
                    user.getUserType());

            value.put(MapContract.MapEntry.COLUMN_NAME_WINK_CNT,
                    user.getWinkCount());

            value.put(MapContract.MapEntry.COLUMN_NAME_HEIGHT, user
                    .getMetaData().getHeight());

            value.put(MapContract.MapEntry.COLUMN_NAME_DRINK, user
                    .getMetaData().getDrink());

            value.put(MapContract.MapEntry.COLUMN_NAME_EYECOLOR, user
                    .getMetaData().getEye_color());

            value.put(MapContract.MapEntry.COLUMN_NAME_HAIRCOLOR, user
                    .getMetaData().getHair_color());

            value.put(MapContract.MapEntry.COLUMN_NAME_SMOKE, user
                    .getMetaData().getSmoke());

            value.put(MapContract.MapEntry.COLUMN_NAME_OUTTO, user
                    .getMetaData().getOut_to());

            value.put(MapContract.MapEntry.COLUMN_NAME_ETHNICITY, user
                    .getMetaData().getEthnicity());


            values[index] = value;
        }
        int flag = resolver.bulkInsert(Uris.URI_BROWSE_USER, values);

        return flag >= 0 ? true : false;

    }

    public List<User> getMapUsersFromCursor(Cursor cursor) {
        List<User> users = new ArrayList<User>();


        if (cursor != null) {
            Log.i("Cursor", "Cursor Count " + cursor.getCount());
            User user = null;
            while (cursor.moveToNext()) {
                user = new User();
                user.setActiveStatus(cursor
                        .getInt(MapContract.MapEntry.COLUMN_POSITION_AC_ST));
                user.setAge(cursor
                        .getInt(MapContract.MapEntry.COLUMN_POSITION_AGE));
                user.setMetaData(new MetaData());
                user.getMetaData()
                        .setBody_hair(
                                cursor.getString(MapContract.MapEntry.COLUMN_POSITION_BODY_HAIR));
                user.getMetaData()
                        .setBody_type(
                                cursor.getString(MapContract.MapEntry.COLUMN_POSITION_BODY_TYPE));
                user.getMetaData()
                        .setCut(cursor
                                .getString(MapContract.MapEntry.COLUMN_POSITION_CUT));
                user.setDistance(cursor
                        .getString(MapContract.MapEntry.COLUMN_POSITION_DISTANCE));
                user.getMetaData()
                        .setHiv_status(
                                cursor.getString(MapContract.MapEntry.COLUMN_POSITION_HIV_ST));
                user.setLastActiveTime(cursor
                        .getString(MapContract.MapEntry.COLUMN_POSITION_LAST_ACT_TIME));
                // TODO set media content
                // user.setMediaContents(mediaContents);
                user.setmLatitude(cursor
                        .getDouble(MapContract.MapEntry.COLUMN_POSITION_LAT));
                user.setmLongitude(cursor
                        .getDouble(MapContract.MapEntry.COLUMN_POSITION_LONG));
                user.getMetaData()
                        .setOrientation(
                                cursor.getString(MapContract.MapEntry.COLUMN_POSITION_ORIENT));
                user.getMetaData()
                        .setPersona(
                                cursor.getString(MapContract.MapEntry.COLUMN_POSITION_PERSONA));
                user.setPrivateNotes(cursor
                        .getString(MapContract.MapEntry.COLUMN_POSITION_PRIVATE_NOTE));
                user.setProfileDesc(cursor
                        .getString(MapContract.MapEntry.COLUMN_POSITION_PROFILE_DESC));
                user.getMetaData()
                        .setRole(
                                cursor.getString(MapContract.MapEntry.COLUMN_POSITION_ROLE));
                user.getMetaData()
                        .setSize(
                                cursor.getString(MapContract.MapEntry.COLUMN_POSITION_SIZE));
                user.getMetaData()
                        .setTemperament(
                                cursor.getString(MapContract.MapEntry.COLUMN_POSITION_TEMPER));
                user.setuId(cursor
                        .getInt(MapContract.MapEntry.COLUMN_POSITION_UID));
                user.setuImg(cursor
                        .getString(MapContract.MapEntry.COLUMN_POSITION_UIMG));
                user.setuName(cursor
                        .getString(MapContract.MapEntry.COLUMN_POSITION_UNAME));
                user.getMetaData()
                        .setUp_for(
                                cursor.getString(MapContract.MapEntry.COLUMN_POSITION_UP_FOR));
                user.setUserType(cursor
                        .getInt(MapContract.MapEntry.COLUMN_POSITION_UTYPE));
                user.setWinkCount(cursor
                        .getInt(MapContract.MapEntry.COLUMN_POSITION_WINK_CNT));

                user.getMetaData()
                        .setHeight(
                                cursor.getString(MapContract.MapEntry.COLUMN_POSITION_HEIGHT));

                user.getMetaData()
                        .setDrink(
                                cursor.getString(MapContract.MapEntry.COLUMN_POSITION_DRINK));

                user.getMetaData()
                        .setEye_color(
                                cursor.getString(MapContract.MapEntry.COLUMN_POSITION_EYECOLOR));

                user.getMetaData()
                        .setHair_color(
                                cursor.getString(MapContract.MapEntry.COLUMN_POSITION_HAIRCOLOR));

                user.getMetaData()
                        .setSmoke(
                                cursor.getString(MapContract.MapEntry.COLUMN_POSITION_SMOKE));

                user.getMetaData()
                        .setOut_to(
                                cursor.getString(MapContract.MapEntry.COLUMN_POSITION_OUTTO));

                user.getMetaData()
                        .setEthnicity(
                                cursor.getString(MapContract.MapEntry.COLUMN_POSITION_ETHNICITY));

                user.setUser(cursor
                        .getInt(MapContract.MapEntry.COLUMN_POSITION_IS_USER) == 1 ? true
                        : false);

                users.add(user);
            }
        }
        return users;
    }

    public List<User> getBrowseUsersFromCursor(Cursor cursor) {
        List<User> users = new ArrayList<User>();

        if (cursor != null) {
            User user = null;
            while (cursor.moveToNext()) {
                user = new User();

                user.setActiveStatus(cursor
                        .getInt(MapContract.MapEntry.COLUMN_POSITION_AC_ST));
                user.setAge(cursor
                        .getInt(MapContract.MapEntry.COLUMN_POSITION_AGE));
                user.setMetaData(new MetaData());
                user.getMetaData()
                        .setBody_hair(
                                cursor.getString(MapContract.MapEntry.COLUMN_POSITION_BODY_HAIR));
                user.getMetaData()
                        .setBody_type(
                                cursor.getString(MapContract.MapEntry.COLUMN_POSITION_BODY_TYPE));
                user.getMetaData()
                        .setCut(cursor
                                .getString(MapContract.MapEntry.COLUMN_POSITION_CUT));
                user.setDistance(cursor
                        .getString(MapContract.MapEntry.COLUMN_POSITION_DISTANCE));
                user.getMetaData()
                        .setHiv_status(
                                cursor.getString(MapContract.MapEntry.COLUMN_POSITION_HIV_ST));
                user.setLastActiveTime(cursor
                        .getString(MapContract.MapEntry.COLUMN_POSITION_LAST_ACT_TIME));
                // TODO set media content
                // user.setMediaContents(mediaContents);
                user.setmLatitude(cursor
                        .getDouble(MapContract.MapEntry.COLUMN_POSITION_LAT));
                user.setmLongitude(cursor
                        .getDouble(MapContract.MapEntry.COLUMN_POSITION_LONG));
                user.getMetaData()
                        .setOrientation(
                                cursor.getString(MapContract.MapEntry.COLUMN_POSITION_ORIENT));
                user.getMetaData()
                        .setPersona(
                                cursor.getString(MapContract.MapEntry.COLUMN_POSITION_PERSONA));
                user.setPrivateNotes(cursor
                        .getString(MapContract.MapEntry.COLUMN_POSITION_PRIVATE_NOTE));
                user.setProfileDesc(cursor
                        .getString(MapContract.MapEntry.COLUMN_POSITION_PROFILE_DESC));
                user.getMetaData()
                        .setRole(
                                cursor.getString(MapContract.MapEntry.COLUMN_POSITION_ROLE));
                user.getMetaData()
                        .setSize(
                                cursor.getString(MapContract.MapEntry.COLUMN_POSITION_SIZE));
                user.getMetaData()
                        .setTemperament(
                                cursor.getString(MapContract.MapEntry.COLUMN_POSITION_TEMPER));
                user.setuId(cursor
                        .getInt(MapContract.MapEntry.COLUMN_POSITION_UID));
                user.setuImg(cursor
                        .getString(MapContract.MapEntry.COLUMN_POSITION_UIMG));
                user.setuName(cursor
                        .getString(MapContract.MapEntry.COLUMN_POSITION_UNAME));
                user.getMetaData()
                        .setUp_for(
                                cursor.getString(MapContract.MapEntry.COLUMN_POSITION_UP_FOR));
                user.setUserType(cursor
                        .getInt(MapContract.MapEntry.COLUMN_POSITION_UTYPE));
                user.setWinkCount(cursor
                        .getInt(MapContract.MapEntry.COLUMN_POSITION_WINK_CNT));

                user.getMetaData()
                        .setHeight(
                                cursor.getString(MapContract.MapEntry.COLUMN_POSITION_HEIGHT));

                user.getMetaData()
                        .setDrink(
                                cursor.getString(MapContract.MapEntry.COLUMN_POSITION_DRINK));

                user.getMetaData()
                        .setEye_color(
                                cursor.getString(MapContract.MapEntry.COLUMN_POSITION_EYECOLOR));

                user.getMetaData()
                        .setHair_color(
                                cursor.getString(MapContract.MapEntry.COLUMN_POSITION_HAIRCOLOR));

                user.getMetaData()
                        .setSmoke(
                                cursor.getString(MapContract.MapEntry.COLUMN_POSITION_SMOKE));

                user.getMetaData()
                        .setOut_to(
                                cursor.getString(MapContract.MapEntry.COLUMN_POSITION_OUTTO));

                user.getMetaData()
                        .setEthnicity(
                                cursor.getString(MapContract.MapEntry.COLUMN_POSITION_ETHNICITY));

                users.add(user);
            }
        }
        return users;
    }

    


    public int deleteRowItems(Uri url, long key) {
        String where = MediaContentContract.MediaContentContractEntry.COLUMN_NAME_KEY
                + "=?";
        return resolver
                .delete(url, where, new String[] { String.valueOf(key) });
    }

    public int deleteContentProviderData(Uri url, String where,
            String[] selectionArgs) {

        return resolver.delete(url, where, selectionArgs);

    }

    @SuppressWarnings("boxing")
    public boolean bulkInsertMediaContent(List<MediaContent> mediaContent) {

        ContentValues[] values = new ContentValues[mediaContent.size()];
        ContentValues value = null;

        for (int index = 0; index < mediaContent.size(); index++) {

            value = new ContentValues();

            value.put(
                    MediaContentContract.MediaContentContractEntry.COLUMN_NAME_UID,
                    mediaContent.get(index).getuId());

            value.put(
                    MediaContentContract.MediaContentContractEntry.COLUMN_NAME_URL,
                    mediaContent.get(index).getUrl());
            value.put(
                    MediaContentContract.MediaContentContractEntry.COLUMN_NAME_TYPE,
                    mediaContent.get(index).getType());
            value.put(
                    MediaContentContract.MediaContentContractEntry.COLUMN_NAME_KEY,
                    mediaContent.get(index).getKey());
            value.put(
                    MediaContentContract.MediaContentContractEntry.COLUMN_NAME_POSTION,
                    mediaContent.get(index).getPosition());

            values[index] = value;

        }

        int flag = resolver.bulkInsert(Uris.URI_MEDIA_CONTENT, values);

        return flag >= 0 ? true : false;

    }

    public List<MediaContent> getMediaContentFRomCursor(Cursor cursor) {
        List<MediaContent> mediaContents = new ArrayList<MediaContent>();

        Log.i("OUT", " Cursor Data " + cursor.getCount());
        if (cursor != null) {

            MediaContent user = null;
            while (cursor.moveToNext()) {
                user = new MediaContent();

                user.setuId(cursor
                        .getInt(MediaContentContract.MediaContentContractEntry.COLUMN_POSITION_UID));

                user.setUrl(cursor
                        .getString(MediaContentContract.MediaContentContractEntry.COLUMN_POSITION_URL));

                user.setType(cursor
                        .getString(MediaContentContract.MediaContentContractEntry.COLUMN_POSITION_TYPE));

                user.setKey(cursor
                        .getLong(MediaContentContract.MediaContentContractEntry.COLUMN_POSITION_KEY));

                user.setPosition(cursor
                        .getInt(MediaContentContract.MediaContentContractEntry.COLUMN_POSITION_POSITION));

                mediaContents.add(user);
            }

        }

        return mediaContents;
    }
}
