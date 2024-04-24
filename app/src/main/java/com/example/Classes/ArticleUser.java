package com.example.Classes;

public class ArticleUser {
    private int articleId;
    private int userId;
    private int ArticleUserId;
    private String dateSave;





    public String getDateSave() {
        return dateSave;
    }

    public void setDateSave(String dateSave) {
        this.dateSave = dateSave;
    }




    public int ArticleUserId() {
        return ArticleUserId;
    }
    public void ArticleUserId(int ArticleUserId) {
        this.ArticleUserId = ArticleUserId;
    }
    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
