package com.example.Classes;

public class UserSingleton {
    private static UserSingleton user=null;
    private UserSingleton(){}

    public static UserSingleton getInstance(){
        if (user==null)
            user=new UserSingleton();
        return user;

    }
    private String name;
    private String surName;
    private String email;
    private String number;
    private String userName;
    private String password;

    public static UserSingleton getUser() {
        return user;
    }

    public static void setUser(UserSingleton user) {
        UserSingleton.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isMale() {
        return isMale;
    }

    public void setMale(boolean male) {
        isMale = male;
    }

    private boolean isMale;


}
