package com.cookandroid.exam.Util;

public class ScheduleItem {
    String title;
    String time;
    String color;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public ScheduleItem(String title, String time, String color){
        this.time = time;
        this.title = title;
        this.color = color;
    }
}
