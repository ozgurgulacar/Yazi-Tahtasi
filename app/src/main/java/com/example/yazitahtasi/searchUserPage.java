package com.example.yazitahtasi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.BaseAdapters.AdapterSearchUser;
import com.example.Databases.DataBaseConstants;
import com.example.Databases.DataBaseHelper;
import com.example.Classes.User;
import com.example.Classes.UserSingleton;

import java.util.List;

public class searchUserPage extends AppCompatActivity {

    DataBaseHelper db;
    EditText txt;
    ListView listView;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);
        db= new DataBaseHelper(this);
        txt=findViewById(R.id.txtSearchPeople);
        listView=findViewById(R.id.listViewSearchUser);
        sharedPreferences=this.getSharedPreferences("com.example.yazitahtasi.SHARED_PREFERENCES", Context.MODE_PRIVATE);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView text = view.findViewById(R.id.txtUserNameSearchPeopleListItem);

                // String Extra userNameUserPage
                if (text.getText().toString().equals(UserSingleton.getInstance().getUserName())){
                    Intent i = new Intent(searchUserPage.this, myProfilePage.class);
                    startActivity(i);
                    finish();
                }
                else{
                    Intent i = new Intent(searchUserPage.this, userPage.class);
                    i.putExtra("userNameUserPage",text.getText().toString());

                    i.putExtra("followId",db.isfollow(
                            UserSingleton.getInstance().getUserName(),
                            text.getText().toString())
                    );

                    startActivity(i);
                }
            }
        });


    }



    public void clickSearchUser(View v){
        String[] sorgu= txt.getText().toString().trim().split(" ");
        String query= "";
        for(int i=0;i< sorgu.length;i++){
            query += " OR "+ DataBaseConstants.User_Name+" LIKE '%"+sorgu[i]+"%'";
        }
        for(int i=0;i< sorgu.length;i++){
            query += " OR "+DataBaseConstants.User_SurName+" LIKE '%"+sorgu[i]+"%'";
        }
        for(int i=0;i< sorgu.length;i++){
            query += " OR "+DataBaseConstants.User_Name_Unique+" LIKE '%"+sorgu[i]+"%'";
        }

        //edit.setText(query.substring(3));
        try {
            List<User> users = db.searchPeople(query);
            AdapterSearchUser adapterSU= new AdapterSearchUser(getApplicationContext(),users);
            listView.setAdapter(adapterSU);

        }catch (Exception x){
            Log.d("HATA", x.getMessage());
            Log.d("HATAa", x.toString());

        }
    }

    public void clickExitSearchUser(View v){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Emin Misiniz");
        alert.setMessage("Çıkış yapmak istediğinize Emin misiniz?");
        alert.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(searchUserPage.this, loginPage.class);
                SharedPreferences.Editor editor= sharedPreferences.edit();
                editor.putBoolean("isActive",false);
                editor.apply();
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

    public void clickBackSearchUser(View v){
        Intent i = new Intent(this, homePage.class);
        startActivity(i);
        finish();

    }

}