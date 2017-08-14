package com.omottec.demoapp.gson;

import android.content.Intent;

/**
 * Created by qinbingbing on 21/06/2017.
 */

public class GsonObject {
    public int primaryPrice;
    public int primaryPrice1;
    public Integer integerPrice;
    private String str;
    private String str1 = "";
    private String str2;
    private boolean b;
    private boolean b1 = true;
    public static final String LAT_LON = "";

    @Override
    public String toString() {
        return "{primaryPrice: " + primaryPrice
                + ", primaryPrice1: " + primaryPrice1
                + ", integerPrice: " + integerPrice
                + ", str:" + str
                + ", str1:" + str1
                + ", str2:" + str2
                + ", b:" + b
                + ", b1:" + b1
                + ", hashCode: " + hashCode()
                + "}";
    }
}
