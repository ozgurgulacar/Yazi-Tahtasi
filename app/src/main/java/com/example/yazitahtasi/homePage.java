package com.example.yazitahtasi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.Classes.DataBaseHelper;

public class homePage extends AppCompatActivity {
    DataBaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        db=new DataBaseHelper(this);
    }
    public void clickMyProfile(View v){
        Intent i =new Intent(homePage.this,myProfilePage.class);
        startActivity(i);
        finish();
    }
}