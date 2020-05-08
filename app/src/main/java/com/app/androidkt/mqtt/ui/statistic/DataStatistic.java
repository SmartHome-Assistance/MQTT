package com.app.androidkt.mqtt.ui.statistic;

public class DataStatistic {
    String title;
    String discription;
    String time;

    public  DataStatistic ( String title, String discription, String time){
        this.title = title;
        this.discription = discription;
        this.time = time;
    }

    public String getDiscription() {
        return discription;
    }

    public String getTime() {
        return time;
    }

    public String getTitle() {
        return title;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
