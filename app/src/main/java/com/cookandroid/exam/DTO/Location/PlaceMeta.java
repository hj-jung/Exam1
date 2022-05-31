package com.cookandroid.exam.DTO.Location;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlaceMeta {
    @SerializedName("total_count")
    @Expose
    private int total_count;
    @SerializedName("pageable_count")
    @Expose
    private int pabeable_count;
    @SerializedName("is_End")
    @Expose
    private boolean is_End;

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    public int getPabeable_count() {
        return pabeable_count;
    }

    public void setPabeable_count(int pabeable_count) {
        this.pabeable_count = pabeable_count;
    }

    public boolean getIs_End() {
        return is_End;
    }

    public void setIs_End(boolean is_End) {
        this.is_End = is_End;
    }

    public PlaceMeta(int total_count, int pabeable_count, boolean is_End){
        this.total_count = total_count;
        this.pabeable_count = pabeable_count;
        this.is_End = is_End;
    }
}
