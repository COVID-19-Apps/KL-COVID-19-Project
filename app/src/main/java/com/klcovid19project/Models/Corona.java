package com.klcovid19project.Models;

public class Corona {

    String district, confirmed;

    public Corona() {
    }

    public Corona(String district, String confirmed) {
        this.district = district;
        this.confirmed = confirmed;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(String confirmed) {
        this.confirmed = confirmed;
    }
}
