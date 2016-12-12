package com.example.gal.headache.Models;

import com.example.gal.headache.Models.Interfaces.IContactListObject;

/**
 * Created by Gal on 01/08/2016.
 */
public class ContactHeader implements IContactListObject {
    private String sectionHeaderName;
    private int sectionNumContacts;

    public ContactHeader(String sectionHeaderName) {
        this.sectionHeaderName = sectionHeaderName;
    }

    public ContactHeader(String sectionHeaderName, int sectionNumContacts) {
        this.sectionHeaderName = sectionHeaderName;
        this.sectionNumContacts = sectionNumContacts;
    }

    public String getSectionHeaderName() {
        return sectionHeaderName;
    }

    public void setSectionHeaderName(String sectionHeaderName) {
        this.sectionHeaderName = sectionHeaderName;
    }

    public int getSectionNumContacts() {
        return sectionNumContacts;
    }

    public void setSectionNumContacts(int sectionNumContacts) {
        this.sectionNumContacts = sectionNumContacts;
    }

    @Override
    public String getTitle() {
        return this.sectionHeaderName;
    }

    @Override
    public void setTitle(String title) {
        this.sectionHeaderName = title;
    }
}
