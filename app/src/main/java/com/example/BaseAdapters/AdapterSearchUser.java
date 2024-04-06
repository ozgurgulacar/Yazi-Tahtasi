package com.example.BaseAdapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.Classes.Users;
import com.example.yazitahtasi.R;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class AdapterSearchUser extends BaseAdapter {

    Context context;
    List<Users> users;
    LayoutInflater inflater;

    public AdapterSearchUser(Context context, List<Users> users) {
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
        convertView = inflater.inflate(R.layout.list_item_search_user,null);
        TextView txtUserName,txtFullName;

        txtUserName=convertView.findViewById(R.id.txtUserNameSearchPeopleListItem);
        txtFullName=convertView.findViewById(R.id.txtNameSearchPeopleListItem);
        ImageView imageView=convertView.findViewById(R.id.btnPhotoSearchPeopleListItem);

        txtUserName.setText(users.get(position).getUserName());
        txtFullName.setText(users.get(position).getName()+" "+users.get(position).getSurName());
        imageView.setImageURI(Uri.parse(String.valueOf(users.get(position).getPhoto())));
        return convertView;
    }
}
