package com.cookandroid.exam.Util;

public class TimelineItem {
    private String timeStr;
    private String apStr;
    private String lineStr;
    private String titleStr;
    private String colorStr;

    public String getTimeEvent() {
        return timeEvent;
    }

    public void setTimeEvent(String timeEvent) {
        this.timeEvent = timeEvent;
    }

    private String timeEvent;

    public void setTime(String time) {
        timeStr = time;
    }

    public void setAP(String ap) {
        apStr = ap;
    }

    public void setLine(String line) {
        lineStr = line;
    }

    public void setTitle(String title)  {
        titleStr = title; }

    public void setColor(String color)  {
        colorStr = color;}

    public String getTime() {
        return this.timeStr;
    }

    public String getAP() {
        return this.apStr;
    }

    public String getLine() {
        return this.lineStr;
    }

    public String getTitle(){
        return this.titleStr;
    }

    public String getColor() {
        return this.colorStr;
    }



}
