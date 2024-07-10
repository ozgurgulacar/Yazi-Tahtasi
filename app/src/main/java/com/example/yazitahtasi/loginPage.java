package com.example.yazitahtasi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.Databases.DataBaseHelper;

public class loginPage extends AppCompatActivity {
    private EditText userName,password;
    DataBaseHelper db;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        userName=findViewById(R.id.editUserName);
        password=findViewById(R.id.editPassword);


        sharedPreferences=this.getSharedPreferences("com.example.yazitahtasi.SHARED_PREFERENCES", Context.MODE_PRIVATE);
        db=new DataBaseHelper(this);
    }
    public void clickLogin(View v){


        //userName ve password EditTextlerinin Boş olup olmadığı kontrol edildi
        if (!userName.getText().toString().isEmpty() && !password.getText().toString().isEmpty()) {
            //Giriş Başarılı mı kontrolü yapıldı
            //Başarılı ise homePageye yönlendirildi
            String deneme=db.isLoginSuccesful(userName.getText().toString(), password.getText().toString());
            if (deneme.equals("true")) {
                Intent i = new Intent(loginPage.this, homePage.class);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("userName",userName.getText().toString());
                editor.putString("password",password.getText().toString());
                editor.putBoolean("isActive",true);
                editor.apply();

                startActivity(i);
                finish();
            }
            else if (deneme.equals("Parola Hatası")){
                Toast t = Toast.makeText(loginPage.this, "Hatalı Parola", Toast.LENGTH_LONG);
                t.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                t.show();
            }
            else if (deneme.equals("Böyle biri yok")){
                Toast t = Toast.makeText(loginPage.this, "HATALI GİRİŞ YAPTINIZ", Toast.LENGTH_LONG);
                t.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                t.show();
            }
            //Değilse Toast Mesajı verildi
            else {
                Toast t = Toast.makeText(loginPage.this, "HATALI GİRİŞ YAPTINIZ", Toast.LENGTH_LONG);
                t.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                t.show();
            }
        }
        else{
            Toast t = Toast.makeText(loginPage.this, "Lütfen Tüm alanları Doldurunuz", Toast.LENGTH_LONG);
            t.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            t.show();
        }
    }

    public void clickRegister(View v){
        Intent i = new Intent(loginPage.this, registerPage.class);
        startActivity(i);

    }
} 