package com.cookandroid.exam.DTO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Schedule {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("color")
    @Expose
    private String color;
    @SerializedName("context")
    @Expose
    private String context;
    @SerializedName("endHms")
    @Expose
    private String endHms;
    @SerializedName("endYmd")
    @Expose
    private String endYmd;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("startHms")
    @Expose
    private String startHms;
    @SerializedName("startYmd")
    @Expose
    private String startYmd;
    @SerializedName("user_id")
    @Expose
    private Integer user_id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("x")
    @Expose
    private Integer x;
    @SerializedName("y")
    @Expose
    private Integer y;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getEndHms() { return endHms; }

    public void setEndHms(String endHms) { this.endHms = endHms; }

    public String getEndYmd() { return endYmd; }

    public void setEndYmd(String endYmd) { this.endYmd = endYmd; }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStartHms() { return startHms; }

    public void setStartHms(String startHms) { this.startHms = startHms; }

    public String getStartYmd() { return startYmd; }

    public void setStartYmd(String startYmd) { this.startYmd = startYmd; }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }


    public Schedule(String color, String context, String endHms, String endYmd, String location, String startHms, String startYmd, Integer user_id, String title, Integer x, Integer y){
        this.color = color;
        this.context = context;
        this.endHms = endHms;
        this.endYmd = endYmd;
        this.location = location;
        this.startYmd = startYmd;
        this.startHms = startHms;
        this.endYmd = endYmd;
        this.user_id = user_id;
        this.title = title;
        this.x = x;
        this.y = y;
    }
}

