package com.example.gal.headache.Models;

/**
 * Created by Gal on 22/07/2016.
 */
public class User {
    
    private int userPin;
    private String userName;

    public User(int userPin, String userName) {
        this.userPin = userPin;
        this.userName = userName;
    }

    public int getUserPin() {
        return userPin;
    }

    public void setUserPin(int userPin) {
        this.userPin = userPin;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
