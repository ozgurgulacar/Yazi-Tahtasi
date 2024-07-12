package com.example.yazitahtasi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.BaseAdapters.AdapterTakipler;
import com.example.Classes.User;
import com.example.Classes.UserSingleton;
import com.example.Databases.DataBaseHelper;

import java.util.ArrayList;

public class takiplistesi extends AppCompatActivity {

    ListView liste;
    DataBaseHelper db;
    String who="",which="",count="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_takiplistesi);
        db= new DataBaseHelper(this);
        liste = findViewById(R.id.listetakip);
        Intent intent = getIntent();
        who = intent.getStringExtra("who");
        which = intent.getStringExtra("which");
        count = intent.getStringExtra("count");
        Log.d("detayTakip", "TakipListesi which: " + which + ", count: " + count + ", who: " +who);

        if (!(count.equals("0"))){
            getAll();
        }

        liste.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView text = view.findViewById(R.id.listitem_username_takip);

                // String Extra userNameUserPage
                if (text.getText().toString().equals(UserSingleton.getInstance().getUserName())){
                    Intent i = new Intent(takiplistesi.this, myProfilePage.class);
                    startActivity(i);
                    finish();
                }
                else{
                    Intent i = new Intent(takiplistesi.this, userPage.class);
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

    public void clickBackTakipListesi(View view) {
        finish();
    }


    @Override
    protected void onRestart() {
        int counteksi=Integer.parseInt(count);
        count=String.valueOf(counteksi-1);
        if (!(count.equals("0"))){
            getAll();
        }
        super.onRestart();
    }

    private void getAll(){

        try {
            ArrayList<String> followUserNames=null;
            ArrayList<User> kullanicilar=null;
            if (which.equals("takipEttikleri")){
                followUserNames=db.getAllTakipEttikleri(who);
            }else{
                followUserNames=db.getAllTakipcileri(who);
            }
            kullanicilar=db.getFullNameFollows(followUserNames);
            AdapterTakipler adapterTakipler = new AdapterTakipler(this,kullanicilar);
            liste.setAdapter(adapterTakipler);

        }catch (Exception e){
            Log.d("detayTakip", e.getMessage());

            finish();
        }


    }
}