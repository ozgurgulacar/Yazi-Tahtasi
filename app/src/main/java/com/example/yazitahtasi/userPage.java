package com.example.yazitahtasi;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.BaseAdapters.AdapterPosts;
import com.example.Classes.Article;
import com.example.Classes.DataBaseHelper;
import com.example.Classes.User;
import com.example.Classes.UserSingleton;

import java.util.List;

public class userPage extends AppCompatActivity {


    ImageView img;
    DataBaseHelper db;
    TextView txtName,txtUserName,txtPostCount,txtIFollow,txtWhoFollow;

    ListView listView;

    String userName;
    int userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);

        listView=findViewById(R.id.listViewUserPage);

        txtName=findViewById(R.id.txtMyNameUserPage);
        txtUserName=findViewById(R.id.txtMyUserNameUserPage);
        txtPostCount=findViewById(R.id.txtPostCountUserPage);
        txtIFollow=findViewById(R.id.txtIFollowCountUserPage);
        txtWhoFollow=findViewById(R.id.txtWhoFollowCountUserPage);

        img=findViewById(R.id.btnPhotoUserPage);



        Intent i = getIntent();
        db= new DataBaseHelper(this);
        userName=i.getStringExtra("userNameUserPage");


        userInformation();
        getPostsUser();


    }


    public void clickBackUserPage(View v){
        finish();
    }
    private void userInformation(){
        try {
            User user=db.getUser(userName);
            if (user.getName() =="HataKodu=1024!"){
                Intent a = new Intent(this, homePage.class);
                startActivity(a);
                finish();
            }
            else {
                txtWhoFollow.setText(String.valueOf(user.getNumberWhoFollowMe()));
                txtName.setText(user.getName()+" "+user.getSurName());
                txtUserName.setText(String.valueOf(user.getUserName()));
                txtIFollow.setText(String.valueOf(user.getNumberIFollow()));
                img.setImageURI(user.getPhoto());
                userId=user.getUserId();
                Log.d("Photo Uri", String.valueOf(UserSingleton.getInstance().getPhoto()));
                Log.d("Photo Uri",String.valueOf(user.getPhoto()));

            }
        }
        catch (Exception e){
            Log.d("TAG",e.getMessage());

        }

    }



    public void clickFollowUserPage(View v){

    }
    private void getPostsUser(){
        List<Article> articles = db.getUserPosts(userId);
        if (!articles.get(0).getArticleTitle().equals("KAYIT BULUNAMADI")){
            AdapterPosts adapterPosts = new AdapterPosts(getApplicationContext(),articles);
            listView.setAdapter(adapterPosts);
            txtPostCount.setText(String.valueOf(articles.size()));
        }
        else{
            txtPostCount.setText("0");
        }
    }

}