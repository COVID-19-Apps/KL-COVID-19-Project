package com.klcovid19project.Models;

public class Toll_Numbers {

    String district, phone_number;

    public Toll_Numbers() {
    }

    public Toll_Numbers(String district, String phone_number) {
        this.district = district;
        this.phone_number = phone_number;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
}
