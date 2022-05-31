package com.cookandroid.exam.DTO.Location;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResultKeyword {
    @SerializedName("Documents")
    @Expose
    private List<Place> Documents;
    @SerializedName("meta")
    @Expose
    private PlaceMeta meta;

    public List<Place> getDocuments() {
        return Documents;
    }

    public void setDocuments(List<Place> documents) {
        Documents = documents;
    }

    public PlaceMeta getMeta() {
        return meta;
    }

    public void setMeta(PlaceMeta meta) {
        this.meta = meta;
    }
}
