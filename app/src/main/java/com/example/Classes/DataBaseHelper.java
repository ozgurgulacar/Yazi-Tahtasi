package com.example.Classes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {

    public DataBaseHelper(@Nullable Context context) {
            super(context, DataBaseConstants.DATABASE_NAME, null, DataBaseConstants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DataBaseConstants.CREATE_TABLE_Articles);
        db.execSQL(DataBaseConstants.CREATE_TABLE_Users);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Articles");
        db.execSQL("DROP TABLE IF EXISTS Users");

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

    public boolean isLoginSuccesful(String userName,String password){
        try {
            SQLiteDatabase db = getReadableDatabase();

            String returns[] = {DataBaseConstants.User_Password};
            String query = DataBaseConstants.User_Name_Unique + "= ?";
            String queryValue[] = {userName};

            Cursor cursor = db.query("Users", returns, query, queryValue, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                String realPassword = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseConstants.User_Password));
                cursor.close();
                if (realPassword.equals(password))
                    return true;
                return false;
            }
            cursor.close();
        }catch (Exception e) {
            return false;
        }
        return false;
    }
}
