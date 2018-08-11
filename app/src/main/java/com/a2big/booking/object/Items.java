package com.a2big.booking.object;

/**
 * Created by javiergonzalezcabezas on 11/5/15.
 */
public class Items {
    public int type;
    public String descString;
    public String imageUrl1;
    public String imageUrl2;
    public String imageUrl3;
    public String imageUrl4;
    public String imageUrl5;

    public Items(int type, String string, String url1,String url2,String url3,String url4,String url5) {
        this.descString = string;
        this.imageUrl1 = url1;
        this.imageUrl2 = url2;
        this.imageUrl3 = url3;
        this.imageUrl4 = url4;

        this.imageUrl5 = url5;

        this.type = type;
    }

}