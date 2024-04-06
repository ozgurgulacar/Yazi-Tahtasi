package com.example.Classes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {



    public DataBaseHelper(@Nullable Context context) {
            super(context, DataBaseConstants.DATABASE_NAME, null, DataBaseConstants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DataBaseConstants.CREATE_TABLE_Articles);
        db.execSQL(DataBaseConstants.CREATE_TABLE_Users);
        db.execSQL(DataBaseConstants.CREATE_TABLE_ArticleUser);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Articles");
        db.execSQL("DROP TABLE IF EXISTS Users");
        db.execSQL("DROP TABLE IF EXISTS ArticleUser");

        onCreate(db);

    }




    public String addUser(UserSingleton user){
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(DataBaseConstants.User_Name_Unique,user.getUserName());
            values.put(DataBaseConstants.User_Email,user.getEmail());
            values.put(DataBaseConstants.User_Password,user.getPassword());
            values.put(DataBaseConstants.User_Name,user.getName());
            values.put(DataBaseConstants.User_SurName,user.getSurName());
            values.put(DataBaseConstants.User_Phone_Number,user.getNumber());
            values.put(DataBaseConstants.User_Number_I_Follow,user.getNumberIFollow());
            values.put(DataBaseConstants.User_Number_Who_Follow_Me,user.getNumberWhoFollowMe());
            values.put(DataBaseConstants.User_Photo_Uri,user.getPhoto().toString());

            long id = db.insert("Users",null,values);

            db.close();
            return String.valueOf(id);

        }
        catch (Exception x){
            return x.getMessage();
        }
    }

    public boolean addArticle(Article article){
        try {
            //Article Tablosuna Ekleme Yapar
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(DataBaseConstants.Article_Number_Saves,0);
            values.put(DataBaseConstants.Article_Title,article.getArticleTitle());
            values.put(DataBaseConstants.Article_Content,article.getArticleContent());
            values.put(DataBaseConstants.Article_Date_Save, article.getDateSave().getTime());

            long id=db.insert("Articles",null,values);
            db.close();


            //ArticleUser Tablosuna Ekleme Yapar
            UserSingleton user=UserSingleton.getInstance();
            SQLiteDatabase db2 = this.getWritableDatabase();
            ContentValues values2 = new ContentValues();

            values2.put(DataBaseConstants.ArticleUser_article_id,id);
            values2.put(DataBaseConstants.ArticleUser_user_id,user.getUserId());

            long idArticleUser=db.insert("ArticleUser",null,values2);
            db.close();

            return true;
        }catch (Exception e){
            return false;
        }
    }

    public List<Users> searchPeople(String queryValue){
        queryValue = queryValue.substring(3);
        List<Users> users = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String sorgu="SELECT * from Users where "+queryValue;
        Cursor cursor=db.rawQuery(sorgu,null);
        if (cursor.moveToFirst()){
            do {
                Users user = new Users();
                user.setName(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseConstants.User_Name)));
                user.setSurName(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseConstants.User_SurName)));
                user.setUserName(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseConstants.User_Name_Unique)));
                user.setNumber(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseConstants.User_Phone_Number)));
                user.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseConstants.User_Email)));
                user.setPhoto(Uri.parse(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseConstants.User_Photo_Uri))));
                user.setUserId(cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseConstants.User_Id)));
                user.setNumberIFollow(cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseConstants.User_Number_I_Follow)));
                user.setNumberWhoFollowMe(cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseConstants.User_Number_Who_Follow_Me)));
                users.add(user);
            }while (cursor.moveToNext());
        }
        else {
            Users user = new Users();
            user.setName("Kimseyi Bulamadı");
            users.add(user);
        }

        cursor.close();

        return users;

    }

    public boolean isLoginSuccesful(String userName,String password){
        try {
            SQLiteDatabase db = getReadableDatabase();

            String returns[] = {DataBaseConstants.User_Password,DataBaseConstants.User_Name,DataBaseConstants.User_Id,DataBaseConstants.User_SurName,DataBaseConstants.User_Email,DataBaseConstants.User_Number_I_Follow,DataBaseConstants.User_Number_Who_Follow_Me,DataBaseConstants.User_Phone_Number,DataBaseConstants.User_Photo_Uri};

            String query = DataBaseConstants.User_Name_Unique + "= ?";
            String queryValue[] = {userName};

            Cursor cursor = db.query("Users", returns, query, queryValue, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                String realPassword = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseConstants.User_Password));
                if (realPassword.equals(password)){

                    UserSingleton user=UserSingleton.getInstance();
                    user.setNumber(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseConstants.User_Phone_Number)));
                    user.setPassword(realPassword);
                    user.setUserId(cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseConstants.User_Id)));
                    user.setUserName(userName);
                    user.setName(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseConstants.User_Name)));
                    user.setSurName(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseConstants.User_SurName)));
                    user.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseConstants.User_Email)));
                    user.setPhoto(Uri.parse(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseConstants.User_Photo_Uri))));
                    user.setNumberIFollow(cursor.getColumnIndexOrThrow(DataBaseConstants.User_Number_I_Follow));
                    user.setNumberWhoFollowMe(cursor.getColumnIndexOrThrow(DataBaseConstants.User_Number_Who_Follow_Me));

                    return true;
                }

                return false;
            }
            cursor.close();
        }catch (Exception e) {
            Log.d("Hata",e.toString());
            Log.d("Hataa",e.getMessage());
            return false;
        }
        return false;
    }
}
