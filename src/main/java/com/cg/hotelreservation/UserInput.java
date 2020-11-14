package com.cg.hotelreservation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;


public class UserInput {

    Scanner scanner = new Scanner(System.in);
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ddMMMyyyy");

    Hotel hotel1 = new Hotel("Lakewood",3,110,90,80,80);

    Hotel hotel2 = new Hotel("Bridgewood",4,160,60,110,50);

    Hotel hotel3 = new Hotel("Ridgewood",5,220,150,100,40);

    public ArrayList<Hotel> hotelArrayList = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("WELCOME TO HOTEL RESERVATION PROGRAM");
        UserInput userInput = new UserInput();
        Date[] dateArray= userInput.input();
        userInput.lowestRateSimple(dateArray[0],dateArray[1]);
    }

    public void addHotel(){
        hotelArrayList.add(hotel1);
        hotelArrayList.add(hotel2);
        hotelArrayList.add(hotel3);
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
        int hotel2_rates=hotel2Rates(numOfDays);
        int hotel1_rates=hotel1Rates(numOfDays);
        int hotel3_rates=hotel3Rates(numOfDays);
        if(hotel1_rates<hotel2_rates&&hotel1_rates<hotel3_rates){
            return hotel1_rates;
        }
        else if(hotel2_rates<hotel1_rates&&hotel2_rates<hotel3_rates)
            return hotel2_rates;
        else
            return hotel3_rates;
    }

    public int hotel1Rates(int numOfDays){
        return numOfDays*hotel1.weekdayReg;
    }
    public int hotel2Rates(int numOfDays){

        return numOfDays*hotel2.weekdayReg;
    }
    public int hotel3Rates(int numOfDays){
        return numOfDays*hotel3.weekdayReg;
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
            System.out.println("Invalid Date");
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
            System.out.println("Invalid Date");
            return null;
        }
    }

    //UC4 MinimumRates according to days
    //UC6 minimumRates with better rating
    public String[] lowestRateWeekdays(Date start, Date end){
        int[] days = getDays(start,end);

        int weekdays = days[0];
        int weekend = days[1];

        int hotel2_rates=hotel2Ratesday(false,weekdays,weekend);
        int hotel1_rates=hotel1Ratesday(false,weekdays,weekend);
        int hotel3_rates=hotel3Ratesday(false,weekdays,weekend);
        System.out.println(hotel1_rates+" " + hotel2_rates+" "+hotel3_rates);
        String[] output = new String[3];
        if(hotel3_rates<=hotel2_rates&&hotel3_rates<=hotel2_rates){
            output[0] = "Ridgewood";
            output[1]=String.valueOf(hotel3.rating);
            output[2]=String.valueOf(hotel3_rates);
            return output;

        }
        else if(hotel2_rates<=hotel1_rates&&hotel2_rates<=hotel3_rates) {
            output[0] = "Bridgewood";
            output[1]=String.valueOf(hotel2.rating);
            output[2]=String.valueOf(hotel2_rates);
            return output;
        }
        else {
            output[0] = "Lakewood";
            output[1]=String.valueOf(hotel1.rating);
            output[2]=String.valueOf(hotel1_rates);
            return output;
        }
    }

    public int hotel1Ratesday(boolean isRewards,int weekdays,int weekends){
        if(isRewards)
            return weekdays*hotel1.weekdayRew+weekends*hotel1.weekendRew;
        else
            return weekdays*hotel1.weekdayReg+weekends*hotel1.weekendReg;
    }
    public int hotel2Ratesday(boolean isRewards,int weekdays,int weekends){
        if(isRewards)
            return weekdays*hotel2.weekdayRew+weekends*hotel2.weekendRew;
        else
            return weekdays*hotel2.weekdayReg+weekends*hotel2.weekendReg;
    }
    public int hotel3Ratesday(boolean isRewards,int weekdays,int weekends){
        if(isRewards)
            return weekdays*hotel3.weekdayRew+weekends*hotel3.weekendRew;
        else
            return weekdays*hotel3.weekdayReg+weekends*hotel3.weekendReg;
    }

    public String[] highestRatedHotel(Date start, Date end){
        int[] days = getDays(start,end);

        int weekdays = days[0];
        int weekend = days[1];

        String[] output=new String[3];
        if(hotel3.getRating()>hotel2.getRating()&&hotel3.getRating()>hotel1.getRating()){
            output[0] = "Ridgewood";
            output[1]=String.valueOf(hotel3.rating);
            output[2]=String.valueOf(hotel3Ratesday(false,weekdays,weekend));
            return output;
        }
        else if(hotel2.getRating()>hotel1.getRating()&&hotel2.getRating()>hotel3.getRating()) {
            output[0] = "Bridgewood";
            output[1]=String.valueOf(hotel2.rating);
            output[2]=String.valueOf(hotel2Ratesday(false,weekdays,weekend));
            return output;
        }
        else {
            output[0] = "Lakewood";
            output[1]=String.valueOf(hotel1.rating);
            output[2]=String.valueOf(hotel1Ratesday(false,weekdays,weekend));
            return output;
        }
    }

    //UC10 Best Rated Cheapest Hotel for Rewards Member
    public String[] lowestRateRewardsWeekdays(Date start, Date end){
        int[] days=getDays(start,end);
        int weekdays=days[0];
        int weekend = days[1];
        int hotel2_rates=hotel2Ratesday(true,weekdays,weekend);
        int hotel1_rates=hotel1Ratesday(true,weekdays,weekend);
        int hotel3_rates=hotel3Ratesday(true,weekdays,weekend);
        String[] output = new String[3];
        if(hotel3_rates<=hotel2_rates&&hotel3_rates<=hotel2_rates){
            output[0] = "Ridgewood";
            output[1]=String.valueOf(hotel3.rating);
            output[2]=String.valueOf(hotel3_rates);
            return output;

        }
        else if(hotel2_rates<=hotel1_rates&&hotel2_rates<=hotel3_rates) {
            output[0] = "Bridgewood";
            output[1]=String.valueOf(hotel2.rating);
            output[2]=String.valueOf(hotel2_rates);
            return output;
        }
        else {
            output[0] = "Lakewood";
            output[1]=String.valueOf(hotel1.rating);
            output[2]=String.valueOf(hotel1_rates);
            return output;
        }
    }

    public Hotel cheapestHotelHighestRatingRewards(Date start, Date end){
        int[] days = getDays(start,end);

        int weekdays = days[0];
        int weekend = days[1];
        hotelArrayList.stream().forEach(hotel -> hotel.setRates(true, weekdays, weekend));

        List<Hotel> list = hotelArrayList.stream().sorted((c1, c2)->Integer.compare(c1.getRates(), c2.getRates()))
                .collect(Collectors.toList());
        int min = list.get(0).getRates();
        list = list.stream().filter(hotel -> hotel.getRates()==min)
                .sorted((c1,c2)->Integer.compare(c2.getRating(),c1.getRating()))
                .collect(Collectors.toList());
        Hotel hotel = list.get(0);
        System.out.println(hotel.getName() + ", Ratings : " + hotel.getRating() + ", Total Charges : " +
                hotel.getRates());
        return hotel;

    }

    public int[] getDays(Date start, Date end){
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
        int[] days = {weekdays,weekend};
        return days;
    }
}
