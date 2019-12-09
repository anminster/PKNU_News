package com.example.pknu_news;

import java.io.Serializable;

class NewsData implements Serializable {
    private String title;       //제목
    private String reporter;    //기자
    private String article;     //기사
    private int num;            // 가번호
    private String profile;     // 사진

    public String getProfile() {
        return profile;
    }


    public NewsData(){}

    public int getNum() {
        return num;
    }


    public String getTitle() {
        return title;
    }

    public String getReporter() {
        return reporter;
    }

    public String getArticle() {
        return article;
    }

}
