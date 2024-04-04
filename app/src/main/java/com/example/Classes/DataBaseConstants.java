package com.example.Classes;

public class DataBaseConstants {

    //DataBase Oluşturmak için Gerekli Olanlar
    public static final String DATABASE_NAME = "MyDB";
    public static final int DATABASE_VERSION = 1;



    //Article Tablosu için gerekli olanlar
    public static final String Article_Id="ArticleId";
    public static final String Article_Title="ArticleTitle";
    public static final String Article_Content="ArticleContent";
    public static final String Article_Number_Saves="NumberSaves";
    public static final String Article_Date_Save="DateSave";

    public static final String CREATE_TABLE_Articles =
            "CREATE TABLE Articles("
                    +Article_Id+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                    +Article_Title+" TEXT,"
                    +Article_Content+" TEXT,"
                    +Article_Number_Saves+" INTEGER,"
                    +Article_Date_Save+" DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";




    //User Tablosu için gerekli olanlar
    public static final String User_Id="UserId";
    public static final String User_Name_Unique="UserName";
    public static final String User_Name="Name";
    public static final String User_SurName="SurName";
    public static final String User_Phone_Number="PhoneNumber";
    public static final String User_Email="Email";
    public static final String User_Password="Password";
    public static final String User_Number_I_Follow="NumberIFollow";
    public static final String User_Number_Who_Follow_Me="NumberWhoFollowMe";
    public static final String User_Photo_Uri="PhotoUri";
    public static final String CREATE_TABLE_Users =
            "CREATE TABLE Users("
                    +User_Id+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                    +User_Name_Unique+" TEXT UNIQUE NOT NULL,"
                    +User_Name+" TEXT,"
                    +User_SurName+" TEXT,"
                    +User_Phone_Number+" TEXT,"
                    +User_Email+" TEXT,"
                    +User_Password+" TEXT,"
                    +User_Number_I_Follow+" INTEGER,"
                    +User_Number_Who_Follow_Me+" INTEGER,"
                    +User_Photo_Uri+" Text"
                    + ")";



}