package com.example.Classes;

public class DataBaseConstants {


    //DataBase Oluşturmak için Gerekli Olanlar
    public static final String DATABASE_NAME = "MyDB";
    public static final int DATABASE_VERSION = 7;








    //Takip Tablosu için gerekli olanlar
    public static final String Follows_Id="FollowsId";
    public static final String User_Name_Follower="UserName";
    public static final String Followed_User_Name="FollowedUserId";

    public static final String CREATE_TABLE_Follows =
            "CREATE TABLE Follows("
                    +Follows_Id+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                    +User_Name_Follower+" TEXT,"
                    +Followed_User_Name+" TEXT"
                    + ")";






    //Oylar Tablosu için gerekli olanlar
    public static final String Rating_Id="RatingId";
    public static final String User_Name_Post="PostUserName";
    public static final String User_Name_Rating="RatingUserName";
    public static final String Score_Post="Score";
    public static final String Post_Id="PostId";
    public static final String CREATE_TABLE_Ratings =
            "CREATE TABLE Ratings("
                    +Rating_Id+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                    +Post_Id+" TEXT,"
                    +User_Name_Post+" TEXT,"
                    +User_Name_Rating+" TEXT,"
                    +Score_Post+" TEXT"
                    + ")";





    //Article Tablosu için gerekli olanlar
    public static final String Article_Id="ArticleId";
    public static final String Article_Title="ArticleTitle";
    public static final String Article_Content="ArticleContent";
    public static final String Article_Number_Scores="NumberScores";
    public static final String Article_Average_Scores="Average";

    public static final String CREATE_TABLE_Articles =
            "CREATE TABLE Articles("
                    +Article_Id+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                    +Article_Title+" TEXT,"
                    +Article_Content+" TEXT,"
                    +Article_Number_Scores+" INTEGER,"
                    +Article_Average_Scores+" TEXT"
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





    public static final String ArticleUser_Date_Save = "DateSave";
    public static final String ArticleUser_articleuser_id="ArticleUserId";
    public static final String ArticleUser_article_id="ArticleId";
    public static final String ArticleUser_user_id="UserId";
    public static final String CREATE_TABLE_ArticleUser =
            "CREATE TABLE ArticleUser("
                    +ArticleUser_articleuser_id+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                    +ArticleUser_article_id+" INTEGER,"
                    +ArticleUser_Date_Save+" INTEGER,"
                    +ArticleUser_user_id+" INTEGER"
                    + ")";

}
