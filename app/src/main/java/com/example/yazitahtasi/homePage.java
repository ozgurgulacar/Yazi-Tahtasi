package com.example.yazitahtasi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.BaseAdapters.AdapterPosts;
import com.example.Classes.Article;
import com.example.Classes.DataBaseHelper;
import com.example.Classes.User;
import com.example.Classes.UserSingleton;

import java.util.ArrayList;

public class homePage extends AppCompatActivity {

    ListView liste;
    DataBaseHelper db;
    ArrayList<String> followIds = new ArrayList<>();
    ArrayList<String> followUserNames = new ArrayList<>();
    ArrayList<String> articleIds = new ArrayList<>();
    ArrayList<Article> articles = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        db = new DataBaseHelper(this);
        liste = findViewById(R.id.listViewHomePage);
        liste.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String articleId=String.valueOf(articles.get(position).getArticleId());
                int userId=db.whoWriteThisPost(articleId);
                User user=db.getUserWithId(userId);


                Intent i = new Intent(homePage.this, DetailPost.class);

                i.putExtra("userPhoto", String.valueOf(user.getPhoto()));
                i.putExtra("userName", user.getName() + " " + user.getSurName());
                i.putExtra("userUniqueName", user.getUserName());

                i.putExtra("postHeaderDetail", articles.get(position).getArticleTitle());
                i.putExtra("postContentDetail", articles.get(position).getArticleContent());
                i.putExtra("postAverageScore",articles.get(position).getAverageScore());
                i.putExtra("postNumberOfScore",String.valueOf(articles.get(position).getNumberOfScores()));
                i.putExtra("articleId",articleId);

                startActivity(i);

            }
        });

        fillListView();

    }


    public void clickMyProfileHomePage(View v) {
        Intent i = new Intent(homePage.this, myProfilePage.class);
        startActivity(i);
        finish();
    }

    public void clickSearchUserHome(View view) {
        Intent i = new Intent(homePage.this, searchUserPage.class);
        startActivity(i);
        finish();
    }

    public void clickSearchWordHome(View v) {
        Intent i = new Intent(homePage.this, webView.class);
        startActivity(i);
    }

    public void clickExitHomePage(View v) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Emin Misiniz");
        alert.setMessage("Çıkış yapmak istediğinize Emin misiniz?");
        alert.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(homePage.this, loginPage.class);
                startActivity(i);
                finish();
            }
        });

        alert.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert.create().show();
    }

    public void clickNewPostHome(View v) {
        Intent i = new Intent(homePage.this, postPage.class);
        i.putExtra("referrer", "HomePage");
        startActivity(i);
    }


    private void checkAndGetArticle() {
        if (UserSingleton.getInstance().getUserName().isEmpty())
            return;

        try {

            followUserNames = db.getMyAllFollowsUserName();

            if (followUserNames.isEmpty())
                return;

            followIds = db.getMyAllFollowsId(followUserNames);

            if (followIds.isEmpty())
                return;

            articleIds = db.getArticlesId(followIds);

            if (articleIds.isEmpty())
                return;

            articles = db.getAllArticle(articleIds);

        } catch (Exception e) {
            Toast.makeText(this, "Bir Hata ile Karşılaştık", Toast.LENGTH_SHORT).show();
        }


    }


    private void fillListView() {
        checkAndGetArticle();
        if (!articles.isEmpty()) {
            AdapterPosts adapterPosts = new AdapterPosts(this, articles);
            liste.setAdapter(adapterPosts);
        } else {
            Log.d("HATa", "checkAndGetArticle: articles");
        }
    }

    public void clickGetArticlesHomePage(View view) {
        fillListView();
    }
}