package com.cookandroid.mysonge.Util;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ScheduleData implements Parcelable {
    @SerializedName("color")
    @Expose
    private String color;
    @SerializedName("context")
    @Expose
    private String context;
    @SerializedName("endHms")
    @Expose
    private String endHms;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("startHms")
    @Expose
    private String startHms;
    @SerializedName("startYmd")
    @Expose
    private String startYmd;
    @SerializedName("startH")
    @Expose
    private String startH;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("time")
    @Expose
    private Integer time;
    @SerializedName("AMPM")
    @Expose
    private String AMPM;
    @SerializedName("x")
    @Expose
    private Double x;
    @SerializedName("y")
    @Expose
    private Double y;

    protected ScheduleData(Parcel in) {
        color = in.readString();
        context = in.readString();
        endHms = in.readString();
        location = in.readString();
        startHms = in.readString();
        startYmd = in.readString();
        startH = in.readString();
        title = in.readString();
        if (in.readByte() == 0) {
            time = null;
        } else {
            time = in.readInt();
        }
        AMPM = in.readString();
        x = in.readDouble();
        y = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(color);
        dest.writeString(context);
        dest.writeString(endHms);
        dest.writeString(location);
        dest.writeString(startHms);
        dest.writeString(startYmd);
        dest.writeString(startH);
        dest.writeString(title);
        if (time == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(time);
        }
        dest.writeString(AMPM);
        dest.writeDouble(x);
        dest.writeDouble(y);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ScheduleData> CREATOR = new Creator<ScheduleData>() {
        @Override
        public ScheduleData createFromParcel(Parcel in) {
            return new ScheduleData(in);
        }

        @Override
        public ScheduleData[] newArray(int size) {
            return new ScheduleData[size];
        }
    };

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

    public String getStartH() { return startH; }

    public void setStartH(String startH) { this.startH = startH; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public String getAMPM() {
        return AMPM;
    }

    public void setAMPM(String AMPM) {
        this.AMPM = AMPM;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public ScheduleData(String color, String context, String endHms, String location, String startHms, String startYmd, String startH, String title, Integer time, String AMPM, Double x, Double y){
        this.color = color;
        this.context = context;
        this.endHms = endHms;
        this.location = location;
        this.startHms = startHms;
        this.startYmd = startYmd;
        this.startH = startH;
        this.title = title;
        this.time = time;
        this.AMPM = AMPM;
        this.x = x;
        this.y = y;
    }
}
