package com.example.gal.headache.Models;

import android.net.Uri;

import com.example.gal.headache.Models.Interfaces.IContactListObject;

import java.net.URI;

/**
 * Created by Gal on 01/08/2016.
 */
public class Contact implements IContactListObject {
    private String name;
    private String phone;

    public Contact(String name) {
        this.name = name;
        this.phone = null;
    }

    public Contact(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return this.phone;
    }

    @Override
    public String getTitle() {
        return this.name;
    }

    @Override
    public void setTitle(String title) {
        this.name = title;
    }
}
