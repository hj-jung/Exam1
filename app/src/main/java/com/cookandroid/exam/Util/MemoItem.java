package com.cookandroid.exam.Util;

public class MemoItem {
    private String title;
    private String color;

    public void setTitle(String title)  { this.title = title;}

    public void setColor(String color)  { this.color = color;}

    public String getTitle() { return title; }

    public String getColor() {
        return color;
    }

    public MemoItem(String title, String color){
        this.title = title;
        this.color = color;
    }
}
