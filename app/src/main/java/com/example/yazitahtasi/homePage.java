package com.example.yazitahtasi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class homePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

    }
    public void clickMyProfile(View v){
        Intent i =new Intent(homePage.this,myProfilePage.class);
        startActivity(i);
        finish();
    }

    public void clickSearchUserHome(View view){
        Intent i = new Intent(homePage.this, searchUserPage.class);
        startActivity(i);
        finish();
    }

    public void clickNewPostHome(View v){
        Intent i = new Intent(homePage.this, postPage.class);
        i.putExtra("referrer","HomePage");
        startActivity(i);
    }

}