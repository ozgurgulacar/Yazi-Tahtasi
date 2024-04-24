package com.example.Classes;

import java.util.Date;

public class Article {
    private int articleId;
    private String articleTitle;
    private String articleContent;
    private int numberSaves;
    private String updated;


    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }
    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }

    public int getNumberSaves() {
        return numberSaves;
    }

    //Sadece 1 arttırma ve 1 azaltmaya izin verildi
    public void setNumberSaves(int number) {
        if (number>0)
            this.numberSaves = numberSaves+1;
        else if(number<0)
            this.numberSaves = numberSaves-1;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }



}
