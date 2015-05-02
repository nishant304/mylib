package com.wecamchat.aviddev.db;

import com.wecamchat.aviddev.api.io.User;
import com.wecamchat.aviddev.util.AppUtil;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * AvidDB Manager class manage the table structure and db upgrade facility.
 * 
 * @author gaurav
 * 
 */
public class AvidDBHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database
    // version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "AvidMedia.db";

    private static SQLiteOpenHelper avidDBManager;

    public AvidDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should
     * happen.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

    	String createTableForUser = AppUtil.getCreateTableStatement(User.class);
        // define table structure
        String CREATE_MAP_TABLE = "CREATE TABLE "
                + MapContract.MapEntry.TABLE_NAME + "(" + BaseColumns._ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + MapContract.MapEntry.COLUMN_NAME_UID + " INTEGER UNIQUE, "
                + MapContract.MapEntry.COLUMN_NAME_UNAME + " TEXT, "
                + MapContract.MapEntry.COLUMN_NAME_UTYPE + " INTEGER, "
                + MapContract.MapEntry.COLUMN_NAME_LAT + " TEXT, "
                + MapContract.MapEntry.COLUMN_NAME_LONG + " TEXT, "
                + MapContract.MapEntry.COLUMN_NAME_AC_ST + " INTEGER, "
                + MapContract.MapEntry.COLUMN_NAME_UIMG + " TEXT, "
                + MapContract.MapEntry.COLUMN_NAME_WINK_CNT + " INTEGER, "
                + MapContract.MapEntry.COLUMN_NAME_PROFILE_DESC + " TEXT, "
                + MapContract.MapEntry.COLUMN_NAME_LAST_ACT_TIME + " TEXT, "
                + MapContract.MapEntry.COLUMN_NAME_DISTANCE + " TEXT, "
                + MapContract.MapEntry.COLUMN_NAME_AGE + " INTEGER, "
                + MapContract.MapEntry.COLUMN_NAME_ORIENT + " TEXT,"
                + MapContract.MapEntry.COLUMN_NAME_BODY_TYPE + " TEXT, "
                + MapContract.MapEntry.COLUMN_NAME_TEMPER + " TEXT, "
                + MapContract.MapEntry.COLUMN_NAME_SIZE + " TEXT, "
                + MapContract.MapEntry.COLUMN_NAME_HIV_ST + " TEXT, "
                + MapContract.MapEntry.COLUMN_NAME_UP_FOR + " TEXT, "
                + MapContract.MapEntry.COLUMN_NAME_ROLE + " TEXT,"
                + MapContract.MapEntry.COLUMN_NAME_PERSONA + " TEXT, "
                + MapContract.MapEntry.COLUMN_NAME_BODY_HAIR + " TEXT, "
                + MapContract.MapEntry.COLUMN_NAME_CUT + " TEXT, "

                + MapContract.MapEntry.COLUMN_NAME_HEIGHT + " TEXT, "
                + MapContract.MapEntry.COLUMN_NAME_DRINK + " TEXT, "
                + MapContract.MapEntry.COLUMN_NAME_EYECOLOR + " TEXT, "
                + MapContract.MapEntry.COLUMN_NAME_HAIRCOLOR + " TEXT,"
                + MapContract.MapEntry.COLUMN_NAME_SMOKE + " TEXT, "
                + MapContract.MapEntry.COLUMN_NAME_OUTTO + " TEXT, "
                + MapContract.MapEntry.COLUMN_NAME_ETHNICITY + " TEXT, "
                + MapContract.MapEntry.COLUMN_NAME_PRIVATE_NOTE + " TEXT, "
                + MapContract.MapEntry.COLUMN_NAME_IS_USER + " INTEGER" + " )";

        String CREATE_BROWSE_TABLE = "CREATE TABLE "
                + BrowseContract.BrowseEntry.TABLE_NAME + "(" + BaseColumns._ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + BrowseContract.BrowseEntry.COLUMN_NAME_UID
                + " INTEGER UNIQUE, "
                + BrowseContract.BrowseEntry.COLUMN_NAME_UNAME + " TEXT, "
                + BrowseContract.BrowseEntry.COLUMN_NAME_UTYPE + " INTEGER, "
                + BrowseContract.BrowseEntry.COLUMN_NAME_LAT + " TEXT, "
                + BrowseContract.BrowseEntry.COLUMN_NAME_LONG + " TEXT, "
                + BrowseContract.BrowseEntry.COLUMN_NAME_AC_ST + " INTEGER, "
                + BrowseContract.BrowseEntry.COLUMN_NAME_UIMG + " TEXT, "
                + BrowseContract.BrowseEntry.COLUMN_NAME_WINK_CNT
                + " INTEGER, "
                + BrowseContract.BrowseEntry.COLUMN_NAME_PROFILE_DESC
                + " TEXT, "
                + BrowseContract.BrowseEntry.COLUMN_NAME_LAST_ACT_TIME
                + " TEXT, " + BrowseContract.BrowseEntry.COLUMN_NAME_DISTANCE
                + " TEXT, " + BrowseContract.BrowseEntry.COLUMN_NAME_AGE
                + " INTEGER, " + BrowseContract.BrowseEntry.COLUMN_NAME_ORIENT
                + " TEXT," + BrowseContract.BrowseEntry.COLUMN_NAME_BODY_TYPE
                + " TEXT, " + BrowseContract.BrowseEntry.COLUMN_NAME_TEMPER
                + " TEXT, " + BrowseContract.BrowseEntry.COLUMN_NAME_SIZE
                + " TEXT, " + BrowseContract.BrowseEntry.COLUMN_NAME_HIV_ST
                + " TEXT, " + BrowseContract.BrowseEntry.COLUMN_NAME_UP_FOR
                + " TEXT, " + BrowseContract.BrowseEntry.COLUMN_NAME_ROLE
                + " TEXT," + BrowseContract.BrowseEntry.COLUMN_NAME_PERSONA
                + " TEXT, " + BrowseContract.BrowseEntry.COLUMN_NAME_BODY_HAIR
                + " TEXT, " + BrowseContract.BrowseEntry.COLUMN_NAME_CUT
                + " TEXT, "

                + MapContract.MapEntry.COLUMN_NAME_HEIGHT + " TEXT, "
                + MapContract.MapEntry.COLUMN_NAME_DRINK + " TEXT, "
                + MapContract.MapEntry.COLUMN_NAME_EYECOLOR + " TEXT, "
                + MapContract.MapEntry.COLUMN_NAME_HAIRCOLOR + " TEXT,"
                + MapContract.MapEntry.COLUMN_NAME_SMOKE + " TEXT, "
                + MapContract.MapEntry.COLUMN_NAME_OUTTO + " TEXT, "
                + MapContract.MapEntry.COLUMN_NAME_ETHNICITY + " TEXT, "

                + BrowseContract.BrowseEntry.COLUMN_NAME_PRIVATE_NOTE + " TEXT"
                + " )";

        /*
         * 
         */

        String CREATE_MEDIA_CONTENT = "CREATE TABLE "
                + MediaContentContract.MediaContentContractEntry.TABLE_NAME
                + "("
                + BaseColumns._ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + MediaContentContract.MediaContentContractEntry.COLUMN_NAME_UID
                + " INTEGER , "
                + MediaContentContract.MediaContentContractEntry.COLUMN_NAME_URL
                + " TEXT, "
                + MediaContentContract.MediaContentContractEntry.COLUMN_NAME_TYPE
                + " TEXT, "
                + MediaContentContract.MediaContentContractEntry.COLUMN_NAME_KEY
                + " LONG, "
                + MediaContentContract.MediaContentContractEntry.COLUMN_NAME_POSTION
                + " INTEGER" + " )";

        // execute create table query
        db.execSQL(CREATE_MAP_TABLE);
        db.execSQL(CREATE_BROWSE_TABLE);
        db.execSQL(CREATE_MEDIA_CONTENT);

    }

    /**
     * Called when the database needs to be upgraded. The implementation should
     * use this method to drop tables, add tables, or do anything else it needs
     * to upgrade to the new schema version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + MapContract.MapEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "
                + BrowseContract.BrowseEntry.TABLE_NAME);

        // Create tables again
        onCreate(db);

    }

    /**
     * @param context
     * @return SQLiteDatabase instance.
     */
    public static SQLiteDatabase getdbInstance(Context context) {
        if (avidDBManager == null) {
            avidDBManager = new AvidDBHelper(context);
        }

        return avidDBManager.getWritableDatabase();

    }

}
