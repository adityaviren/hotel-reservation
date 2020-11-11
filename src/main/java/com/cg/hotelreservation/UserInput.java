package com.cg.hotelreservation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Scanner;



public class UserInput {

    Scanner scanner = new Scanner(System.in);
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ddMMMyyyy");

    Lakewood lakewood=new Lakewood(3,110,90,80,80);

    Bridgewood bridgewood = new Bridgewood(4,160,60,110,50);

    Ridgewood ridgewood = new Ridgewood(5,220,150,100,40);

    public static void main(String[] args) {
        System.out.println("WELCOME TO HOTEL RESERVATION PROGRAM");
        UserInput userInput = new UserInput();
        Date[] dateArray= userInput.input();
        userInput.lowestRateSimple(dateArray[0],dateArray[1]);
    }

    public Date[] input(){

        Date start,end;
        while(true) {
            System.out.println("Enter the start date in the format ddMMMYYYY");
            start = returnStartDate(scanner.nextLine());
            if(start!=null)
                break;
        }
        while (true){
            System.out.println("Enter the end date in the format ddMMMYYYY");
            end = returnEndDate(scanner.nextLine());
            if(end!=null)
                break;
        }
        Date[] dateArray=new Date[2];
        dateArray[0]=start;
        dateArray[1]=end;
        return dateArray;
    }

    //UC1 basic rates
    public int lowestRateSimple(Date start, Date end){
        long difference = end.getTime()-start.getTime();
        int numOfDays = (int) Math.floor(difference/(3600*24*1000));
        int bridgewood_rates=bridgewoodRates(numOfDays);
        int lakewood_rates=lakewoodRates(numOfDays);
        int ridgewood_rates=ridgewoodRates(numOfDays);
        if(lakewood_rates<bridgewood_rates&&lakewood_rates<ridgewood_rates){
            return lakewood_rates;
        }
        else if(bridgewood_rates<lakewood_rates&&bridgewood_rates<ridgewood_rates)
            return bridgewood_rates;
        else
            return ridgewood_rates;
    }

    public int lakewoodRates(int numOfDays){
        return numOfDays*lakewood.weekdayReg;
    }
    public int bridgewoodRates(int numOfDays){

        return numOfDays*bridgewood.weekdayReg;
    }
    public int ridgewoodRates(int numOfDays){
        return numOfDays*ridgewood.weekdayReg;
    }

    public LocalDate convertToLocalDateViaSqlDate(Date dateToConvert) {
        return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
    }

    public Date convertToDateViaSqlDate(LocalDate dateToConvert) {
        return java.sql.Date.valueOf(dateToConvert);
    }

    public Date returnStartDate(String start){
        try {
            return simpleDateFormat.parse(start);
        } catch (ParseException e) {
            return null;
        }
    }

    public Date returnEndDate(String end){
        try {
            Date date = simpleDateFormat.parse(end);
            LocalDate localDate = convertToLocalDateViaSqlDate(date);
            localDate=localDate.plusDays(1);
            date=convertToDateViaSqlDate(localDate);
            return date;
        } catch (ParseException e) {
            return null;
        }
    }

    //UC4 MinimumRates according to days
    //UC6 minimumRates with better rating
    public String[] lowestRateWeekdays(Date start, Date end){
        int weekdays=0,weekend=0;
        Date count=start;
        LocalDate localDate;
        long difference = end.getTime()-start.getTime();
        int numOfDays = (int) Math.floor(difference/(3600*24*1000));
        for(int i=0;i<numOfDays;i++){
            if(count.getDay()==6||count.getDay()==0){
                weekend++;
            }
            else
                weekdays++;
            localDate = convertToLocalDateViaSqlDate(count).plusDays(1);
            count=convertToDateViaSqlDate(localDate);
        }
        int bridgewood_rates=bridgewoodRatesday(false,weekdays,weekend);
        int lakewood_rates=lakewoodRatesday(false,weekdays,weekend);
        int ridgewood_rates=ridgewoodRatesday(false,weekdays,weekend);
        String[] output = new String[3];
        if(ridgewood_rates<=bridgewood_rates&&ridgewood_rates<=bridgewood_rates){
            output[0] = "Ridgewood";
            output[1]=String.valueOf(ridgewood.rating);
            output[2]=String.valueOf(ridgewood_rates);
            return output;

        }
        else if(bridgewood_rates<=lakewood_rates&&bridgewood_rates<=ridgewood_rates) {
            output[0] = "Bridgewood";
            output[1]=String.valueOf(bridgewood.rating);
            output[2]=String.valueOf(bridgewood_rates);
            return output;
        }
        else {
            output[0] = "Lakewood";
            output[1]=String.valueOf(lakewood.rating);
            output[2]=String.valueOf(lakewood_rates);
            return output;
        }
    }

    public int lakewoodRatesday(boolean isRewards,int weekdays,int weekends){
        if(isRewards)
            return weekdays*lakewood.weekdayReg+weekends*lakewood.weekendReg;
        else
            return weekdays*lakewood.weekdayRew+weekends*lakewood.weekendRew;
    }
    public int bridgewoodRatesday(boolean isRewards,int weekdays,int weekends){
        if(isRewards)
            return weekdays*bridgewood.weekdayRew+weekends*bridgewood.weekendRew;
        else
            return weekdays*bridgewood.weekdayReg+weekends*bridgewood.weekendReg;
    }
    public int ridgewoodRatesday(boolean isRewards,int weekdays,int weekends){
        if(isRewards)
            return weekdays*ridgewood.weekdayRew+weekends*ridgewood.weekendRew;
        else
            return weekdays*ridgewood.weekdayReg+weekends*ridgewood.weekendReg;
    }

    public String[] highestRatedHotel(Date start, Date end){
        int weekdays=0,weekend=0;
        Date count=start;
        LocalDate localDate;
        long difference = end.getTime()-start.getTime();
        int numOfDays = (int) Math.floor(difference/(3600*24*1000));
        for(int i=0;i<numOfDays;i++){
            if(count.getDay()==6||count.getDay()==0){
                weekend++;
            }
            else
                weekdays++;
            localDate = convertToLocalDateViaSqlDate(count).plusDays(1);
            count=convertToDateViaSqlDate(localDate);
        }

        String[] output=new String[3];
        if(ridgewood.getRating()>bridgewood.getRating()&&ridgewood.getRating()>lakewood.getRating()){
            output[0] = "Ridgewood";
            output[1]=String.valueOf(ridgewood.rating);
            output[2]=String.valueOf(ridgewoodRatesday(false,weekdays,weekend));
            return output;
        }
        else if(bridgewood.getRating()>lakewood.getRating()&&bridgewood.getRating()>ridgewood.getRating()) {
            output[0] = "Bridgewood";
            output[1]=String.valueOf(bridgewood.rating);
            output[2]=String.valueOf(bridgewoodRatesday(false,weekdays,weekend));
            return output;
        }
        else {
            output[0] = "Lakewood";
            output[1]=String.valueOf(lakewood.rating);
            output[2]=String.valueOf(lakewoodRatesday(false,weekdays,weekend));
            return output;
        }
    }

    //UC10 Best Rated Cheapest Hotel for Rewards Member
    public String[] lowestRateRewardsWeekdays(Date start, Date end){
        int weekdays=0,weekend=0;
        Date count=start;
        LocalDate localDate;
        long difference = end.getTime()-start.getTime();
        int numOfDays = (int) Math.floor(difference/(3600*24*1000));
        for(int i=0;i<numOfDays;i++){
            if(count.getDay()==6||count.getDay()==0){
                weekend++;
            }
            else
                weekdays++;
            localDate = convertToLocalDateViaSqlDate(count).plusDays(1);
            count=convertToDateViaSqlDate(localDate);
        }
        int bridgewood_rates=bridgewoodRatesday(true,weekdays,weekend);
        int lakewood_rates=lakewoodRatesday(true,weekdays,weekend);
        int ridgewood_rates=ridgewoodRatesday(true,weekdays,weekend);
        String[] output = new String[3];
        if(ridgewood_rates<=bridgewood_rates&&ridgewood_rates<=bridgewood_rates){
            output[0] = "Ridgewood";
            output[1]=String.valueOf(ridgewood.rating);
            output[2]=String.valueOf(ridgewood_rates);
            return output;

        }
        else if(bridgewood_rates<=lakewood_rates&&bridgewood_rates<=ridgewood_rates) {
            output[0] = "Bridgewood";
            output[1]=String.valueOf(bridgewood.rating);
            output[2]=String.valueOf(bridgewood_rates);
            return output;
        }
        else {
            output[0] = "Lakewood";
            output[1]=String.valueOf(lakewood.rating);
            output[2]=String.valueOf(lakewood_rates);
            return output;
        }
    }
}
