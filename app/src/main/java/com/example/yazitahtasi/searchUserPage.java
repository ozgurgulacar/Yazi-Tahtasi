package com.example.yazitahtasi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.example.BaseAdapters.AdapterSearchUser;
import com.example.Classes.DataBaseConstants;
import com.example.Classes.DataBaseHelper;
import com.example.Classes.Users;

import java.util.List;

public class searchUserPage extends AppCompatActivity {

    DataBaseHelper db;
    EditText txt;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);
        db= new DataBaseHelper(this);
        txt=findViewById(R.id.txtSearchPeople);
        listView=findViewById(R.id.listViewSearchUser);
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
            List<Users> users = db.searchPeople(query);
            AdapterSearchUser adapterSU= new AdapterSearchUser(getApplicationContext(),users);
            listView.setAdapter(adapterSU);

        }catch (Exception x){
            Log.d("HATA", x.getMessage());
            Log.d("HATAa", x.toString());

        }
    }
}