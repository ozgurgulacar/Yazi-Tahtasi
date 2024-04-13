package com.example.yazitahtasi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.Classes.Article;
import com.example.Classes.DataBaseHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class postPage extends AppCompatActivity {
    Button btnCancel,btnShare;
    EditText txtTitle,txtContent;
    String referrer;
    Article article;
    DataBaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_page);
        btnCancel=findViewById(R.id.btnCancelPost);
        btnShare=findViewById(R.id.btnSharePost);
        db= new DataBaseHelper(this);
        txtTitle=findViewById(R.id.txtPostTitlePost);
        txtContent=findViewById(R.id.txtPostDetailPost);



        Intent i = getIntent();


        referrer=i.getStringExtra("referrer");
        if (referrer.equals("HomePage")){
            btnCancel.setText("İptal Et");
            btnShare.setText("Paylaş");
            article=new Article();
        }
        else if(referrer.equals("DetailsMyPosts")){
            btnCancel.setText("SİL");
            btnShare.setText("Güncelle");
            //article Nesnesine Referans Atacanak ve Yazı İçerikleri Eklenecek
        }
    }
    public void clickShareOrUpdatePost(View v) {
        //Başlık Ve İçerik Boş Değilse
        if (!txtContent.getText().toString().isEmpty() && !txtTitle.getText().toString().isEmpty()){

            //Yeni Bir Post paylaşılacak
            if (referrer.equals("HomePage")) {
                article.setArticleTitle(txtTitle.getText().toString());
                article.setArticleContent(txtContent.getText().toString());
                article.setNumberSaves(0);
                //article.setDateSave(Calendar.getInstance().getTime());

                boolean isTrue=db.addArticle(article);

                //Post Paylaşıldıysa
                if (isTrue){
                    Toast.makeText(this,"Post Paylaşıldı",Toast.LENGTH_SHORT).show();
                    finish();
                }
                //Post Paylaşılmadıysa
                else{
                    Toast.makeText(this,"Bir Sorun Meydana Geldi",Toast.LENGTH_SHORT).show();
                }
            }
        }
        //Başlık Ve İçerik Boş İse
        else{
            Toast.makeText(this,"Lütfen Başlık Ve İçerik Kısmını Doldurun",Toast.LENGTH_SHORT).show();
        }
    }
}