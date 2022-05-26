package com.cookandroid.exam.DTO.Dust;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Body {

    @SerializedName("totalCount")
    @Expose
    private Integer totalCount;
    @SerializedName("items")
    @Expose
    private List<Item> items = null;
    @SerializedName("pageNo")
    @Expose
    private Integer pageNo;
    @SerializedName("numOfRows")
    @Expose
    private Integer numOfRows;

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getNumOfRows() {
        return numOfRows;
    }

    public void setNumOfRows(Integer numOfRows) {
        this.numOfRows = numOfRows;
    }

}
