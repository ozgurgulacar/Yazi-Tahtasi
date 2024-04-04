package com.example.yazitahtasi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.Classes.DataBaseHelper;

public class loginPage extends AppCompatActivity {
    private EditText userName,password;
    DataBaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        userName=findViewById(R.id.editUserName);
        password=findViewById(R.id.editPassword);

        db=new DataBaseHelper(this);
    }
    public void clickLogin(View v){
        //userName ve password EditTextlerinin Boş olup olmadığı kontrol edildi
        if (!userName.getText().toString().isEmpty() && !password.getText().toString().isEmpty()) {
            //Giriş Başarılı mı kontrolü yapıldı
            //Başarılı ise homePageye yönlendirildi
            if (db.isLoginSuccesful(userName.getText().toString(), password.getText().toString())) {
                Intent i = new Intent(loginPage.this, homePage.class);
                startActivity(i);
                finish();
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