package com.example.gal.headache.DAL;

/**
 * Created by Gal on 22/07/2016.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDB extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "headache.users.db";
    // Contacts table name
    public static final String TABLE_USERS = "users";
    // Shops Table Columns names
    private static final String USER_ID = "id";
    public static final String USER_NAME = "user_name";
    public static final String USER_PIN = "user_pin";
    public static final String[] COLUMNS = {USER_NAME,USER_PIN};

    public UserDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + USER_ID + " INTEGER PRIMARY KEY," + USER_NAME + " TEXT,"
                + USER_PIN + " INTEGER" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    public void insert(ContentValues values) {
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_USERS, null, values);
        db.close(); // Closing database connection
    }


    public int numberOfRows(){
        SQLiteDatabase db = getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_USERS);

        db.close();

        return numRows;
    }
}