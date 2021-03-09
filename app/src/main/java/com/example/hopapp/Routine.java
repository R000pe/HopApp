package com.example.hopapp;
/**
 * Tehtavien luokka
 * @author sanku, Wilma Paloranta
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
    /**
     * Toinen konstruktori pvm:ien lisaamista varten
     * */
    public Routine( int imageResource, String text1, String text2, int year, int month, int dayOfMonth){

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

    /**
     * asettaa rutiininajankohdan vuoden
     * @param year vuosi
     * */
    public void setYear(int year){      // date setters
        this.year = year;
    }

    /**
     * asettaa rutiininajankohdan kuukauden
     * @param month kuukausi
     * */
    public void setMonth(int month){
        this.month = month;
    }

    /**
     * asettaa rutiininajankohdan paivan
     * @param day paiva
     * */
    public void setDayOfMonth(int day){
        this.dayOfMonth = day;
    }


    /**
     * palauttaa rutiininajankohdan vuoden
     * @return int year vuosi
     * */
    public int getYear(){       // date getters
        return this.year;
    }

    /**
     * palauttaa rutiininajankohdan kuukauden
     * @return int month kuukausi
     * */
    public int getMonth(){
        return this.month;
    }
    /**
     * palauttaa rutiininajankohdan paivan
     * @return int day paiva
     * */
    public int getDayOfMonth(){
        return this.dayOfMonth;
    }
    /**
     * palauttaa rutiininajankohdan pvm kokonaisuudessaan
     * @return dayofMonth
     * @return month
     * @return year
     * */
    public String getDateFull(){    // returns d/m/yyyy
        return dayOfMonth + ". " + month + " " + year;
    }

    /**
     * palauttaa aktiviteetin otsikon & pvm miellyttavan nakoisesti Schedule-luokan listaan
     * @return
     * */
    @Override
    public String toString() {

        return  this.mText1 +
                ", " + dayOfMonth +
                ". " + (month+1) +
                ". " + year;
    }
}
