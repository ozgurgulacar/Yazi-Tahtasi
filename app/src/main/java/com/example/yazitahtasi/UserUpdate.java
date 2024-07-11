package com.example.yazitahtasi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.Classes.UserSingleton;
import com.example.Databases.DataBaseHelper;

public class UserUpdate extends AppCompatActivity {
    EditText ad,soyad,no,mail,parola;
    UserSingleton user = UserSingleton.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_update);

        ad=findViewById(R.id.txtAdUpdate);
        soyad=findViewById(R.id.txtSoyadUpdate);
        parola=findViewById(R.id.txtParolaUpdate);
        no=findViewById(R.id.txtNoUpdate);
        mail=findViewById(R.id.txtEmailUpdate);

        if (user==null){
            finish();
        }
        ad.setText(user.getName());
        soyad.setText(user.getSurName());
        parola.setText(user.getPassword());
        no.setText(user.getNumber());
        mail.setText(user.getEmail());

    }

    public void clickBackUpdate(View view) {
        Intent i = new Intent(this, myProfilePage.class);
        startActivity(i);
        finish();
    }

    public void clickUpdate(View view) {
        String adS=ad.getText().toString();
        String soyadS=soyad.getText().toString();
        String parolaS=parola.getText().toString();
        String noS=no.getText().toString();
        String mailS=mail.getText().toString();


        if (adS.isEmpty() || adS.equals(" ") || adS.equals("")){
            Toast.makeText(this,"Lütfen Adınızı Giriniz",Toast.LENGTH_SHORT).show();
            return;
        }
        if (soyadS.isEmpty() || soyadS.equals(" ") || soyadS.equals("")){
            Toast.makeText(this,"Lütfen Adınızı Giriniz",Toast.LENGTH_SHORT).show();
            return;
        }
        if (parolaS.isEmpty() || parolaS.equals(" ") || parolaS.equals("")){
            Toast.makeText(this,"Lütfen Adınızı Giriniz",Toast.LENGTH_SHORT).show();
            return;
        }
        if (noS.isEmpty() || noS.equals(" ") || noS.equals("")){
            Toast.makeText(this,"Lütfen Adınızı Giriniz",Toast.LENGTH_SHORT).show();
            return;
        }
        if (mailS.isEmpty() || mailS.equals(" ") || mailS.equals("")){
            Toast.makeText(this,"Lütfen Adınızı Giriniz",Toast.LENGTH_SHORT).show();
            return;
        }



        user.setName(adS);
        user.setSurName(soyadS);
        user.setNumber(noS);
        user.setPassword(parolaS);
        user.setEmail(mailS);


        DataBaseHelper db = new DataBaseHelper(this);
        db.updateUser();
        Toast.makeText(this,"Başarıyla Güncellendi",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, myProfilePage.class);
        startActivity(i);
        finish();
    }
}