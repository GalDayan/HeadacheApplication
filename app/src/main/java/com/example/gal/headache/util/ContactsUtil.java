package com.example.gal.headache.util;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.example.gal.headache.DAL.Actions.ContactsActions;
import com.example.gal.headache.Models.Contact;
import com.example.gal.headache.Models.ContactHeader;
import com.example.gal.headache.Models.Interfaces.IContactListObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gal on 01/08/2016.
 */
public final class ContactsUtil {

    public static List<IContactListObject> getContactsWithSections(Context ctx) {
        List<IContactListObject> contactsWithSections = new ArrayList<>();
        ContactsActions contactsActions = new ContactsActions(ctx);
        List<Contact> contacts = contactsActions.getContacts();

        for (char i = 'א'; i <= 'ת'; i++) {

            int contactCount = 0;

            if (i != 'ך' && i != 'ן' && i != 'ף' && i != 'ץ' && i != 'ם') {

                ContactHeader contactHeader = new ContactHeader("האות " + i + "'");
                contactsWithSections.add(contactHeader);

                for (Contact contact : contacts) {
                    if (contact.getName() != null && !contact.getName().isEmpty() && contact.getName().charAt(0) == i) {
                        contactsWithSections.add(contact);
                        contactCount++;
                    }
                }

                contactHeader.setSectionNumContacts(contactCount);
            }
        }

        return contactsWithSections;
    }

    public static List<IContactListObject> getContactsWithSectionsStub(Context ctx) {
        List<IContactListObject> contactsWithSections = new ArrayList<>();

        for (char i = 'א'; i <= 'ת'; i++) {
            if (i != 'ך' && i != 'ן' && i != 'ף' && i != 'ץ' && i != 'ם') {
                contactsWithSections.add(new ContactHeader("האות " + i + "'"));
                contactsWithSections.add(new Contact(i + " אות"));
            }
        }

        return contactsWithSections;
    }
}
