package com.cookandroid.mysonge.DTO.Location;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlaceMeta {
    @SerializedName("is_end")
    @Expose
    private Boolean isEnd;
    @SerializedName("pageable_count")
    @Expose
    private Integer pageableCount;
    @SerializedName("same_name")
    @Expose
    private SameName sameName;
    @SerializedName("total_count")
    @Expose
    private Integer totalCount;

    public Boolean getIsEnd() {
        return isEnd;
    }

    public void setIsEnd(Boolean isEnd) {
        this.isEnd = isEnd;
    }

    public Integer getPageableCount() {
        return pageableCount;
    }

    public void setPageableCount(Integer pageableCount) {
        this.pageableCount = pageableCount;
    }

    public SameName getSameName() {
        return sameName;
    }

    public void setSameName(SameName sameName) {
        this.sameName = sameName;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public PlaceMeta(SameName same_name, int total_count, int pabeable_count, boolean is_End){
        this.sameName = same_name;
        this.totalCount = total_count;
        this.pageableCount = pabeable_count;
        this.isEnd = is_End;
    }
}
