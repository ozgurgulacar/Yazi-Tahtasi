package com.example.yazitahtasi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.example.Databases.DataBaseHelper;

public class MainActivity extends AppCompatActivity {
    Handler h = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences preferences = this.getSharedPreferences("com.example.yazitahtasi.SHARED_PREFERENCES",Context.MODE_PRIVATE);
        DataBaseHelper db = new DataBaseHelper(this);


        if (preferences.getBoolean("isActive", false)==false) {

            h.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(MainActivity.this, loginPage.class);
                    startActivity(i);
                    finish();
                }
            }, 2000);
        }else{
            String deneme = db.isLoginSuccesful(preferences.getString("userName"," "),
                    preferences.getString("password"," "));



            if(deneme.equals("true")) {
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(MainActivity.this, homePage.class);
                        startActivity(i);
                        finish();
                    }
                }, 2000);
            }else{
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("isActive",false);
                editor.apply();
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(MainActivity.this, loginPage.class);

                        startActivity(i);
                        finish();
                    }
                }, 2000);
            }


        }




    }
}