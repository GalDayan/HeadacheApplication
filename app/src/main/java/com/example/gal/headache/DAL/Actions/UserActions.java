package com.example.gal.headache.DAL.Actions;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.gal.headache.DAL.UserDB;
import com.example.gal.headache.Models.User;

/**
 * Created by Gal on 22/07/2016.
 */
public class UserActions {
    private UserDB userDB;

    public UserActions(Context ctx) {
        userDB = new UserDB(ctx);
    }

    public void addUser(User user) {
        ContentValues values = new ContentValues();
        values.put(userDB.USER_NAME, user.getUserName());
        values.put(userDB.USER_PIN, user.getUserPin());
        userDB.insert(values);
    }

    public boolean loginUser(User user) {
        // 1. get reference to readable DB
        SQLiteDatabase db = userDB.getReadableDatabase();
        Cursor cursor = null;
        Boolean hasUser = false;

        try {
            cursor =
                    db.query(userDB.TABLE_USERS, // a. table
                            userDB.COLUMNS, // b. column names
                            userDB.USER_PIN + " = ?", // c. selections
                            new String[]{Integer.toString(user.getUserPin())}, // d. selections args
                            null, // e. group by
                            null, // f. having
                            null, // g. order by
                            null); // h. limit


            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    hasUser= true;
                }
            }
        }
        catch (Exception ex) {
            Log.d("","");
        }
        // 2. build query


        cursor.close();
        db.close();

        return hasUser;
    }

    public boolean hasUser() {
        return userDB.numberOfRows() > 0;
    }
}
