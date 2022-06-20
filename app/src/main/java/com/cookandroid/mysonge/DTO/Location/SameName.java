package com.cookandroid.mysonge.DTO.Location;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SameName {
    @SerializedName("keyword")
    @Expose
    private String keyword;
    @SerializedName("region")
    @Expose
    private List<Object> region = null;
    @SerializedName("selected_region")
    @Expose
    private String selectedRegion;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public List<Object> getRegion() {
        return region;
    }

    public void setRegion(List<Object> region) {
        this.region = region;
    }

    public String getSelectedRegion() {
        return selectedRegion;
    }

    public void setSelectedRegion(String selectedRegion) {
        this.selectedRegion = selectedRegion;
    }
}
