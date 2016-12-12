package com.example.gal.headache.DAL.Actions;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.example.gal.headache.DAL.ContactDB;
import com.example.gal.headache.Models.Contact;
import com.example.gal.headache.Models.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gal on 22/07/2016.
 */
public class ContactsActions {
    private ContactDB contactDB;

    public ContactsActions(Context ctx) {
        contactDB = new ContactDB(ctx);
    }

    public long addContact(Contact contact) {
        ContentValues values = new ContentValues();
        values.put(contactDB.CONTACT_NAME, contact.getName());
        values.put(contactDB.CONTACT_URI, contact.getPhone());
        return contactDB.insert(values);
    }

    public List<Contact> getContacts() {
        // 1. get reference to readable DB
        SQLiteDatabase db = contactDB.getReadableDatabase();
        Cursor cursor = null;

        ArrayList<Contact> contacts = new ArrayList<>();

        try {
            cursor =
                    db.query(contactDB.TABLE_CONTACTS, // a. table
                            contactDB.COLUMNS, // b. column names
                            "", // c. selections
                            null, // d. select ions args
                            null, // e. group by
                            null, // f. having
                            null, // g. order by
                            null); // h. limit


            if (cursor != null) {
                while (cursor.moveToNext()) {
                    contacts.add(new Contact(cursor.getString(cursor.getColumnIndex(contactDB.CONTACT_NAME)),
                                             cursor.getString(cursor.getColumnIndex(contactDB.CONTACT_URI))));
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

        return contacts;
    }

    public boolean hasContacts() {
        return contactDB.numberOfRows() > 0;
    }
}
