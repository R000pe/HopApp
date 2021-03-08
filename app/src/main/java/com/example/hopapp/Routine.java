package com.example.hopapp;

public class Routine { // sanna koittaa lisätä pvmäärät tähän
    private int mImageResource, year, month, day;
    private String mText1;
    private String mText2;

    public Routine( int imageResource, String text1, String text2) {
        mImageResource = imageResource;
        mText1 = text1;
        mText2 = text2;
    }

    public int getmImageResource(){
        return mImageResource;
    }
    public String getText1(){
        return mText1;
    }

    public String getText2(){
        return mText2;
    }

    //sanna's getters
    public int getYear(){
        return this.year;
    }

    public int getMonth(){
        return this.month;
    }

    public int getDay(){
        return this.day;
    }

    //sanna's setters
    public void setYear(int year){
        this.year = year;
    }

    public void setMonth(int month){
        this.month = month;
    }

    public void setDay(int day){
        this.day = day;
    }

}
