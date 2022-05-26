package com.cookandroid.exam.DTO.Location;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Place {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("place_name")
    @Expose
    private String place_name;
    @SerializedName("address_name")
    @Expose
    private String address_name;
    @SerializedName("road_address_name")
    @Expose
    private String road_address_name;
    @SerializedName("x")
    @Expose
    private String x;
    @SerializedName("y")
    @Expose
    private String y;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlace_name() {
        return place_name;
    }

    public void setPlace_name(String place_name) {
        this.place_name = place_name;
    }

    public String getAddress_name() {
        return address_name;
    }

    public void setAddress_name(String address_name) {
        this.address_name = address_name;
    }

    public String getRoad_address_name() {
        return road_address_name;
    }

    public void setRoad_address_name(String road_address_name) {
        this.road_address_name = road_address_name;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public Place(String id, String place_name, String address_name, String road_address_name, String x, String y){
        this.id = id;
        this.place_name = place_name;
        this.address_name = address_name;
        this.road_address_name = road_address_name;
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return "Place(id=" + this.id + ", place_name=" + this.place_name + ", address_name=" + this.address_name + ", road_address_name=" + this.road_address_name + ", x=" + this.x + ", y=" + ")";
    }
}
