package com.example.yazitahtasi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.BaseAdapters.AdapterPosts;
import com.example.Classes.Article;
import com.example.Databases.DataBaseHelper;
import com.example.Classes.User;
import com.example.Classes.UserSingleton;

import java.util.List;

public class userPage extends AppCompatActivity {

    List<Article> articles;
    User user;
    ImageView img;
    DataBaseHelper db;
    TextView txtName, txtUserName, txtPostCount, txtIFollow, txtWhoFollow;
    Button btnFollow;
    ListView listView;

    String userName,followsId;
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);

        listView = findViewById(R.id.listViewUserPage);

        txtName = findViewById(R.id.txtMyNameUserPage);
        txtUserName = findViewById(R.id.txtMyUserNameUserPage);
        txtPostCount = findViewById(R.id.txtPostCountUserPage);
        txtIFollow = findViewById(R.id.txtIFollowCountUserPage);
        txtWhoFollow = findViewById(R.id.txtWhoFollowCountUserPage);
        btnFollow=findViewById(R.id.btnfollowUserPage);


        img = findViewById(R.id.btnPhotoUserPage);


        Intent i = getIntent();
        db = new DataBaseHelper(this);
        userName = i.getStringExtra("userNameUserPage");
        followsId = i.getStringExtra("followId");
        if (!followsId.equals("null"))
            btnFollow.setText("Takipten Çıkar");
        else{
            btnFollow.setText("Takip Et");
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String articleId=String.valueOf(articles.get(position).getArticleId());

                Intent i = new Intent(userPage.this, DetailPost.class);

                i.putExtra("userPhoto", String.valueOf(user.getPhoto()));
                i.putExtra("userName", user.getName() + " " + user.getSurName());
                i.putExtra("userUniqueName", user.getUserName());

                i.putExtra("postHeaderDetail", articles.get(position).getArticleTitle());
                i.putExtra("postContentDetail", articles.get(position).getArticleContent());
                i.putExtra("postAverageScore",articles.get(position).getAverageScore());
                i.putExtra("postNumberOfScore",String.valueOf(articles.get(position).getNumberOfScores()));
                i.putExtra("articleId",articleId);

                startActivity(i);

            }
        });



        txtIFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detayTakip("takipEttikleri",txtIFollow.getText().toString());
            }
        });


        txtWhoFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detayTakip("takipcileri",txtWhoFollow.getText().toString());
            }
        });

        userInformation();
        getPostsUser();


    }


    private void detayTakip(String which,String count){
        try {
            Intent intent = new Intent(userPage.this, takiplistesi.class);
            intent.putExtra("which",which);
            intent.putExtra("count",count);
            intent.putExtra("who",user.getUserName());

            startActivity(intent);
            Log.d("detayTakip", "which: " + which + ", count: " + count + ", who: " + user.getUserName());
        }catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }

    }

    public void clickBackUserPage(View v) {
        finish();
    }

    private void userInformation() {
        try {
            user = db.getUser(userName);
            if (user.getName() == "HataKodu=1024!") {
                Intent a = new Intent(this, homePage.class);
                startActivity(a);
                finish();
            } else {
                txtWhoFollow.setText(String.valueOf(user.getNumberWhoFollowMe()));
                Log.d("denemeicin", "userInformation: "+String.valueOf(user.getNumberWhoFollowMe()));
                txtName.setText(user.getName() + " " + user.getSurName());
                txtUserName.setText(String.valueOf(user.getUserName()));
                txtIFollow.setText(String.valueOf(user.getNumberIFollow()));
                img.setImageURI(user.getPhoto());
                userId = user.getUserId();

            }
        } catch (Exception e) {
            Log.d("TAG", e.getMessage());

        }

    }


    public void clickFollowUserPage(View v) {
        if (btnFollow.getText().toString().equals("Takip Et")){
            String donus=db.addFollow(UserSingleton.getInstance().getUserName(),user.getUserName());
            if(!donus.equals("-1")){
                btnFollow.setText("Takipten Çıkar");
                int count=Integer.parseInt(txtWhoFollow.getText().toString())+1;
                txtWhoFollow.setText(String.valueOf(count));
                db.updateOtherUserFollow(String.valueOf(count),txtUserName.getText().toString());
                UserSingleton.getInstance().setNumberIFollow(UserSingleton.getInstance().getNumberIFollow()+1);
                db.updateCurrentUserFollow();
            }else{
                Toast.makeText(userPage.this,"Bir Hata Oluştu",Toast.LENGTH_SHORT).show();
            }
        }
        else{
            btnFollow.setText("Takip Et");
            db.deleteFollow(txtUserName.getText().toString());
            txtWhoFollow.setText(String.valueOf(Integer.parseInt(txtWhoFollow.getText().toString())-1));
            db.updateOtherUserFollow(txtWhoFollow.getText().toString(),txtUserName.getText().toString());
            UserSingleton.getInstance().setNumberIFollow(UserSingleton.getInstance().getNumberIFollow()-1);
            db.updateCurrentUserFollow();

        }



    }

    private void getPostsUser() {
        articles = db.getUserPosts(userId);
        if (!articles.get(0).getArticleTitle().equals("KAYIT BULUNAMADI")) {
            AdapterPosts adapterPosts = new AdapterPosts(getApplicationContext(), articles);
            listView.setAdapter(adapterPosts);

            txtPostCount.setText(String.valueOf(articles.size()));
        } else {
            txtPostCount.setText("0");
        }
    }

}