package com.wecamchat.aviddev.db;

import android.provider.BaseColumns;

/**
 * This class is used to declare schema of map database table.
 * 
 * @author gaurav
 * 
 */

public class MapContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public MapContract() {
    }

    /* Inner class that defines the table contents */
    public static abstract class MapEntry implements BaseColumns {

        // Table name of database.
        public static final String TABLE_NAME = "map";

        // Column name is require for insert and update data in table.
        public static final String COLUMN_NAME_UID = "uId";
        public static final String COLUMN_NAME_UNAME = "uName";
        public static final String COLUMN_NAME_UTYPE = "uType";
        public static final String COLUMN_NAME_LAT = "lat";
        public static final String COLUMN_NAME_LONG = "long";
        public static final String COLUMN_NAME_AC_ST = "acSt";
        public static final String COLUMN_NAME_UIMG = "uImg";
        public static final String COLUMN_NAME_WINK_CNT = "winkCount";
        public static final String COLUMN_NAME_PROFILE_DESC = "ProfileDesc";
        public static final String COLUMN_NAME_LAST_ACT_TIME = "lstActiveTime";
        public static final String COLUMN_NAME_DISTANCE = "distance";
        public static final String COLUMN_NAME_AGE = "age";
        public static final String COLUMN_NAME_ORIENT = "orientation";
        public static final String COLUMN_NAME_BODY_TYPE = "bodyType";
        public static final String COLUMN_NAME_TEMPER = "temper";
        public static final String COLUMN_NAME_SIZE = "size";
        public static final String COLUMN_NAME_HIV_ST = "hivSt";
        public static final String COLUMN_NAME_UP_FOR = "upFor";
        public static final String COLUMN_NAME_ROLE = "role";
        public static final String COLUMN_NAME_PERSONA = "persona";
        public static final String COLUMN_NAME_BODY_HAIR = "bodyhair";
        public static final String COLUMN_NAME_CUT = "cut";

        public static final String COLUMN_NAME_HEIGHT = "height";
        public static final String COLUMN_NAME_DRINK = "drink";
        public static final String COLUMN_NAME_EYECOLOR = "eyeColor";
        public static final String COLUMN_NAME_HAIRCOLOR = "hairColor";
        public static final String COLUMN_NAME_SMOKE = "smoke";
        public static final String COLUMN_NAME_OUTTO = "outTo";
        public static final String COLUMN_NAME_ETHNICITY = "ethnicity";

        public static final String COLUMN_NAME_PRIVATE_NOTE = "privateNote";

        public static final String COLUMN_NAME_IS_USER = "isUser";

        // Column position is require for getting data from cursor.
        public static final int COLUMN_POSITION_UID = 1;
        public static final int COLUMN_POSITION_UNAME = 2;
        public static final int COLUMN_POSITION_UTYPE = 3;
        public static final int COLUMN_POSITION_LAT = 4;
        public static final int COLUMN_POSITION_LONG = 5;
        public static final int COLUMN_POSITION_AC_ST = 6;
        public static final int COLUMN_POSITION_UIMG = 7;
        public static final int COLUMN_POSITION_WINK_CNT = 8;
        public static final int COLUMN_POSITION_PROFILE_DESC = 9;
        public static final int COLUMN_POSITION_LAST_ACT_TIME = 10;
        public static final int COLUMN_POSITION_DISTANCE = 11;
        public static final int COLUMN_POSITION_AGE = 12;
        public static final int COLUMN_POSITION_ORIENT = 13;
        public static final int COLUMN_POSITION_BODY_TYPE = 14;
        public static final int COLUMN_POSITION_TEMPER = 15;
        public static final int COLUMN_POSITION_SIZE = 16;
        public static final int COLUMN_POSITION_HIV_ST = 17;
        public static final int COLUMN_POSITION_UP_FOR = 18;
        public static final int COLUMN_POSITION_ROLE = 19;
        public static final int COLUMN_POSITION_PERSONA = 20;
        public static final int COLUMN_POSITION_BODY_HAIR = 21;
        public static final int COLUMN_POSITION_CUT = 22;

        public static final int COLUMN_POSITION_HEIGHT = 23;
        public static final int COLUMN_POSITION_DRINK = 24;
        public static final int COLUMN_POSITION_EYECOLOR = 25;
        public static final int COLUMN_POSITION_HAIRCOLOR = 26;
        public static final int COLUMN_POSITION_SMOKE = 27;
        public static final int COLUMN_POSITION_OUTTO = 28;
        public static final int COLUMN_POSITION_ETHNICITY = 29;

        public static final int COLUMN_POSITION_PRIVATE_NOTE = 30;
        public static final int COLUMN_POSITION_IS_USER = 31;
    }

}
