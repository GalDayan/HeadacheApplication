package com.example.gal.headache.DAL;

/**
 * Created by Gal on 22/07/2016.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ContactDB extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "headache.contacts.db";
    // Contacts table name
    public static final String TABLE_CONTACTS = "contacts";
    // Shops Table Columns names
    private static final String CONTACT_ID = "id";
    public static final String CONTACT_NAME = "contact_name";
    public static final String CONTACT_URI = "contact_uri";
    public static final String[] COLUMNS = {CONTACT_NAME, CONTACT_URI};

    public ContactDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + CONTACT_ID + " INTEGER PRIMARY KEY,"
                + CONTACT_NAME + " TEXT,"
                + CONTACT_URI + " TEXT )";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        onCreate(db);
    }

    public long insert(ContentValues values) {
        SQLiteDatabase db = getWritableDatabase();
        long insertNum = db.insert(TABLE_CONTACTS, null, values);
        db.close(); // Closing database connection

        return insertNum;
    }


    public int numberOfRows(){
        SQLiteDatabase db = getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_CONTACTS);

        db.close();

        return numRows;
    }
}