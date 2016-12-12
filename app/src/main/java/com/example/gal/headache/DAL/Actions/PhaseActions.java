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

    public long addPhase(Phase phase) {
        ContentValues values = new ContentValues();
        values.put(phasesDB.PHASE_NUMBER, phase.getNumber());

        return phasesDB.insert(values);
    }

    public Phase getMaxPhase() {
        // 1. get reference to readable DB
        SQLiteDatabase db = phasesDB.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor =
                    db.query(phasesDB.TABLE_PHASE, // a. table
                            new String[]{"MAX(" + phasesDB.PHASE_NUMBER + ")"}, // b. column names
                            null, // c. selections
                            null, // d. select ions args
                            null, // e. group by
                            null, // f. having
                            null, // g. order by
                            null); // h. limit


            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    return new Phase(cursor.getInt(0));
                }
            }
        } catch (Exception ex) {
            Log.d("", "");
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

    public Phase resetPhases() {
        if (phasesDB.delete() > 0) {
            Phase phase = new Phase(1);
            if (addPhase(phase) > 1) {
                return phase;
            }
        }

        return null;
    }
}
