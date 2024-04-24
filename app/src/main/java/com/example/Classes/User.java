package com.example.Classes;

import android.net.Uri;

public class User {
    private String name;
    private String surName;
    private String email;
    private String number;
    private String userName;
    private int numberIFollow;
    private int numberWhoFollowMe;
    private Uri photo;
    private int userId;


    public void setName(String name) {
        this.name = name;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setNumberIFollow(int numberIFollow) {
        this.numberIFollow = numberIFollow;
    }

    public void setNumberWhoFollowMe(int numberWhoFollowMe) {
        this.numberWhoFollowMe = numberWhoFollowMe;
    }

    public void setPhoto(Uri photo) {
        this.photo = photo;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public String getSurName() {
        return surName;
    }

    public String getEmail() {
        return email;
    }

    public String getNumber() {
        return number;
    }

    public String getUserName() {
        return userName;
    }

    public int getNumberIFollow() {
        return numberIFollow;
    }

    public int getNumberWhoFollowMe() {
        return numberWhoFollowMe;
    }

    public Uri getPhoto() {
        return photo;
    }

    public int getUserId() {
        return userId;
    }



}
