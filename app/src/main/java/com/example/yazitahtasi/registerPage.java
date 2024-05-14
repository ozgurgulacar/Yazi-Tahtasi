package com.example.yazitahtasi;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.blogspot.atifsoftwares.circularimageview.CircularImageView;
import com.example.Classes.UserSingleton;
import com.github.dhaval2404.imagepicker.ImagePicker;

public class registerPage extends AppCompatActivity {
    private EditText name,surname,mail,number;
    private RadioButton male,female;
    CircularImageView imageView ;
    AlertDialog.Builder dialog;
    CheckBox checkBox;
    UserSingleton user = UserSingleton.getInstance();

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
        imageView=findViewById(R.id.btnPhoto);
        checkBox=findViewById(R.id.checkBox);
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

        //Fotoğraf Kontrolü
        if (user.getPhoto()==null){
            canNavigateToRegisterPage2 = false;
            t.setText("Lütfen Bir Fotoğraf Seçiniz");
            t.show();
        }


        //Tüm kontrollerden Geçtiyse Sonraki Sayfaya yönlendirir
        if(canNavigateToRegisterPage2) {
            if (checkBox.isChecked()){
                user.setSurName(surname.getText().toString());
                user.setName(name.getText().toString());
                user.setEmail(mail.getText().toString());
                user.setNumber(number.getText().toString());
                user.setMale(male.isChecked()? true:false);
                Intent i = new Intent(registerPage.this, registerPage2.class);
                startActivity(i);
            }
            else {
                Toast.makeText(this,"Lütfen Onay Veriniz",Toast.LENGTH_SHORT).show();
            }

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
        ImagePicker.with(this)
                .crop()	    			//Crop image(Optional), Check Customization for more option
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .start();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            imageView.setImageURI(uri);
            user.setPhoto((uri));
        }
        else{
            Toast.makeText(this,"Fotoğraf Seçilmedi",Toast.LENGTH_SHORT).show();
        }
    }

    private void alertDialog(){
        dialog=new AlertDialog.Builder(registerPage.this);
        dialog.setCancelable(false);
        dialog.setTitle("  ");
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