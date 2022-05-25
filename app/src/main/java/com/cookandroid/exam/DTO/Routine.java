package com.cookandroid.exam.DTO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.time.LocalTime;

public class Routine {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("achieve")
    @Expose
    private Boolean achieve;
    @SerializedName("context")
    @Expose
    private String context;
    @SerializedName("friday")
    @Expose
    private Integer friday;
    @SerializedName("monday")
    @Expose
    private Integer monday;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("routineTime")
    @Expose
    private String routineTime;
    @SerializedName("saturday")
    @Expose
    private Integer saturday;
    @SerializedName("sunday")
    @Expose
    private Integer sunday;
    @SerializedName("thursday")
    @Expose
    private Integer thursday;
    @SerializedName("tuesday")
    @Expose
    private Integer tuesday;
    @SerializedName("wednesday")
    @Expose
    private Integer wednesday;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSunday() {
        return sunday;
    }

    public void setSunday(Integer sunday) {
        this.sunday = sunday;
    }

    public Integer getMonday() {
        return monday;
    }

    public void setMonday(Integer monday) {
        this.monday = monday;
    }

    public Integer getTuesday() {
        return tuesday;
    }

    public void setTuesday(Integer tuesday) {
        this.tuesday = tuesday;
    }

    public Integer getWednesday() {
        return wednesday;
    }

    public void setWednesday(Integer wednesday) {
        this.wednesday = wednesday;
    }

    public Integer getThursday() {
        return thursday;
    }

    public void setThursday(Integer thursday) {
        this.thursday = thursday;
    }

    public Integer getFriday() {
        return friday;
    }

    public void setFriday(Integer friday) {
        this.friday = friday;
    }

    public Integer getSaturday() {
        return saturday;
    }

    public void setSaturday(Integer saturday) {
        this.saturday = saturday;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Boolean getAchieve() {
        return achieve;
    }

    public void setAchieve(Boolean achieve) {
        this.achieve = achieve;
    }

    public String getRoutineTime() {
        return routineTime;
    }

    public void setRoutineTime(String routineTime) {
        this.routineTime = routineTime;
    }

    public Routine(Boolean achieve, String context, int friday, int monday, String name, String routineTime, int saturday, int sunday, int thursday, int tuesday, int wednesday) {
        this.achieve = achieve;
        this.context = context;
        this.friday = friday;
        this.monday = monday;
        this.name = name;
        this.routineTime = routineTime;
        this.saturday = saturday;
        this.sunday = sunday;
        this.thursday = thursday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
    }

}


