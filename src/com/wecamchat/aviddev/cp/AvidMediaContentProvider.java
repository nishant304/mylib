package com.wecamchat.aviddev.cp;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.wecamchat.aviddev.db.AvidDBHelper;
import com.wecamchat.aviddev.db.BrowseContract;
import com.wecamchat.aviddev.db.MapContract;
import com.wecamchat.aviddev.db.MediaContentContract;

public class AvidMediaContentProvider extends ContentProvider {

    private static final String TAG = "AvidContentProvider";
    private SQLiteDatabase db;
    private static UriMatcher sUriMatcher;

    // set uri matcher
    static {
        sUriMatcher = new UriMatcher(0);

        sUriMatcher.addURI(ContentProviderData.AUTHORITY,
                MapContract.MapEntry.TABLE_NAME,
                ContentProviderData.ID_MAP_USER);

        sUriMatcher.addURI(ContentProviderData.AUTHORITY,
                BrowseContract.BrowseEntry.TABLE_NAME,
                ContentProviderData.ID_BROWSE_USER);

        sUriMatcher.addURI(ContentProviderData.AUTHORITY,
                MediaContentContract.MediaContentContractEntry.TABLE_NAME,
                ContentProviderData.ID_MEDIA_CONTENT);
    }

    @Override
    public String getType(Uri uri) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        db = AvidDBHelper.getdbInstance(getContext());

        db.beginTransaction();
        long result = 0;
        switch (sUriMatcher.match(uri)) {
        case ContentProviderData.ID_MAP_USER:
            result = db.insert(MapContract.MapEntry.TABLE_NAME, null, values);
            break;
        case ContentProviderData.ID_BROWSE_USER:
            result = db.insert(BrowseContract.BrowseEntry.TABLE_NAME, null,
                    values);
            break;

        case ContentProviderData.ID_MEDIA_CONTENT:
            result = db.insert(
                    MediaContentContract.MediaContentContractEntry.TABLE_NAME,
                    null, values);
            break;

        }

        Log.d(TAG, "insert :: " + sUriMatcher.match(uri) + " : result :"
                + result);

        db.setTransactionSuccessful();
        db.endTransaction();
        // db.close();
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.withAppendedPath(uri, result + "");
    }

    @Override
    public boolean onCreate() {
        try {
            db = AvidDBHelper.getdbInstance(getContext());
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
            String[] selectionArgs, String sortOrder) {
        Cursor resultCursor = null;

        db = AvidDBHelper.getdbInstance(getContext());

        switch (sUriMatcher.match(uri)) {
        case ContentProviderData.ID_MAP_USER:
            resultCursor = db
                    .query(MapContract.MapEntry.TABLE_NAME, projection,
                            selection, selectionArgs, null, null, sortOrder);
            resultCursor.setNotificationUri(getContext().getContentResolver(),
                    uri);
            break;

        case ContentProviderData.ID_BROWSE_USER:
            resultCursor = db
                    .query(BrowseContract.BrowseEntry.TABLE_NAME, projection,
                            selection, selectionArgs, null, null, sortOrder);
            resultCursor.setNotificationUri(getContext().getContentResolver(),
                    uri);
            break;

        case ContentProviderData.ID_MEDIA_CONTENT:
            resultCursor = db
                    .query(MediaContentContract.MediaContentContractEntry.TABLE_NAME,
                            projection, selection, selectionArgs, null, null,
                            sortOrder);
            resultCursor.setNotificationUri(getContext().getContentResolver(),
                    uri);
            break;

        default:
            String sql = selection;
            resultCursor = db.rawQuery(sql, null);
            resultCursor.setNotificationUri(getContext().getContentResolver(),
                    uri);
            break;
        }

        return resultCursor;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        db = AvidDBHelper.getdbInstance(getContext());
        int result = -1;
        switch (sUriMatcher.match(uri)) {
        case ContentProviderData.ID_MAP_USER:
            result = db.delete(MapContract.MapEntry.TABLE_NAME, selection,
                    selectionArgs);
            break;
        case ContentProviderData.ID_BROWSE_USER:
            result = db.delete(BrowseContract.BrowseEntry.TABLE_NAME,
                    selection, selectionArgs);
            break;
        case ContentProviderData.ID_MEDIA_CONTENT:
            result = db.delete(
                    MediaContentContract.MediaContentContractEntry.TABLE_NAME,
                    selection, selectionArgs);
            break;

        }

        Log.d(TAG, "deleted row :: " + result);

        return result;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
            String[] selectionArgs) {
        int result = -1;
        db = AvidDBHelper.getdbInstance(getContext());
        switch (sUriMatcher.match(uri)) {
        case ContentProviderData.ID_MAP_USER:
            result = db.update(MapContract.MapEntry.TABLE_NAME, values,
                    selection, selectionArgs);

            break;
        case ContentProviderData.ID_BROWSE_USER:
            result = db.update(BrowseContract.BrowseEntry.TABLE_NAME, values,
                    selection, selectionArgs);
            break;

        }

        return result;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        int num = values == null ? -1 : values.length;
        long result = -1;

        db = AvidDBHelper.getdbInstance(getContext());
        db.beginTransaction();
        switch (sUriMatcher.match(uri)) {
        case ContentProviderData.ID_MAP_USER:
            for (int i = 0; i < num; i++) {
                result = db.insert(MapContract.MapEntry.TABLE_NAME, null,
                        values[i]);
            }
            break;
        case ContentProviderData.ID_BROWSE_USER:
            for (int i = 0; i < num; i++) {
                result = db.insert(BrowseContract.BrowseEntry.TABLE_NAME, null,
                        values[i]);
            }
            break;

        case ContentProviderData.ID_MEDIA_CONTENT:
            for (int i = 0; i < num; i++) {
                result = db
                        .insert(MediaContentContract.MediaContentContractEntry.TABLE_NAME,
                                null, values[i]);
            }
            break;

        }

        Log.d(TAG, "bulkInsert :: " + sUriMatcher.match(uri) + " : result :"
                + result);

        db.setTransactionSuccessful();
        db.endTransaction();

        getContext().getContentResolver().notifyChange(uri, null);

        return (int) result;
    }

}
