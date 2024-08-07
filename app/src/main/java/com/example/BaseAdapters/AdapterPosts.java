package com.example.BaseAdapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.Classes.Article;
import com.example.Databases.DataBaseHelper;
import com.example.yazitahtasi.R;

import java.util.List;

public class AdapterPosts extends BaseAdapter {

    Context context;
    List<Article> articles;
    LayoutInflater inflater;
    DataBaseHelper db;


    public AdapterPosts(Context context, List<Article> articles) {
        this.context = context;
        this.articles = articles;
        inflater=LayoutInflater.from(context);
        db = new DataBaseHelper(context);
    }


    @Override
    public int getCount() {
        return articles.size();
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
        convertView = inflater.inflate(R.layout.list_item_posts,null);
        TextView txtHeaderPosts,txtContentPosts,txtPostId,txtAverageScore;


        txtHeaderPosts=convertView.findViewById(R.id.txtHeaderPosts_list_item);
        txtContentPosts=convertView.findViewById(R.id.txtContentPosts_list_item);

        ImageView imageView=convertView.findViewById(R.id.btnPhotoPosts_list_item);

        txtHeaderPosts.setText(articles.get(position).getArticleTitle());
        String content = articles.get(position).getArticleContent();
        if (content.length()>160){
            txtContentPosts.setText(content.substring(0,150)+"...");
        }
        else {
            txtContentPosts.setText(content);
        }
        int id = articles.get(position).getArticleId();

        String uri=db.getArticlePhotoUri(id);

        imageView.setImageURI(Uri.parse(uri));
        return convertView;
    }
}
