package com.cookandroid.exam.Util;

public class LocationItem {
    String name;
    String road;
    String address;
    Double x;   //위도
    Double y;   //경도

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoad() {
        return road;
    }

    public void setRoad(String road) {
        this.road = road;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public LocationItem(String name, String road, String address, Double x, Double y){
        this.name = name;
        this.road = road;
        this.address = address;
        this.x = x;
        this.y = y;
    }
}
