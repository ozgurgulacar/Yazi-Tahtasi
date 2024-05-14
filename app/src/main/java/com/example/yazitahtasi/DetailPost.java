package com.example.yazitahtasi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.Classes.Article;
import com.example.Classes.DataBaseHelper;
import com.example.Classes.UserSingleton;

public class DetailPost extends AppCompatActivity {

    ImageView img;
    TextView txtName,txtUserName,txtHeaderPost;
    EditText txtContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_post);
        txtName=findViewById(R.id.txtNameDetailPost);
        txtUserName=findViewById(R.id.txtUserNameDetailPost);
        img=findViewById(R.id.btnPhotoDetailPost);
        txtHeaderPost=findViewById(R.id.txtHeaderPostDetailPost);
        txtContent=findViewById(R.id.txtContentDetailPost);

        try {
            Intent i = getIntent();
            txtName.setText(i.getStringExtra("userName"));
            txtUserName.setText(i.getStringExtra("userUniqueName"));
            img.setImageURI(Uri.parse(i.getStringExtra("userPhoto")));
            txtHeaderPost.setText(i.getStringExtra("postHeaderDetail"));
            txtContent.setText(i.getStringExtra("postContentDetail"));

        }catch (Exception e){
            finish();
        }



    }

    public void clickBackDetailPost(View v){
        finish();
    }

    public void clickNavigateProfileDetailPost(View v){
        if (txtUserName.getText().toString().equals(UserSingleton.getInstance().getUserName()))
            finish();
        else{
            Intent i = new Intent(this, userPage.class);
            i.putExtra("userNameUserPage",txtUserName.getText().toString());
            startActivity(i);
            finish();
        }

    }







}