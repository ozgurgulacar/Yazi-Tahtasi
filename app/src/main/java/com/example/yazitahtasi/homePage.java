package com.example.yazitahtasi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class homePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

    }
    public void clickMyProfileHomePage(View v){
        Intent i =new Intent(homePage.this,myProfilePage.class);
        startActivity(i);
        finish();
    }

    public void clickSearchUserHome(View view){
        Intent i = new Intent(homePage.this, searchUserPage.class);
        startActivity(i);
        finish();
    }
    public void clickSearchWordHome(View v){

    }
    public void clickExitHomePage (View v){
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

    public void clickNewPostHome(View v){
        Intent i = new Intent(homePage.this, postPage.class);
        i.putExtra("referrer","HomePage");
        startActivity(i);
    }

}