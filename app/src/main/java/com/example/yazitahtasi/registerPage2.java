package com.example.yazitahtasi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.Classes.UserSingleton;

public class registerPage2 extends AppCompatActivity {

    EditText userName, password;
    UserSingleton user = UserSingleton.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page2);
        userName=findViewById(R.id.editUserName);
        password=findViewById(R.id.editPassword);

    }

    public void clickBackRegister2(View v){
        finish();
    }

    public void clickRegisterFinal(View v){
        //Database içerisinde UserName Var mı kontrolü yapılacak.
        //Kayıt Edilecek

    }
}