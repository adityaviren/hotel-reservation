package com.cg.hotelreservation;

public class Ridgewood {
    public int rating=5;
    public int weekdayReg=220;
    public int weekendReg=150;
    public int weekdayRew=100;
    public int weekendRew=40;

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getWeekdayReg() {
        return weekdayReg;
    }

    public void setWeekdayReg(int weekdayReg) {
        this.weekdayReg = weekdayReg;
    }

    public int getWeekendReg() {
        return weekendReg;
    }

    public void setWeekendReg(int weekendReg) {
        this.weekendReg = weekendReg;
    }

    public int getWeekdayRew() {
        return weekdayRew;
    }

    public void setWeekdayRew(int weekdayRew) {
        this.weekdayRew = weekdayRew;
    }

    public int getWeekendRew() {
        return weekendRew;
    }

    public void setWeekendRew(int weekendRew) {
        this.weekendRew = weekendRew;
    }

    Ridgewood(int rating,int weekdayReg,int weekendReg){
        this.rating=rating;
        this.weekendReg=weekendReg;
        this.weekdayReg=weekdayReg;
    }
}
