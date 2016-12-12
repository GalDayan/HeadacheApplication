package com.example.gal.headache.DAL;

/**
 * Created by Gal on 22/07/2016.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PhasesDB extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "headache.phase.db";
    // Contacts table name
    public static final String TABLE_PHASE = "phase";
    // Shops Table Columns names
    public static final String PHASE_NUMBER = "phaseNumber";
    public static final String[] COLUMNS = {PHASE_NUMBER};

    public PhasesDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_PHASE + "("
                + PHASE_NUMBER + " INTEGER PRIMARY KEY)";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PHASE);
        onCreate(db);
    }

    public long insert(ContentValues values) {
        SQLiteDatabase db = getWritableDatabase();
        long insertNum = db.insert(TABLE_PHASE, null, values);
        db.close(); // Closing database connection

        return insertNum;
    }


    public int numberOfRows(){
        SQLiteDatabase db = getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_PHASE);

        db.close();

        return numRows;
    }
}