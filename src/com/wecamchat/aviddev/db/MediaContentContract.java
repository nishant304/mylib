package com.wecamchat.aviddev.db;

import android.provider.BaseColumns;

public class MediaContentContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public MediaContentContract() {
    }

    /* Inner class that defines the table contents */
    public static abstract class MediaContentContractEntry implements
            BaseColumns {

        // Table name of database.
        public static final String TABLE_NAME = "mediaContent";

        // Column name is require for insert and update data in table.
        public static final String COLUMN_NAME_UID = "uId";
        public static final String COLUMN_NAME_URL = "url";
        public static final String COLUMN_NAME_TYPE = "type";
        public static final String COLUMN_NAME_KEY = "key";
        public static final String COLUMN_NAME_POSTION = "position";

        // Column position is require for getting data from cursor.
        public static final int COLUMN_POSITION_UID = 1;
        public static final int COLUMN_POSITION_URL = 2;
        public static final int COLUMN_POSITION_TYPE = 3;
        public static final int COLUMN_POSITION_KEY = 4;
        public static final int COLUMN_POSITION_POSITION = 5;

    }

}
