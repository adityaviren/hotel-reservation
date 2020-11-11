package com.cg.hotelreservation;

import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

public class HotelReservationTest {

    @Test
    public void GivenIncorrectDate_shouldAskAgain() {
        UserInput userInput = new UserInput();
        Date start = userInput.returnStartDate("12-09-2020");
        Date end = userInput.returnEndDate("13-09-2020");
        Assert.assertEquals(null,start);
        Assert.assertEquals(null,end);
    }

    @Test
    public void givenDate_shouldReturnLowestRate_whenPassedThroughInput() {
        UserInput userInput = new UserInput();
        Date start = userInput.returnStartDate("12sep2020");
        Date end = userInput.returnEndDate("13sep2020");
        int minRate = userInput.lowestRateSimple(start,end);
        Assert.assertEquals(220,minRate);
    }

    @Test
    public void givenRange_shouldReturnLowestRate_accordingToWeekdays() {
        UserInput userInput = new UserInput();
        Date start = userInput.returnStartDate("11sep2020");
        Date end = userInput.returnEndDate("12sep2020");
        String[] output = userInput.lowestRateWeekdays(start,end);
        System.out.println(output[0] + ",Rating :" + output[1] + " and Total Rates = $" + output[2]);
        int minRate = Integer.parseInt(output[2]);
        Assert.assertEquals(200,minRate);
    }

    @Test
    public void givenRange_shouldReturnLowestRate_accordingToRatings() {
        UserInput userInput = new UserInput();
        Date start = userInput.returnStartDate("11sep2020");
        Date end = userInput.returnEndDate("12sep2020");
        String[] output = userInput.lowestRateWeekdays(start,end);
        System.out.println(output[0] + ",Rating :" + output[1] + " and Total Rates = $" + output[2]);
        Assert.assertEquals(output[1],"4");
    }

    @Test
    public void GivenRange_shouldReturnHighestRatedHotelAndRate() {
        UserInput userInput = new UserInput();
        Date start = userInput.returnStartDate("11sep2020");
        Date end = userInput.returnEndDate("12sep2020");
        String[] output = userInput.highestRatedHotel(start,end);
        System.out.println(output[0] + ",Rating :" + output[1] + " and Total Rates = $" + output[2]);
        Assert.assertEquals(output[1],"5");
        Assert.assertEquals(output[0],"Ridgewood");
        Assert.assertEquals(output[2],"370");
    }

    @Test
    public void givenRange_shouldReturnCheapesHotel_whenCustomerIsRewardsMember() {
        UserInput userInput = new UserInput();
        Date start = userInput.returnStartDate("11sep2020");
        Date end = userInput.returnEndDate("12sep2020");
        String[] output = userInput.lowestRateRewardsWeekdays(start,end);
        System.out.println(output[0] + ",Rating :" + output[1] + " and Total Rates = $" + output[2]);
        Assert.assertEquals(output[1],"5");
        Assert.assertEquals(output[0],"Ridgewood");
        Assert.assertEquals(output[2],"140");
    }
}
