package com.example.arnavi.Data;

public class BuildingDTO {

    private String bname;    //건물이름
    private float latitude;     //위도
    private float longtitude;   //경도

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(float longtitude) {
        this.longtitude = longtitude;
    }
}
