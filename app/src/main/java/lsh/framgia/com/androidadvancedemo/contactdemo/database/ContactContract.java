package lsh.framgia.com.androidadvancedemo.contactdemo.database;

import static lsh.framgia.com.androidadvancedemo.contactdemo.database.ContactContract.ContactEntry.COLUMN_ID;
import static lsh.framgia.com.androidadvancedemo.contactdemo.database.ContactContract.ContactEntry.COLUMN_MOBILE_NUMBER;
import static lsh.framgia.com.androidadvancedemo.contactdemo.database.ContactContract.ContactEntry.COLUMN_NAME;
import static lsh.framgia.com.androidadvancedemo.contactdemo.database.ContactContract.ContactEntry.TABLE_NAME;

public class ContactContract {

    static class ContactEntry {
        static final String TABLE_NAME = "entry";
        static final String COLUMN_ID = "id";
        static final String COLUMN_NAME = "title";
        static final String COLUMN_MOBILE_NUMBER = "mobile_number";
    }


    static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME + " TEXT," +
                    COLUMN_MOBILE_NUMBER + " TEXT)";

    static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;
}
