package com.example.BaseAdapters;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Classes.User;
import com.example.Classes.UserSingleton;
import com.example.Databases.DataBaseHelper;
import com.example.yazitahtasi.R;
import com.example.yazitahtasi.searchUserPage;
import com.example.yazitahtasi.userPage;

import java.util.List;

public class AdapterTakipler extends BaseAdapter {

    Context context;
    List<User> users;
    LayoutInflater inflater;

    public AdapterTakipler(Context context, List<User> users) {
        this.context = context;
        this.users = users;
        inflater=LayoutInflater.from(context);
    }




    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.list_item_takipler,null);
        TextView txtUserName,txtFullName;

        txtUserName=convertView.findViewById(R.id.listitem_username_takip);
        txtFullName=convertView.findViewById(R.id.listitem_ad_takip);

        ImageView imageView=convertView.findViewById(R.id.listitem_photo_takip);


        txtUserName.setText(users.get(position).getUserName());
        txtFullName.setText(users.get(position).getName()+" "+users.get(position).getSurName());
        imageView.setImageURI(Uri.parse(String.valueOf(users.get(position).getPhoto())));

        return convertView;
    }
}
