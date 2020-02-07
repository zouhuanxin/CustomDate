package com.example.customdatelibrary.bean;

public class Notebean {
    private String date;
    private String note;

    public Notebean(){

    }

    public Notebean(String date, String note) {
        this.date = date;
        this.note = note;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
