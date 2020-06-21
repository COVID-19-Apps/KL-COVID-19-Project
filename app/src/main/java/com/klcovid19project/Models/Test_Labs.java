package com.klcovid19project.Models;

public class Test_Labs {
    String sno, name, type, test_type;

    public Test_Labs() {
    }

    public Test_Labs(String sno, String name, String type, String test_type) {
        this.sno = sno;
        this.name = name;
        this.type = type;
        this.test_type = test_type;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
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

    public String getTest_type() {
        return test_type;
    }

    public void setTest_type(String test_type) {
        this.test_type = test_type;
    }
}
