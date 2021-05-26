package com.example.capstone_ui_1.Data;

public class ChosunDTO {

    private int b_pno;
    private String building;    //건물이름
    private String major;
    private String professor;
    private double latitude;     //위도
    private double longtitude;   //경도

    public int getB_pno() {
        return b_pno;
    }

    public void setB_pno(int b_pno) {
        this.b_pno = b_pno;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) { this.building = building; }

    public String getMajor() { return major; }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getProfessor() { return professor; }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }
}
