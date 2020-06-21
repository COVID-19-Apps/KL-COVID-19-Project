package com.klcovid19project.Models;

public class HealthCareList {

    String sno, district, name, type;

    public HealthCareList() {
    }

    public HealthCareList(String sno, String district, String name, String type) {
        this.sno = sno;
        this.district = district;
        this.name = name;
        this.type = type;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
