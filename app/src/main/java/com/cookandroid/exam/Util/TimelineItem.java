package com.cookandroid.exam.Util;

public class TimelineItem {
    private String timeStr;
    private String apStr;
    private String lineStr;

    public void setTime(String time) {
        timeStr = time;
    }

    public void setAP(String ap) {
        apStr = ap;
    }

    public void setLine(String line) {
        lineStr = line;
    }

    public String getTime() {
        return this.timeStr;
    }

    public String getAP() {
        return this.apStr;
    }

    public String getLine() {
        return this.lineStr;
    }

}
