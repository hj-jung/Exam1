package com.cookandroid.mysonge.DTO.Dust;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PmInfo {

    @SerializedName("response")
    @Expose
    private Response response;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

}