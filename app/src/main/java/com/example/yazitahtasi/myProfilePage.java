package com.example.yazitahtasi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    int which;
    ListView listView;
    ImageView img;
    TextView txtWhoFollowCount,txtIFollowCount,txtPostsCount,txtname,txtusername;


    @Override
    protected void onRestart() {
        myPosts();
        super.onRestart();

    }

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

        registerForContextMenu(listView);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                which=position;
                return false;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserSingleton user = UserSingleton.getInstance();
                String articleId=String.valueOf(articles.get(position).getArticleId());
                Intent i = new Intent(myProfilePage.this, DetailPost.class);

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
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inf = getMenuInflater();
        inf.inflate(R.menu.menumyprofile,menu);

        menu.setHeaderTitle("İşleminizi Seçin");
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.itemDelete){
            db.deleteArticle(String.valueOf(articles.get(which).getArticleId()));
            myPosts();

        } else if (item.getItemId()==R.id.itemEdit) {
            Intent i = new Intent(myProfilePage.this,postPage.class);
            i.putExtra("Title",articles.get(which).getArticleTitle());
            i.putExtra("Content",articles.get(which).getArticleContent());
            i.putExtra("Id",String.valueOf(articles.get(which).getArticleId()));
            i.putExtra("referrer","DetailsMyPosts");
            startActivity(i);
        }else{
            return false;
        }
        return true;
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