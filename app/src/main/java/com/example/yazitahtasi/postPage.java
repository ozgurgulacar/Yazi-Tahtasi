package com.example.yazitahtasi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.Classes.Article;
import com.example.Databases.DataBaseHelper;

public class postPage extends AppCompatActivity {
    Button btnCancel, btnShare;
    EditText txtTitle, txtContent;
    String referrer,Id="";
    Article article;
    DataBaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_page);
        btnCancel = findViewById(R.id.btnCancelPost);
        btnShare = findViewById(R.id.btnSharePost);
        db = new DataBaseHelper(this);
        txtTitle = findViewById(R.id.txtPostTitlePost);
        txtContent = findViewById(R.id.txtPostDetailPost);


        Intent i = getIntent();


        referrer = i.getStringExtra("referrer");
        if (referrer.equals("HomePage")) {
            btnCancel.setText("İptal Et");
            btnShare.setText("Paylaş");
            article = new Article();
        } else if (referrer.equals("DetailsMyPosts")) {
            btnCancel.setText("İptal Et");
            btnShare.setText("Güncelle");
            txtTitle.setText(i.getStringExtra("Title"));
            txtContent.setText(i.getStringExtra("Content"));
            Id=i.getStringExtra("Id");
            //article Nesnesine Referans Atacanak ve Yazı İçerikleri Eklenecek
        }
    }

    public void clickCancelOrDeletePost(View v){
        finish();
    }

    public void clickShareOrUpdatePost(View v) {
        //Başlık Ve İçerik Boş Değilse
        if (!txtContent.getText().toString().isEmpty() && !txtTitle.getText().toString().isEmpty()) {

            //Yeni Bir Post paylaşılacak
            if (referrer.equals("HomePage")) {
                article.setArticleTitle(txtTitle.getText().toString());
                article.setArticleContent(txtContent.getText().toString());
                article.setNumberOfScores(0);
                article.setAverageScore("0");
                boolean isTrue = db.addArticle(article);

                //Post Paylaşıldıysa
                if (isTrue) {
                    Toast.makeText(this, "Post Paylaşıldı", Toast.LENGTH_SHORT).show();
                    finish();
                }
                //Post Paylaşılmadıysa
                else {
                    Toast.makeText(this, "Bir Sorun Meydana Geldi", Toast.LENGTH_SHORT).show();
                }
            }
            //Post Düzenlenecekse
            else{
                db.updateArticle(Id,txtContent.getText().toString(),txtTitle.getText().toString());
                Toast.makeText(postPage.this,"Yazınız Başarıyla Güncellendi",Toast.LENGTH_SHORT).show();
                finish();
            }
        }
        //Başlık Ve İçerik Boş İse
        else {
            Toast.makeText(postPage.this,"Lütfen Alanları Doldurunuz",Toast.LENGTH_SHORT).show();

        }
    }
}