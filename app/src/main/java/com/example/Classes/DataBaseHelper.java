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




    public User getUser(String userName){
        User user = new User();

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String query = DataBaseConstants.User_Name_Unique+ "= ?";
            String queryValue[] = {String.valueOf(userName)};
            String returns[] = {DataBaseConstants.User_Id,DataBaseConstants.User_Name,DataBaseConstants.User_SurName,
                    DataBaseConstants.User_Photo_Uri,DataBaseConstants.User_Number_Who_Follow_Me,DataBaseConstants.User_Number_I_Follow};
            Cursor cursor = db.query("Users",returns,query,queryValue,null,null,null);
            if (cursor.moveToFirst()){
                cursor.moveToFirst();
                user.setName(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseConstants.User_Name)));

                user.setSurName(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseConstants.User_SurName)));

                user.setUserId(cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseConstants.User_Id)));

                String uri = (cursor.getString(cursor.getColumnIndexOrThrow(DataBaseConstants.User_Photo_Uri)));
                user.setPhoto(Uri.parse(uri));

                user.setUserName(userName);

                user.setNumberIFollow(cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseConstants.User_Number_I_Follow)));

                user.setNumberWhoFollowMe(cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseConstants.User_Number_Who_Follow_Me)));

            }

        }catch (Exception e){
            user.setName("HataKodu=1024!");
        }


        return user;
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
            Log.d("kayıtolurken2","IFollow "+user.getNumberIFollow()+" WhoFollow "+ user.getNumberWhoFollowMe());


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
            values.put(DataBaseConstants.Article_Is_Update, article.getUpdated());

            long id=db.insert("Articles",null,values);


            //ArticleUser Tablosuna Ekleme Yapar
            UserSingleton user=UserSingleton.getInstance();

            ContentValues values2 = new ContentValues();

            values2.put(DataBaseConstants.ArticleUser_article_id,id);
            values2.put(DataBaseConstants.ArticleUser_user_id,user.getUserId());
            values2.put(DataBaseConstants.ArticleUser_Date_Save,String.valueOf(System.currentTimeMillis()));

            db.insert("ArticleUser",null,values2);
            db.close();

            return true;
        }catch (Exception e){
            Log.d("HataArticle",e.toString());
            Log.d("HataArticles",e.getMessage());
            return false;
        }
    }

    public List<User> searchPeople(String queryValue){
        queryValue = queryValue.substring(3);
        List<User> users = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String sorgu="SELECT * from Users where "+queryValue;
        Cursor cursor=db.rawQuery(sorgu,null);
        if (cursor.moveToFirst()){
            do {
                User user = new User();
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
            User user = new User();
            user.setName("Kimseyi Bulamadı");
            users.add(user);
        }

        cursor.close();

        return users;

    }



    public List<Article> getUserPosts(int userId){
        List<Article> articles = new ArrayList<>();
        try {
            SQLiteDatabase db = this.getReadableDatabase();

            String query = DataBaseConstants.ArticleUser_user_id+ "= ?";
            String queryValue[] = {String.valueOf(userId)};
            String returns[] = {DataBaseConstants.ArticleUser_article_id,DataBaseConstants.ArticleUser_Date_Save};
            String sortOrder = DataBaseConstants.ArticleUser_Date_Save + " DESC";

            Cursor cursor = db.query("ArticleUser",returns,query,queryValue,null,null,sortOrder);

            List<String> id_Articles = new ArrayList<>();
            if (cursor.moveToFirst()){
                do {
                    id_Articles.add(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseConstants.ArticleUser_article_id)));
                }while (cursor.moveToNext());
                //Buraya Kadar Yazılarımızın İDLERİNİ aldık. Artık Bu İDlerden Yazıları bulup yazıları geri döndürmemiz gerekiyor.
                query = DataBaseConstants.Article_Id+ "= ?";
                String[] queryValues = new String[id_Articles.size()];
                for (int i = 0; i < id_Articles.size(); i++) {
                    queryValues[i] = id_Articles.get(i);
                }
                String returnvalues[] = {DataBaseConstants.Article_Id,DataBaseConstants.Article_Title,DataBaseConstants.Article_Content,DataBaseConstants.Article_Is_Update};
                for (int i =0;i<id_Articles.size();i++){
                    String nowQuery[]={queryValues[i]};

                    Cursor cursor2 = db.query("Articles",returnvalues,query,nowQuery,null,null,null);
                    if (cursor2.moveToFirst()){
                        do {
                            Article article = new Article();
                            article.setArticleId(cursor2.getInt(cursor2.getColumnIndexOrThrow(DataBaseConstants.Article_Id)));
                            article.setArticleTitle(cursor2.getString(cursor2.getColumnIndexOrThrow(DataBaseConstants.Article_Title)));
                            article.setArticleContent(cursor2.getString(cursor2.getColumnIndexOrThrow(DataBaseConstants.Article_Content)));
                            article.setUpdated(cursor2.getString(cursor2.getColumnIndexOrThrow(DataBaseConstants.Article_Is_Update)));
                            articles.add(article);
                        }while (cursor2.moveToNext());
                    }
                }

            }
            else{
                Article article = new Article();
                article.setArticleTitle("KAYIT BULUNAMADI");
                articles.add(article);
            }
            cursor.close();
            return articles;
        }catch (Exception e){
            Log.d("HataGetArticles",e.toString());
            Log.d("HataaGetArticles",e.getMessage());
            return articles;
        }

    }
    public List<Article> getMyPosts(){
        List<Article> articles = new ArrayList<>();
        try{


            SQLiteDatabase db = this.getReadableDatabase();

            String query = DataBaseConstants.ArticleUser_user_id+ "= ?";
            String queryValue[] = {String.valueOf(UserSingleton.getInstance().getUserId())};
            String returns[] = {DataBaseConstants.ArticleUser_article_id,DataBaseConstants.ArticleUser_Date_Save};
            String sortOrder = DataBaseConstants.ArticleUser_Date_Save + " DESC";


            Cursor cursor = db.query("ArticleUser",returns,query,queryValue,null,null,sortOrder);

            List<String> id_Articles = new ArrayList<>();
            if (cursor.moveToFirst()){
                do {
                    id_Articles.add(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseConstants.ArticleUser_article_id)));
                }while (cursor.moveToNext());
                //Buraya Kadar Yazılarımızın İDLERİNİ aldık. Artık Bu İDlerden Yazıları bulup yazıları geri döndürmemiz gerekiyor.
                query = DataBaseConstants.Article_Id+ "= ?";
                String[] queryValues = new String[id_Articles.size()];
                for (int i = 0; i < id_Articles.size(); i++) {
                    queryValues[i] = id_Articles.get(i);
                }
                String returnvalues[] = {DataBaseConstants.Article_Id,DataBaseConstants.Article_Title,DataBaseConstants.Article_Content,DataBaseConstants.Article_Is_Update};
                for (int i =0;i<id_Articles.size();i++){
                    String nowQuery[]={queryValues[i]};

                    Cursor cursor2 = db.query("Articles",returnvalues,query,nowQuery,null,null,null);
                    if (cursor2.moveToFirst()){
                        do {
                            Article article = new Article();
                            article.setArticleId(cursor2.getInt(cursor2.getColumnIndexOrThrow(DataBaseConstants.Article_Id)));
                            article.setArticleTitle(cursor2.getString(cursor2.getColumnIndexOrThrow(DataBaseConstants.Article_Title)));
                            article.setArticleContent(cursor2.getString(cursor2.getColumnIndexOrThrow(DataBaseConstants.Article_Content)));
                            //String dateString = cursor2.getString(cursor2.getColumnIndexOrThrow(DataBaseConstants.Article_Date_Save));
                            //article.setDateSave(new Date(dateString));
                            article.setUpdated(cursor2.getString(cursor2.getColumnIndexOrThrow(DataBaseConstants.Article_Is_Update)));
                            articles.add(article);
                        }while (cursor2.moveToNext());
                    }
                }

            }
            else{
                Article article = new Article();
                article.setArticleTitle("KAYIT BULUNAMADI");
                articles.add(article);
            }
            cursor.close();
            return articles;
        }catch (Exception e){
            Log.d("HataGetArticles",e.toString());
            Log.d("HataaGetArticles",e.getMessage());
            return articles;
        }

    }
    public String getArticlePhotoUri(int articleId){
        SQLiteDatabase db = getReadableDatabase();

        String returns[]= {DataBaseConstants.ArticleUser_user_id};
        String query = DataBaseConstants.ArticleUser_article_id + "= ?";
        String queryValue[] = {String.valueOf(articleId)};
        Cursor cursor = db.query("ArticleUser", returns, query, queryValue, null, null, null);
        int  userId=-1;
        if (cursor.moveToFirst()){
            cursor.moveToFirst();
            userId =  cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseConstants.ArticleUser_user_id));
        }

        String returns2[] = {DataBaseConstants.User_Photo_Uri};
        String query2 = DataBaseConstants.User_Id + "= ?";
        String queryValue2[] = {String.valueOf(userId)};

        Cursor cursor2 = db.query("Users", returns2, query2, queryValue2, null, null, null);
        try{
            if (cursor2.moveToFirst()){
                cursor2.moveToFirst();
                String uri = cursor2.getString(cursor2.getColumnIndexOrThrow(DataBaseConstants.User_Photo_Uri));
                db.close();
                return uri;
            }
        }catch (Exception e){
            Log.d("hataphoto",e.getMessage());
        }
        cursor2.close();
        db.close();
        return "Fotoğraf Bulunamadı";
    }

    public String isLoginSuccesful(String userName,String password){
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
                    user.setNumberIFollow(cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseConstants.User_Number_I_Follow)));
                    user.setNumberWhoFollowMe(cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseConstants.User_Number_Who_Follow_Me)));

                    return "true";
                }

                return "Parola Hatası";
            }
            cursor.close();
        }catch (Exception e) {
            return "Hata Fırlattık";
        }
        return "Böyle biri yok";
    }
}
