package com.cookandroid.mysonge.DTO.Location;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResultKeyword {
    @SerializedName("documents")
    @Expose
    private List<Document> documents = null;
    @SerializedName("meta")
    @Expose
    private PlaceMeta meta;

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    public PlaceMeta getMeta() {
        return meta;
    }

    public void setMeta(PlaceMeta meta) {
        this.meta = meta;
    }
}
