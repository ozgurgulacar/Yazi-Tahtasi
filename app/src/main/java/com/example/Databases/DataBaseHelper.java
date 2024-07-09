package com.example.Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.Classes.Article;
import com.example.Classes.User;
import com.example.Classes.UserSingleton;

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
        db.execSQL(DataBaseConstants.CREATE_TABLE_Follows);
        db.execSQL(DataBaseConstants.CREATE_TABLE_Ratings);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Articles");
        db.execSQL("DROP TABLE IF EXISTS Users");
        db.execSQL("DROP TABLE IF EXISTS ArticleUser");
        db.execSQL("DROP TABLE IF EXISTS Follows");
        db.execSQL("DROP TABLE IF EXISTS Ratings");

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

    public String addFollow(String myUserName,String otherUserName){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DataBaseConstants.User_Name_Follower,myUserName);
        values.put(DataBaseConstants.Followed_User_Name,otherUserName);
        try{
            return String.valueOf(db.insert("Follows",null,values));
        }catch (Exception e){
            return "-1";
        }

    }

    public void deleteFollow(String otherUser){
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = DataBaseConstants.User_Name_Follower +" = ? AND "+ DataBaseConstants.Followed_User_Name +" = ?";
        String[] whereArgs = {UserSingleton.getInstance().getUserName(),otherUser};

        db.delete("Follows",whereClause,whereArgs);
    }

    public void updateOtherUserFollow(String newValue,String otherUserName){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DataBaseConstants.User_Number_Who_Follow_Me,Integer.parseInt(newValue));

        String whereClause = DataBaseConstants.User_Name_Unique +" = ?";
        String[] whereArgs = {otherUserName};

        int rowsUpdated = db.update("Users",values,whereClause,whereArgs);

    }

    public void updateCurrentUserFollow(){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DataBaseConstants.User_Number_I_Follow,UserSingleton.getInstance().getNumberIFollow());

        String whereClause = DataBaseConstants.User_Name_Unique +" = ?"; //
        String[] whereArgs = {UserSingleton.getInstance().getUserName()}; //

        int rowsUpdated = db.update("Users",values,whereClause,whereArgs);
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

            values.put(DataBaseConstants.Article_Number_Scores,0);
            values.put(DataBaseConstants.Article_Title,article.getArticleTitle());
            values.put(DataBaseConstants.Article_Content,article.getArticleContent());
            values.put(DataBaseConstants.Article_Average_Scores, "0");

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
            return false;
        }
    }

    public void updateRating(Article article){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DataBaseConstants.Article_Number_Scores,article.getNumberOfScores());
        values.put(DataBaseConstants.Article_Average_Scores,article.getAverageScore());

        String whereClause = DataBaseConstants.Article_Id +" = ?"; //
        String[] whereArgs = {String.valueOf(article.getArticleId())}; //

        int rowsUpdated = db.update("Articles",values,whereClause,whereArgs);
    }

    public void updateArticle(String Id,String content,String title){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DataBaseConstants.Article_Title,title);
        values.put(DataBaseConstants.Article_Content,content);

        String whereClause = DataBaseConstants.Article_Id +" = ?"; //
        String[] whereArgs = {String.valueOf(Id)}; //

        int rowsUpdated = db.update("Articles",values,whereClause,whereArgs);
    }

    public void deleteArticle(String Id){
        SQLiteDatabase db = this.getWritableDatabase();

        String whereClause = DataBaseConstants.Article_Id +" = ?";
        String[] whereArgs = {Id};
        db.delete("Articles",whereClause,whereArgs);

        whereClause=DataBaseConstants.Post_Id +" = ?";
        whereArgs[0] = Id;
        db.delete("Ratings",whereClause,whereArgs);

        whereClause=DataBaseConstants.ArticleUser_article_id +" = ?";
        whereArgs[0] = Id;
        db.delete("ArticleUser",whereClause,whereArgs);

    }

    public void addRating(String userName,String userNameRating,String postId,String score){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();


        values.put(DataBaseConstants.User_Name_Post,userName);
        values.put(DataBaseConstants.User_Name_Rating,userNameRating);
        values.put(DataBaseConstants.Score_Post, score);
        values.put(DataBaseConstants.Post_Id, postId);

        long id=db.insert("Ratings",null,values);
    }

    public String getAmIScored(String postId){
        SQLiteDatabase db = this.getReadableDatabase();

        String query = DataBaseConstants.User_Name_Rating+ "= ? AND "+DataBaseConstants.Post_Id +"= ?";
        String queryValue[] = {String.valueOf(UserSingleton.getInstance().getUserName()),postId};
        String returns[] = {DataBaseConstants.Score_Post};

        Cursor cursor = db.query("Ratings",returns,query,queryValue,null,null,null);

        String donus="Puanlanmadı";

        if (cursor.moveToFirst()) {
            donus=cursor.getString(cursor.getColumnIndexOrThrow(DataBaseConstants.Score_Post));
        }
        return donus;

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

    public String isfollow(String myUserId, String otherUserId){
        SQLiteDatabase db = getReadableDatabase();

        String returns[]= {DataBaseConstants.Follows_Id};
        String query = DataBaseConstants.User_Name_Follower + "= ? AND "+DataBaseConstants.Followed_User_Name + "= ?";
        String queryValue[] = {myUserId,otherUserId};

        Cursor cursor = db.query("Follows",returns,query,queryValue,null,null,null);
        if (cursor.moveToFirst())
            return String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseConstants.Follows_Id)));

        return "null";
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
                String returnvalues[] = {DataBaseConstants.Article_Id,DataBaseConstants.Article_Title,DataBaseConstants.Article_Content,DataBaseConstants.Article_Average_Scores,DataBaseConstants.Article_Number_Scores};
                for (int i =0;i<id_Articles.size();i++){
                    String nowQuery[]={queryValues[i]};

                    Cursor cursor2 = db.query("Articles",returnvalues,query,nowQuery,null,null,null);
                    if (cursor2.moveToFirst()){
                        do {
                            Article article = new Article();
                            article.setArticleId(cursor2.getInt(cursor2.getColumnIndexOrThrow(DataBaseConstants.Article_Id)));
                            article.setArticleTitle(cursor2.getString(cursor2.getColumnIndexOrThrow(DataBaseConstants.Article_Title)));
                            article.setArticleContent(cursor2.getString(cursor2.getColumnIndexOrThrow(DataBaseConstants.Article_Content)));
                            article.setAverageScore(cursor2.getString(cursor2.getColumnIndexOrThrow(DataBaseConstants.Article_Average_Scores)));
                            Log.d("TAG", "getUserPosts: "+cursor2.getString(cursor2.getColumnIndexOrThrow(DataBaseConstants.Article_Average_Scores)));
                            article.setNumberOfScores(cursor2.getInt(cursor2.getColumnIndexOrThrow(DataBaseConstants.Article_Number_Scores)));
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
                String returnvalues[] = {DataBaseConstants.Article_Id,DataBaseConstants.Article_Title,DataBaseConstants.Article_Content,DataBaseConstants.Article_Average_Scores,DataBaseConstants.Article_Number_Scores};
                for (int i =0;i<id_Articles.size();i++){
                    String nowQuery[]={queryValues[i]};

                    Cursor cursor2 = db.query("Articles",returnvalues,query,nowQuery,null,null,null);
                    if (cursor2.moveToFirst()){
                        do {
                            Article article = new Article();
                            article.setArticleId(cursor2.getInt(cursor2.getColumnIndexOrThrow(DataBaseConstants.Article_Id)));
                            article.setArticleTitle(cursor2.getString(cursor2.getColumnIndexOrThrow(DataBaseConstants.Article_Title)));
                            article.setArticleContent(cursor2.getString(cursor2.getColumnIndexOrThrow(DataBaseConstants.Article_Content)));
                            article.setAverageScore(cursor2.getString(cursor2.getColumnIndexOrThrow(DataBaseConstants.Article_Average_Scores)));
                            article.setNumberOfScores(cursor2.getInt(cursor2.getColumnIndexOrThrow(DataBaseConstants.Article_Number_Scores)));
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

    public ArrayList<String> getMyAllFollowsUserName(){
        ArrayList<String> followsUserName= new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        String returns[]= {DataBaseConstants.Followed_User_Name};
        String query = DataBaseConstants.User_Name_Follower + "= ?";
        String queryValue[] = {UserSingleton.getInstance().getUserName().toString()};

        Cursor cursor = db.query("Follows",returns,query,queryValue,null,null,null);

        if (cursor.moveToFirst()){
            do {
                followsUserName.add(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseConstants.Followed_User_Name)));
            }while (cursor.moveToNext());
        }
        return followsUserName;
    }

    public ArrayList<String> getMyAllFollowsId(ArrayList<String> followsUserName){
        ArrayList<String> followsId= new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        String returns[]= {DataBaseConstants.User_Id};
        String query = DataBaseConstants.User_Name_Unique + "= ?";
        String queryValue[] = {followsUserName.get(0)};
        for (int i = 0 ; i<followsUserName.size();i++){
            queryValue[0] = followsUserName.get(i);
            Cursor cursor= db.query("Users",returns,query,queryValue,null,null,null);
            if (cursor.moveToFirst()){
                cursor.moveToFirst();
                followsId.add(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseConstants.User_Id)));
            }
        }
        return followsId;
    }

    public ArrayList<String> getArticlesId(ArrayList<String> followsId){
        ArrayList<String> articlesId=new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();


        StringBuilder inClause = new StringBuilder();
        String[] queryValues = new String[followsId.size()];

        for (int i = 0; i < followsId.size(); i++) {
            queryValues[i] = followsId.get(i);
            inClause.append("?");
            if (i < followsId.size() - 1) {
                inClause.append(",");
            }
        }

        String query = DataBaseConstants.ArticleUser_user_id + " IN (" + inClause.toString() + ")";
        String[] returns = {DataBaseConstants.ArticleUser_article_id, DataBaseConstants.ArticleUser_Date_Save};
        String sortOrder = DataBaseConstants.ArticleUser_Date_Save + " DESC";

        Cursor cursor = db.query(
                "ArticleUser",   // The table to query
                returns,                                    // The array of columns to return (pass null to get all)
                query,                                      // The columns for the WHERE clause
                queryValues,                                // The values for the WHERE clause
                null,                                       // Don't group the rows
                null,                                       // Don't filter by row groups
                sortOrder                                   // The sort order
        );

        if (cursor != null) {
            while (cursor.moveToNext()) {
                articlesId.add(String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseConstants.ArticleUser_article_id))));

            }
            cursor.close();
        }

        return articlesId;


    }

    public ArrayList<Article> getAllArticle(ArrayList<String> articlesId){
        ArrayList<Article> articles = new ArrayList<>();

        SQLiteDatabase db= this.getReadableDatabase();

        String returns[]= {DataBaseConstants.Article_Title,DataBaseConstants.Article_Content,DataBaseConstants.Article_Number_Scores,DataBaseConstants.Article_Average_Scores};
        String query = DataBaseConstants.Article_Id + "= ?";
        String queryValue[] = {articlesId.get(0)};
        for (int i = 0 ; i<articlesId.size();i++){
            queryValue[0] = articlesId.get(i);
            Cursor cursor= db.query("Articles",returns,query,queryValue,null,null,null);
            if (cursor.moveToFirst()){
                cursor.moveToFirst();
                Article a = new Article();
                a.setArticleId(Integer.parseInt(articlesId.get(i)));
                a.setArticleTitle(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseConstants.Article_Title)));
                a.setArticleContent(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseConstants.Article_Content)));
                a.setAverageScore(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseConstants.Article_Average_Scores)));
                a.setNumberOfScores(cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseConstants.Article_Number_Scores)));
                articles.add(a);

            }
        }

        return articles;
    }

    public int whoWriteThisPost(String Id){
        int userId = -1;

        SQLiteDatabase db = this.getReadableDatabase();

        String query = DataBaseConstants.ArticleUser_article_id+ "= ?";
        String queryValue[] = {String.valueOf(Id)};
        String returns[] = {DataBaseConstants.ArticleUser_user_id};

        Cursor cursor = db.query("ArticleUser",returns,query,queryValue,null,null,null);

        if (cursor.moveToFirst()){
            cursor.moveToFirst();
            userId=cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseConstants.ArticleUser_user_id));
        }
        return userId;
    }

    public User getUserWithId(int userId){
        User user = new User();

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String query = DataBaseConstants.User_Id+ "= ?";
            String queryValue[] = {String.valueOf(userId)};
            String returns[] = {DataBaseConstants.User_Name_Unique,DataBaseConstants.User_Name,DataBaseConstants.User_SurName,
                    DataBaseConstants.User_Photo_Uri,DataBaseConstants.User_Number_Who_Follow_Me,DataBaseConstants.User_Number_I_Follow};
            Cursor cursor = db.query("Users",returns,query,queryValue,null,null,null);
            if (cursor.moveToFirst()){
                cursor.moveToFirst();
                user.setName(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseConstants.User_Name)));

                user.setSurName(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseConstants.User_SurName)));

                user.setUserId(userId);

                String uri = (cursor.getString(cursor.getColumnIndexOrThrow(DataBaseConstants.User_Photo_Uri)));
                user.setPhoto(Uri.parse(uri));

                user.setUserName(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseConstants.User_Name_Unique)));

                user.setNumberIFollow(cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseConstants.User_Number_I_Follow)));

                user.setNumberWhoFollowMe(cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseConstants.User_Number_Who_Follow_Me)));

            }

        }catch (Exception e){
            user.setName("HataKodu=1024!");
        }


        return user;
    }


}
