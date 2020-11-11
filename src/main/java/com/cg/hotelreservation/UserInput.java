package com.cg.hotelreservation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Scanner;

import static java.time.temporal.ChronoUnit.DAYS;

public class UserInput {

    Scanner scanner = new Scanner(System.in);
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ddMMMyyyy");

    

    public static void main(String[] args) {
        System.out.println("WELCOME TO HOTEL RESERVATION PROGRAM");
        UserInput userInput = new UserInput();
        Date[] dateArray=new Date[2];
        dateArray = userInput.input();
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
        Lakewood lakewood=new Lakewood();
        return numOfDays*lakewood.weekdayReg;
    }
    public int bridgewoodRates(int numOfDays){
        Bridgewood bridgewood = new Bridgewood();
        return numOfDays*bridgewood.weekdayReg;
    }
    public int ridgewoodRates(int numOfDays){
        Ridgewood ridgewood = new Ridgewood();
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
    public int lowestRateWeekdays(Date start, Date end){
        int weekdays=0,weekend=0;
        Date count=start;
        LocalDate localDate;
        long difference = end.getTime()-start.getTime();
        int numOfDays = (int) Math.floor(difference/(3600*24*1000));
        for(int i=0;i<numOfDays;i++){
            if(count.getDay()==6||count.getTime()==5){
                weekend++;
            }
            else
                weekdays++;
            localDate = convertToLocalDateViaSqlDate(count).plusDays(1);
            count=convertToDateViaSqlDate(localDate);
        }
        int bridgewood_rates=bridgewoodRatesday(weekdays,weekend);
        int lakewood_rates=lakewoodRatesday(weekdays,weekend);
        int ridgewood_rates=ridgewoodRatesday(weekdays,weekend);
        if(lakewood_rates<=bridgewood_rates&&lakewood_rates<=ridgewood_rates){
            return lakewood_rates;
        }
        else if(bridgewood_rates<=lakewood_rates&&bridgewood_rates<=ridgewood_rates)
            return bridgewood_rates;
        else
            return ridgewood_rates;
    }

    public int lakewoodRatesday(int weekdays,int weekends){
        Lakewood lakewood=new Lakewood();
        return weekdays*lakewood.weekdayReg+weekends*lakewood.weekendReg;
    }
    public int bridgewoodRatesday(int weekdays,int weekends){
        Bridgewood bridgewood = new Bridgewood();
        return weekdays*bridgewood.weekdayReg+weekends*bridgewood.weekendReg;
    }
    public int ridgewoodRatesday(int weekdays,int weekends){
        Ridgewood ridgewood = new Ridgewood();
        return weekdays*ridgewood.weekdayReg+weekends*ridgewood.weekendReg;
    }
}
