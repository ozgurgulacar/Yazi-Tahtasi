package com.example.yazitahtasi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class loginPage extends AppCompatActivity {
    private EditText userName,password;
    private Button btnLogin,btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        btnLogin=findViewById(R.id.btnLogin);
        btnRegister=findViewById(R.id.btnRegister);
        userName=findViewById(R.id.editUserName);
        password=findViewById(R.id.editPassword);
    }
    public void clickLogin(View v){

        if (userName.getText().toString().equals("DENEME") && password.getText().toString().equals("123")){
            Intent i = new Intent(loginPage.this, homePage.class);
            startActivity(i);
            finish();
        }

        else{
            Toast t = Toast.makeText(loginPage.this,"HATALI GİRİŞ YAPTINIZ",Toast.LENGTH_LONG);
            t.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL,0,0);
            t.show();
        }
    }
    public void clickRegister(View v){
        Intent i = new Intent(loginPage.this, registerPage.class);
        startActivity(i);
    }
}