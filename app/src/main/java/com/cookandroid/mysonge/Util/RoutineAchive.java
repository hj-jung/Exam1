package com.cookandroid.mysonge.Util;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RoutineAchive {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("achieve")
    @Expose
    private Boolean achieve;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getAchieve() {
        return achieve;
    }

    public void setAchieve(Boolean achieve) {
        this.achieve = achieve;
    }

    public RoutineAchive(Boolean achieve) {
        this.achieve = achieve;
    }
}
