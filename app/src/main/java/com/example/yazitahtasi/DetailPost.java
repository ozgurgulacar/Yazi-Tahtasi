package com.example.yazitahtasi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.Classes.Article;
import com.example.Classes.DataBaseHelper;
import com.example.Classes.UserSingleton;

public class DetailPost extends AppCompatActivity {

    ImageView img;
    TextView txtName, txtUserName, txtHeaderPost, txtAverageScore;
    String userName, articleId;
    EditText txtContent;
    int numberOfScores;
    float averageScore;
    DataBaseHelper db;
    Article article=new Article();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_post);
        txtName = findViewById(R.id.txtNameDetailPost);
        txtUserName = findViewById(R.id.txtUserNameDetailPost);
        txtAverageScore = findViewById(R.id.txtAverageScore_list_item);
        img = findViewById(R.id.btnPhotoDetailPost);

        txtHeaderPost = findViewById(R.id.txtHeaderPostDetailPost);
        txtContent = findViewById(R.id.txtContentDetailPost);

        db = new DataBaseHelper(this);
        Intent i = getIntent();


        String articleTitle, articleContent, postNumberOfScores, postAverageScore;

        articleTitle = i.getStringExtra("postHeaderDetail").trim();
        articleContent = i.getStringExtra("postContentDetail");
        articleId = i.getStringExtra("articleId");
        postNumberOfScores = i.getStringExtra("postNumberOfScore").trim();
        postAverageScore = i.getStringExtra("postAverageScore");


        article.setNumberOfScores(Integer.parseInt(postNumberOfScores));
        article.setArticleTitle(articleTitle);
        article.setArticleContent(articleContent);
        article.setArticleId(Integer.parseInt(articleId));
        article.setAverageScore(postAverageScore);

        img.setImageURI(Uri.parse(i.getStringExtra("userPhoto")));
        txtName.setText(i.getStringExtra("userName"));
        userName = i.getStringExtra("userUniqueName");
        txtUserName.setText("@"+userName);

        articleId = String.valueOf(article.getArticleId());

        txtHeaderPost.setText(articleTitle);
        txtContent.setText(articleContent);
        numberOfScores = Integer.parseInt(postNumberOfScores);
        averageScore = Float.parseFloat(postAverageScore);
        txtAverageScore.setText("Average Score " + averageScore);


    }

    public void clickBackDetailPost(View v) {
        finish();
    }

    public void clickNavigateProfileDetailPost(View v) {
        if (userName.equals(UserSingleton.getInstance().getUserName()))
            finish();
        else {
            Intent i = new Intent(this, userPage.class);
            i.putExtra("userNameUserPage", userName);
            i.putExtra("followId",db.isfollow(
                    UserSingleton.getInstance().getUserName(),
                    userName)
            );
            startActivity(i);
            finish();
        }

    }


    public void clickScore(View view) {
        String rated = db.getAmIScored(articleId);
        if (userName.equals(UserSingleton.getInstance().getUserName())) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Oylama Başarısız");
            builder.setMessage("Kendi Yazınızı Oylayamazsınız");
            builder.setPositiveButton("Anladım", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.create().show();

        } else if (!rated.equals("Puanlanmadı")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Oylama Başarısız");
            builder.setMessage("Bu Yazıyı Daha Önce Oyladınız. Eski Oyunuz: " + rated);
            builder.setPositiveButton("Anladım", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.create().show();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View ratingDialogView = getLayoutInflater().inflate(R.layout.rating_dialog, null);

            RatingBar ratingBar = ratingDialogView.findViewById(R.id.rating_bar);
            ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                }
            });

            builder.setView(ratingDialogView);

            builder.setPositiveButton("Puanla", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    float rating = ratingBar.getRating();
                    float sumScore = averageScore * numberOfScores;
                    numberOfScores++;
                    float newAverageScore = (sumScore + rating) / (numberOfScores);
                    txtAverageScore.setText("Average Score: " + String.valueOf(newAverageScore));

                    averageScore = newAverageScore;

                    db.addRating(userName, UserSingleton.getInstance().getUserName(), articleId, String.valueOf(rating));

                    article.setAverageScore(String.valueOf(averageScore));
                    article.setNumberOfScores(numberOfScores);
                    db.updateRating(article);


                }
            });
            builder.setNegativeButton("Kapat", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }


    }
}