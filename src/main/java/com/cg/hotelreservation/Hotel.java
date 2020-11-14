package com.cg.hotelreservation;

public class Hotel {
    protected String name;
    public int rating;
    public int weekdayReg;
    public int weekendReg;
    public int weekdayRew;
    public int weekendRew;
    public int rates;

    public int getRates() {
        return rates;
    }

    public void setRates(int rates) {
        this.rates = rates;
    }

    public void setRates(boolean isRewards,int weekdays,int weekends){
        if(isRewards)
            rates =  weekdays*weekdayRew+weekends*weekendRew;
        else
            rates = weekdays*weekdayReg+weekends*weekendReg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    Hotel(String name,int rating,int weekdayReg,int weekendReg,int weekdayRew,int weekendRew){
        this.name=name;
        this.rating=rating;
        this.weekendReg=weekendReg;
        this.weekdayReg=weekdayReg;
        this.weekdayRew=weekdayRew;
        this.weekendRew=weekendRew;
    }

    public String toString(){
        return name + " " + rating + " " + rates + " ";
    }
}

