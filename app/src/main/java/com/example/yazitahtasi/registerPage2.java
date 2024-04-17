package com.example.yazitahtasi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.Classes.DataBaseHelper;
import com.example.Classes.UserSingleton;

public class registerPage2 extends AppCompatActivity {

    EditText userName, password;
    UserSingleton user = UserSingleton.getInstance();
    DataBaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page2);
        userName=findViewById(R.id.editUserName);
        password=findViewById(R.id.editPassword);
        dbHelper=new DataBaseHelper(this);
    }

    public void clickBackRegister2(View v){

        finish();
    }

    public void clickRegisterFinal(View v){


        user.setUserName(userName.getText().toString());
        user.setPassword(password.getText().toString());
        user.setNumberIFollow(0);
        user.setNumberWhoFollowMe(0);
        String l = dbHelper.addUser(user);
        if (l.equals("-1"))
            Toast.makeText(this,"Lütfen farklı bir kullanıcı Adı Deneyiniz",Toast.LENGTH_SHORT).show();
        else {
            AlertDialog.Builder dialog=new AlertDialog.Builder(registerPage2.this);
            dialog.setCancelable(false);
            dialog.setTitle("  ");
            dialog.setMessage("Başarılı Bir Şekilde Kayıt Olundu");
            dialog.setIcon(R.mipmap.ic_launcher_round);
            dialog.setPositiveButton("Ana Sayfaya Dön",new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent i = new Intent(registerPage2.this, loginPage.class);
                    startActivity(i);
                    finish();
                }
            });

        }
    }
}