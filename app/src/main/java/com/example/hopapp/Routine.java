package com.example.hopapp;
/**
 * Tehtavien luokka
 * @author sanku
 * @version 1.1 03/2021
 */
public class Routine {
    private int mImageResource;
    private String mText1;
    private String mText2;
    private int year, month, dayOfMonth;

    public Routine( int imageResource, String text1, String text2){
        mImageResource = imageResource;
        mText1 = text1;
        mText2 = text2;
    }

    public Routine( int imageResource, String text1, String text2, int year, int month, int dayOfMonth){
        /**
         * Toinen konstruktori pvm:ien lisaamista varten
         * */
        mImageResource = imageResource;
        mText1 = text1;
        mText2 = text2;
        this.year = year;
        this.month = month;
        this.dayOfMonth = dayOfMonth;
    }


    public int getmImageResource(){
        return mImageResource;
    }
    public String getTitle(){
        return mText1;
    }

    public String getDesc(){
        return mText2;
    }

    public void setYear(int year){      // date setters
        this.year = year;
    }

    public void setMonth(int month){
        this.month = month;
    }

    public void setDayOfMonth(int day){
        this.dayOfMonth = day;
    }

    public int getYear(){       // date getters
        return this.year;
    }

    public int getMonth(){
        return this.month;
    }

    public int getDayOfMonth(){
        return this.dayOfMonth;
    }

    public String getDateFull(){    // returns d/m/yyyy
        return dayOfMonth + ". " + month + " " + year;
    }

    @Override
    public String toString() {
        /**
         * palauttaa aktiviteetin otsikon & pvm miellyttavan nakoisesti Schedule-luokan listaan
         * */
        return  this.mText1 +
                ", " + dayOfMonth +
                ". " + (month+1) +
                ". " + year;
    }
}
