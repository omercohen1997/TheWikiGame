package com.oog.thewikigame.models;

public class GameDetailModel {
    public final String date;
    public final String startArticle;
    public final String endArticle;
    public final String time;
    public final String jumps;
    public final String returns;
    public final String findInText;
    public final String finished;

    public GameDetailModel(String date,
                           String startArticle,
                           String endArticle,
                           String time,
                           String jumps,
                           String returns,
                           String findInText,
                           String finished) {
        this.date = date;
        this.startArticle = startArticle;
        this.endArticle = endArticle;
        this.time = time;
        this.jumps = jumps;
        this.returns = returns;
        this.findInText = findInText;
        this.finished = finished;
    }



}
