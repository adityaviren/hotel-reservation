package com.cg.hotelreservation;

public class Lakewood {
    public int rating=3;
    public int weekdayReg=110;
    public int weekendReg=90;
    public int weekdayRew=80;
    public int weekendRew=80;

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

    Lakewood(int rating,int weekdayReg,int weekendReg){
        this.rating=rating;
        this.weekendReg=weekendReg;
        this.weekdayReg=weekdayReg;
    }
}
