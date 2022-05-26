package com.cookandroid.exam.DTO.Location;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResultKeyword {
    @SerializedName("Documents")
    @Expose
    private List<Place> Documents;

    public List<Place> getDocuments() {
        return Documents;
    }

    public void setDocuments(List<Place> documents) {
        Documents = documents;
    }
}
