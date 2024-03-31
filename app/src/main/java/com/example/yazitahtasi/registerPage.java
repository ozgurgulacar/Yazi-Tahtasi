package com.example.yazitahtasi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class registerPage extends AppCompatActivity {
    private EditText name,surname,mail,number;
    private RadioButton male,female;
    AlertDialog.Builder dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
        name=findViewById(R.id.editName);
        surname=findViewById(R.id.editSurname);
        mail=findViewById(R.id.editEmail);
        number=findViewById(R.id.editNumber);
        male=findViewById(R.id.radioMale);
        female=findViewById(R.id.radioFemale);
    }

    public void clickContinueRegister(View v){
        Toast t = Toast.makeText(registerPage.this,"Lütfen Tüm Alanları Doldurunuz",Toast.LENGTH_SHORT);
        t.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL,0,0);
        boolean canNavigateToRegisterPage2=true;


        //Boş bir input var mı kontrolü yapılır
        if (name.getText().toString().equals("") || surname.getText().toString().equals("")
                        || mail.getText().toString().equals("")||number.getText().toString().equals("")){

            canNavigateToRegisterPage2=false;
            t.show();
        }

        //Mail inputu "@" ve "." içeriyor mu  kontrolü yapılır
        else if(!mail.getText().toString().contains("@") || !mail.getText().toString().contains(".")){
            canNavigateToRegisterPage2=false;
            t.setText("Lütfen Geçerli Bir Mail Giriniz");
            t.show();
        }

        //Cinsiyet Seçilmiş mi Kontrolü yapar
        else if(!male.isChecked() && !female.isChecked()) {
            canNavigateToRegisterPage2 = false;
            t.show();
        }
        //Number İnputunu gezer ve Number inputu Sadece Sayılardan mı oluşuyor Kontrolü yapar
        for (char c: number.getText().toString().toCharArray()){
            if (!Character.isDigit(c)){
                canNavigateToRegisterPage2=false;
                t.setText("Lütfen Geçerli Bir Numara Giriniz");
                t.show();
                break;
            }
        }

        //Tüm kontrollerden Geçtiyse Sonraki Sayfaya yönlendirir
        if(canNavigateToRegisterPage2) {
            Intent i = new Intent(registerPage.this, registerPage2.class);
            startActivity(i);
        }
    }

    public void clickBackRegister1(View v){
        alertDialog();
        dialog.create().show();
    }

    @Override
    public void onBackPressed(){
        alertDialog();
        dialog.create().show();
    }
    public void clickAddImageRegister(View v){

    }


    private void alertDialog(){
        dialog=new AlertDialog.Builder(registerPage.this);
        dialog.setCancelable(false);
        dialog.setTitle("???");
        dialog.setMessage("Geri Dönmek İstediğinize Emin Misiniz?");
        dialog.setIcon(R.mipmap.ic_launcher_round);
        dialog.setPositiveButton("EVET",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        dialog.setNegativeButton("HAYIR",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
    }
}