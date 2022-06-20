package com.cookandroid.mysonge.DTO.Dust;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Header {

    @SerializedName("resultMsg")
    @Expose
    private String resultMsg;
    @SerializedName("resultCode")
    @Expose
    private String resultCode;

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

}
