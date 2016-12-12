package com.example.gal.headache.DAL.Actions;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.gal.headache.DAL.PhasesDB;
import com.example.gal.headache.Models.Contact;
import com.example.gal.headache.Models.Phase;

import java.util.ArrayList;
/**
 * Created by Gal on 22/07/2016.
 */
public class PhaseActions {
    private PhasesDB phasesDB;

    public PhaseActions(Context ctx) {
        phasesDB = new PhasesDB(ctx);
    }

    public long addPhase(int currentPhase) {
        ContentValues values = new ContentValues();
        values.put(phasesDB.PHASE_NUMBER, ++currentPhase);

        return phasesDB.insert(values);
    }

    public Phase getMaxPhase() {
        // 1. get reference to readable DB
        SQLiteDatabase db = phasesDB.getReadableDatabase();
        Cursor cursor = null;

        ArrayList<Contact> contacts = new ArrayList<>();

        try {
            cursor =
                    db.query(phasesDB.TABLE_PHASE, // a. table
                            phasesDB.COLUMNS, // b. column names
                            "MAX(" + phasesDB.PHASE_NUMBER + ")", // c. selections
                            null, // d. select ions args
                            null, // e. group by
                            null, // f. having
                            null, // g. order by
                            null); // h. limit


            if (cursor != null) {
                if (cursor.moveToNext()) {
                    return new Phase(cursor.getInt(cursor.getColumnIndex(phasesDB.PHASE_NUMBER)));
                }
            }
        }
        catch (Exception ex) {
            Log.d("","");
        }
        // 2. build query


        if (cursor != null) {
            cursor.close();
        }

        db.close();

        return null;
    }

    public boolean hasPhases() {
        return phasesDB.numberOfRows() > 0;
    }
}
