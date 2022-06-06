package com.cookandroid.exam.DTO.Location;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Document {
    @SerializedName("address_name")
    @Expose
    private String addressName;
    @SerializedName("category_group_code")
    @Expose
    private String categoryGroupCode;
    @SerializedName("category_group_name")
    @Expose
    private String categoryGroupName;
    @SerializedName("category_name")
    @Expose
    private String categoryName;
    @SerializedName("distance")
    @Expose
    private String distance;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("place_name")
    @Expose
    private String placeName;
    @SerializedName("place_url")
    @Expose
    private String placeUrl;
    @SerializedName("road_address_name")
    @Expose
    private String roadAddressName;
    @SerializedName("x")
    @Expose
    private String x;
    @SerializedName("y")
    @Expose
    private String y;

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public String getCategoryGroupCode() {
        return categoryGroupCode;
    }

    public void setCategoryGroupCode(String categoryGroupCode) {
        this.categoryGroupCode = categoryGroupCode;
    }

    public String getCategoryGroupName() {
        return categoryGroupName;
    }

    public void setCategoryGroupName(String categoryGroupName) {
        this.categoryGroupName = categoryGroupName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getPlaceUrl() {
        return placeUrl;
    }

    public void setPlaceUrl(String placeUrl) {
        this.placeUrl = placeUrl;
    }

    public String getRoadAddressName() {
        return roadAddressName;
    }

    public void setRoadAddressName(String roadAddressName) {
        this.roadAddressName = roadAddressName;
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

    public Document(String id, String distance, String place_name, String place_url, String category_name, String address_name, String road_address_name, String phone, String category_group_name, String category_group_code, String x, String y){
        this.id = id;
        this.distance = distance;
        this.placeName = place_name;
        this.placeUrl = place_url;
        this.categoryName = category_name;
        this.addressName = address_name;
        this.roadAddressName = road_address_name;
        this.categoryGroupCode = category_group_code;
        this.categoryGroupName = category_group_name;
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return "Place(id=" + this.id + ", place_name=" + this.placeName + ", address_name=" + this.addressName + ", road_address_name=" + this.roadAddressName + ", x=" + this.x + ", y=" + ")";
    }
}
