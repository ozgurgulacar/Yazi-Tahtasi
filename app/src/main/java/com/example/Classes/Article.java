package com.example.Classes;

import java.util.Date;

public class Article {
    private int articleId;
    private String articleTitle;
    private String articleContent;
    private int numberOfScores;
    private String averageScore;






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

    public int getNumberOfScores() {
        return numberOfScores;
    }

    public void setNumberOfScores(int number) {
            this.numberOfScores = number;
    }

    public String getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(String averageScore) {
        this.averageScore = averageScore;
    }



}
