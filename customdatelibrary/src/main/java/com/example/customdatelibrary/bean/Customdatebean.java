package com.example.customdatelibrary.bean;

public class Customdatebean {
    private String olddate; //需要覆盖的原数据
    private String newdata; //新数据 覆盖的数据

    public Customdatebean(){

    }

    public Customdatebean(String olddate, String newdata) {
        this.olddate = olddate;
        this.newdata = newdata;
    }

    public String getOlddate() {
        return olddate;
    }

    public void setOlddate(String olddate) {
        this.olddate = olddate;
    }

    public String getNewdata() {
        return newdata;
    }

    public void setNewdata(String newdata) {
        this.newdata = newdata;
    }
}
