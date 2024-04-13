package com.example.yazitahtasi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.example.BaseAdapters.AdapterPosts;
import com.example.Classes.Article;
import com.example.Classes.DataBaseHelper;

import java.util.List;

public class myProfilePage extends AppCompatActivity {

    DataBaseHelper db;
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile_page);
        Log.d("SetSonrası","SetSonrası");
        db = new DataBaseHelper(this);
        Log.d("DB Sonrası","toString");
        listView = findViewById(R.id.listViewMyProfilePage);

        myPosts();

    }

    public void clickHomePage(View v){
        Intent i =new Intent(myProfilePage.this,homePage.class);
        startActivity(i);
        finish();

    }

    public void clickMyWordsMyProfil(View v){
        myPosts();
    }




    public void myPosts(){
        try {
            List<Article> articles = db.getMyPosts();
            AdapterPosts adapterPosts = new AdapterPosts(getApplicationContext(),articles);
            listView.setAdapter(adapterPosts);
        }catch (Exception e){
            Log.d("HataGetArticlesProfil",e.toString());
            Log.d("HataaGetArticlesProfil",e.getMessage());
        }
    }
}