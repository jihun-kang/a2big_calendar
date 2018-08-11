package com.a2big.booking;


/**
 * Created by javiergonzalezcabezas on 11/5/15.
 */
public class BookingItems {
    public String day;
    public String roomNo;
    public String totalNumber;
    public String number;
    public String price;

    public BookingItems(String d, String no, String total, String n, String p) {
        this.day = d;
        this.roomNo = no;
        this.totalNumber = total;
        this.number = n;
        this.price = p;
    }

}