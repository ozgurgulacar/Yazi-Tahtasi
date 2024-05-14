package com.example.yazitahtasi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.BaseAdapters.AdapterPosts;
import com.example.Classes.Article;
import com.example.Classes.DataBaseHelper;
import com.example.Classes.User;
import com.example.Classes.UserSingleton;

import java.util.List;

public class myProfilePage extends AppCompatActivity {

    DataBaseHelper db;
    List<Article> articles;
    int countPost=0;
    ListView listView;
    ImageView img;
    TextView txtWhoFollowCount,txtIFollowCount,txtPostsCount,txtname,txtusername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile_page);
        db = new DataBaseHelper(this);
        listView = findViewById(R.id.listViewMyProfilePage);
        img = findViewById(R.id.btnPhotoMyProfil);
        txtWhoFollowCount=findViewById(R.id.txtWhoFollowCount);
        txtIFollowCount=findViewById(R.id.txtIFollowCount);
        txtPostsCount=findViewById(R.id.txtPostCount);
        txtname=findViewById(R.id.txtMyNameMyProfile);
        txtusername=findViewById(R.id.txtMyUserNameMyProfile);

        myPosts();
        myphoto();
        myInformation();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserSingleton user = UserSingleton.getInstance();
                int articleId=articles.get(position).getArticleId();
                Intent i = new Intent(myProfilePage.this, DetailPost.class);
                i.putExtra("articleId",articleId);
                i.putExtra("userPhoto", String.valueOf(user.getPhoto()));
                i.putExtra("userName", user.getName() + " " + user.getSurName());
                i.putExtra("userUniqueName", user.getUserName());
                i.putExtra("postHeaderDetail", articles.get(position).getArticleTitle());
                i.putExtra("postContentDetail", articles.get(position).getArticleContent());
                startActivity(i);
            }
        });
    }




    public void clickHomePageMyProfilePage(View v){
        Intent i =new Intent(myProfilePage.this,homePage.class);
        startActivity(i);
        finish();

    }

    public void clickSearchUserMyProfilePage(View v){
        Intent i =new Intent(myProfilePage.this,searchUserPage.class);
        startActivity(i);
        finish();
    }

    public void clickSearchWordMyProfilePage(View v){

    }
    public void clickExitMyProfilePage(View v){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Emin Misiniz");
        alert.setMessage("Çıkış yapmak istediğinize Emin misiniz?");
        alert.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(myProfilePage.this, loginPage.class);
                startActivity(i);
                finish();
            }
        });

        alert.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert.create().show();
    }
    public void clickEditProfileMyProfilePage(View v){

    }

    public void myInformation(){
        UserSingleton user= UserSingleton.getInstance();
        User user2=db.getUser(user.getUserName());
        user.setNumberWhoFollowMe(user2.getNumberWhoFollowMe());
        txtusername.setText(user.getUserName());
        txtname.setText(user.getName()+" "+user.getSurName());
        txtIFollowCount.setText(String.valueOf(user.getNumberIFollow()));
        txtWhoFollowCount.setText(String.valueOf(user.getNumberWhoFollowMe()));


    }


    public void myPosts(){
        try {
            articles = db.getMyPosts();
            if (!articles.get(0).getArticleTitle().equals("KAYIT BULUNAMADI")){
                AdapterPosts adapterPosts = new AdapterPosts(getApplicationContext(),articles);
                listView.setAdapter(adapterPosts);
                txtPostsCount.setText(String.valueOf(articles.size()));
            }
            else{
                txtPostsCount.setText("0");
            }

        }catch (Exception e){
            Log.d("HataGetArticlesProfil",e.toString());
            Log.d("HataaGetArticlesProfil",e.getMessage());
        }
    }

    public void myphoto(){
        img.setImageURI(UserSingleton.getInstance().getPhoto());
    }
}