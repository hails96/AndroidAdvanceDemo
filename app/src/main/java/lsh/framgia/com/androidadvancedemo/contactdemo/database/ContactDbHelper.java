package lsh.framgia.com.androidadvancedemo.contactdemo.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import lsh.framgia.com.androidadvancedemo.contactdemo.database.ContactContract.ContactEntry;
import lsh.framgia.com.androidadvancedemo.contactdemo.model.Contact;

public class ContactDbHelper extends SQLiteOpenHelper {

    private static final String TAG = ContactDbHelper.class.getSimpleName();
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Contact.db";

    public ContactDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ContactContract.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(ContactContract.SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public long insertContact(Contact contact) {
        long contactId = -1;
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(ContactEntry.COLUMN_ID, contact.getId());
            values.put(ContactEntry.COLUMN_NAME, contact.getName());
            values.put(ContactEntry.COLUMN_MOBILE_NUMBER, contact.getMobileNumber());
            contactId = db.insertOrThrow(ContactEntry.TABLE_NAME, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        } finally {
            db.endTransaction();
        }
        return contactId;
    }

    public List<Contact> readContacts() {
        List<Contact> contacts = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(
                ContactEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            Contact contact = new Contact();
            contact.setId(
                    cursor.getString(cursor.getColumnIndex(ContactEntry.COLUMN_ID)));
            contact.setName(
                    cursor.getString(cursor.getColumnIndex(ContactEntry.COLUMN_NAME)));
            contact.setMobileNumber(
                    cursor.getString(cursor.getColumnIndex(ContactEntry.COLUMN_MOBILE_NUMBER)));
            contacts.add(contact);
        }
        cursor.close();
        return contacts;
    }

    public int updateContact(Contact contact) {
        int rowsAffected = -1;
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(ContactEntry.COLUMN_MOBILE_NUMBER, contact.getMobileNumber());
            rowsAffected = db.update(
                    ContactEntry.TABLE_NAME,
                    values,
                    ContactEntry.COLUMN_NAME + " LIKE ?",
                    new String[]{contact.getName()}
            );
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        } finally {
            db.endTransaction();
        }
        return rowsAffected;
    }

    public int deleteContacts() {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(ContactEntry.TABLE_NAME, null, null);
    }
}
