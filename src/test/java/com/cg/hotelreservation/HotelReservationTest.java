package com.cg.hotelreservation;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HotelReservationTest {

    @Test
    public void inputDate() throws ParseException {
        String date="13sep2014";
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMMyyyy");
        Date date1=dateFormat.parse(date);
        System.out.println(date1);
    }
}
