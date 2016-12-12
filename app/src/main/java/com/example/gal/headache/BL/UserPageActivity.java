package com.example.gal.headache.BL;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gal.headache.BL.Adapters.ContactsAdapter;
import com.example.gal.headache.DAL.Actions.ContactsActions;
import com.example.gal.headache.Models.Contact;
import com.example.gal.headache.Models.ContactHeader;
import com.example.gal.headache.Models.Interfaces.IContactListObject;
import com.example.gal.headache.R;
import com.example.gal.headache.util.ContactsUtil;

import java.util.ArrayList;
import java.util.List;

public class UserPageActivity extends Activity {

    private static final int PICK_CONTACT = 1;
    private ListView lsvContacts;
    private TextView txtTotalContactsNumber;
    private TextView txtContactsNumber;
    ImageButton imgAddContact;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);

        initDM();
        initOnClick();
        loadData();
    }

    private void initOnClick() {
        imgAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReadContact();
            }
        });

        txtContactsNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetAllContacts();
            }
        });
    }

    public void resetAllContacts() {
        if (new ContactsActions(getBaseContext()).deleteAllContacts() > 0) {
            loadData();
        }
    }

    public void ReadContact() {
        try {
            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
            startActivityForResult(intent, PICK_CONTACT);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadData() {
        // Construct the data source
        List<IContactListObject> arrayOfContacts = ContactsUtil.getContactsWithSections(getBaseContext());

        setCountContactsAtTitle(arrayOfContacts);

        // Create the adapter to convert the array to views
        ContactsAdapter adapter = new ContactsAdapter(this, arrayOfContacts);

        // Attach the adapter to a ListView
        lsvContacts.setAdapter(adapter);
    }

    private void setCountContactsAtTitle(List<IContactListObject> contacts) {
        int totalLetters = 0;
        int totalContacts = 0;

        for (IContactListObject contact : contacts) {
            if (contact instanceof ContactHeader) {
                totalLetters++;

                if (((ContactHeader) contact).getSectionNumContacts() > 0) {
                    totalContacts++;
                }
            }
        }

        txtTotalContactsNumber.setText(Integer.toString(totalLetters));
        txtContactsNumber.setText(Integer.toString(totalContacts));

        int contactsNumberColor;

        if (totalContacts < 5) {
            contactsNumberColor = Color.RED;
        } else if (totalContacts < totalLetters / 2) {
            contactsNumberColor = Color.BLUE;
        } else {
            contactsNumberColor = Color.GREEN;
        }

        txtContactsNumber.setTextColor(contactsNumberColor);
    }

    private void initDM() {
        lsvContacts = (ListView) findViewById(R.id.lsvContactsView);
        imgAddContact = (ImageButton) findViewById(R.id.imgButtonAddContact);
        txtTotalContactsNumber = (TextView) findViewById(R.id.txtTotalContacts);
        txtContactsNumber = (TextView) findViewById(R.id.txtNumOfContacts);
    }

    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        switch (reqCode) {
            case (PICK_CONTACT):
                if (resultCode == Activity.RESULT_OK) {
                    Uri contactData = data.getData();
                    Cursor c = managedQuery(contactData, null, null, null, null);
                    //startManagingCursor(c);
                    if (c.moveToFirst()) {

                        String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));

                        String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                        String cNumber = null;

                        if (hasPhone.equalsIgnoreCase("1")) {
                            Cursor phones = getContentResolver().query(
                                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,
                                    null, null);

                            if (phones.moveToFirst()) {
                                cNumber = phones.getString(phones.getColumnIndex("data1"));
                            }
                        }

                        String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));


                        if (name == null) {
                            Toast.makeText(this, "לא נמצא שם", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (new ContactsActions(getBaseContext()).addContact(new Contact(name, cNumber)) > 0) {
                            loadData();
                            return;
                        }

                        Toast.makeText(this, "השם קיים :)", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }

    }

    private String retrieveContactNumber(Uri uriContact) {

        String contactNumber = null;

        // getting contacts ID
        Cursor cursorID = getContentResolver().query(uriContact,
                new String[]{ContactsContract.Contacts._ID},
                null, null, null);

        String contactID = "";

        if (cursorID.moveToFirst()) {

            contactID = cursorID.getString(cursorID.getColumnIndex(ContactsContract.Contacts._ID));
        }

        cursorID.close();


        ArrayList<String> phones = new ArrayList<String>();

        Cursor cursor = getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                new String[]{contactID}, null);

        while (cursor.moveToNext()) {
            phones.add(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
        }

        cursor.close();


        Toast.makeText(this, "Contact Phone Number: " + contactNumber, Toast.LENGTH_LONG).show();

        return contactNumber;
    }
}

