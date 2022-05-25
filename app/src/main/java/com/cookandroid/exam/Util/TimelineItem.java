package com.cookandroid.exam.Util;

public class TimelineItem {
    String time;
    String ap;
    String title;
    String color;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAP() {
        return ap;
    }

    public void setAP(String ap) {
        this.ap = ap;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public TimelineItem(String time, String ap, String title, String color){
        this.time = time;
        this.ap = ap;
        this.title = title;
        this.color = color;
    }

}
